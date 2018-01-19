package com.xkcoding.util.domain;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 树节点
 * </p>
 *
 * @package: com.xkcoding.util.domain
 * @description： 树节点
 * @author: yangkai.shen
 * @date: Created in 2017/12/7 上午10:27
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
	/**
	 * id 编号
	 */
	private Integer id;
	/**
	 * 节点名称
	 */
	private String name;
	/**
	 * 父节点 id
	 */
	private Integer parentId;
	/**
	 * 层级
	 */
	private String level;
	/**
	 * 当前层级顺序
	 */
	private Integer seq;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 子节点列表
	 */
	private List<TreeNode> children = Lists.newArrayList();
}
