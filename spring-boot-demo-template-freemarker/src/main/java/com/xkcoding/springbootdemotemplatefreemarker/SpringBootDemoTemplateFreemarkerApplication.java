package com.xkcoding.springbootdemotemplatefreemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SpringBootApplication
public class SpringBootDemoTemplateFreemarkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoTemplateFreemarkerApplication.class, args);
	}

	@GetMapping({"","/"})
	public ModelAndView home(){
		return new ModelAndView("index");
	}
}
