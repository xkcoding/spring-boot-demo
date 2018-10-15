package com.xkcoding.orm.jdbctemplate.dao.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xkcoding.orm.jdbctemplate.annotation.Auto;
import com.xkcoding.orm.jdbctemplate.annotation.Column;
import com.xkcoding.orm.jdbctemplate.annotation.Ignore;
import com.xkcoding.orm.jdbctemplate.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * Dao基类
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.dao.base
 * @description: Dao基类
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 11:28 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class BaseDao<T> {
	private JdbcTemplate jdbcTemplate;

	public BaseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 通用插入
	 *
	 * @param t          对象
	 * @param ignoreNull 是否忽略 null 值
	 * @return 操作的行数
	 */
	public Integer insert(T t, Boolean ignoreNull) {
		Table tableAnnotation = t.getClass().getAnnotation(Table.class);
		String table;
		if (ObjectUtil.isNotNull(tableAnnotation)) {
			table = StrUtil.format("`{}`", tableAnnotation.name());
		} else {
			table = StrUtil.format("`{}`", t.getClass().getName().toLowerCase());
		}

		// 获取所有字段，包含父类中的字段
		Field[] fields = ReflectUtil.getFields(t.getClass());

		// 过滤数据库中不存在的字段，以及自增列
		List<Field> filterField;
		Stream<Field> fieldStream = CollUtil.toList(fields).stream().filter(field -> ObjectUtil.isNull(field.getAnnotation(Ignore.class)) || ObjectUtil.isNull(field.getAnnotation(Auto.class)));

		// 是否过滤字段值为null的字段
		if (ignoreNull) {
			filterField = fieldStream.filter(field -> ObjectUtil.isNotNull(ReflectUtil.getFieldValue(t, field))).collect(Collectors.toList());
		} else {
			filterField = fieldStream.collect(Collectors.toList());
		}

		// 构造列
		List<String> columnList = CollUtil.newArrayList();
		for (Field field : filterField) {
			Column columnAnnotation = field.getAnnotation(Column.class);
			String columnName;
			if (ObjectUtil.isNotNull(columnAnnotation)) {
				columnName = columnAnnotation.name();
			} else {
				columnName = field.getName();
			}
			columnList.add(StrUtil.format("`{}`", columnName));
		}
		String columns = StrUtil.join(",", columnList);

		// 构造占位符
		String params = StrUtil.repeatAndJoin("?", columnList.size(), ",");

		// 构造值
		Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

		String sql = StrUtil.format("INSERT INTO {table} ({columns}) VALUES ({params})", Dict.create().set("table", table).set("columns", columns).set("params", params));
		log.debug("【执行SQL】SQL：{}", sql);
		log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(values));
		return jdbcTemplate.update(sql, values);
	}
}
