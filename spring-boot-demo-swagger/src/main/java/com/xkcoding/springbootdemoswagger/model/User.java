package com.xkcoding.springbootdemoswagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户基本信息")
public class User {
	private Long id;

	@ApiModelProperty("姓名")
	@Size(max = 20)
	private String name;

	@Max(value = 30, message = "年龄小于30岁才可以！")
	@Min(value = 18, message = "你必须成年才可以！")
	private Integer age;

	@Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式错误！")
	private String email;

	@Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "请输入正确的手机格式！")
	private String phone;
}
