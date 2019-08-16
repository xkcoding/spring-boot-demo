package org.nutz.demo.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.lang.Times;
import org.nutz.plugin.spring.boot.service.entity.DataBaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Entity extends DataBaseEntity {
	@Column("create_at")
	@Comment("数据创建时间")
	@Builder.Default
	Date createTime = Times.now();

	@Id
	long id;

	@Column("last_update")
	@Comment("数据最后更新时间")
	@Builder.Default
	Date lastUpdate = Times.now();

}
