package com.xkcoding.springbootdemocacheredis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1442134563392432775L;
	private Long id;
	private String name;
	private Date birthDay;
}
