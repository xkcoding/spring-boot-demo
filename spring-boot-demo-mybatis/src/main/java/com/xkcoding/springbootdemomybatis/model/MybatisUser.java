package com.xkcoding.springbootdemomybatis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "mybatis_user")
public class MybatisUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "tel")
	private String tel;

	@Column(name = "create_time")
	private Date createTime;
}
