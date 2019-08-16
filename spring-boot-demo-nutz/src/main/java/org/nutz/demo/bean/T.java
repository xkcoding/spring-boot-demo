package org.nutz.demo.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table("t_tt_ttt_tttt")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "creater", buildMethodName = "instance")
public class T extends Entity {

	@Name
	@Builder.Default
	String uuid = R.UU64();

	@Column("long_text")
	@ColDefine(width = 2000)
	@JsonField(ignore = true)
	@Builder.Default
	String longText = R.sg(20).next();

	@Column("t_tt_time")
	@JsonField(dataFormat = "yyyy-MM-dd HH:mm:ss")
	@Builder.Default
	Date date = Times.now();

	@Column("t_tt")
	TT tt;
}
