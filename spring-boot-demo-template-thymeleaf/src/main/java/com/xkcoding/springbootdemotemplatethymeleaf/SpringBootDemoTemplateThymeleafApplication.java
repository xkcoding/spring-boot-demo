package com.xkcoding.springbootdemotemplatethymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
public class SpringBootDemoTemplateThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoTemplateThymeleafApplication.class, args);
	}

	@GetMapping({"", "/"})
	public ModelAndView index(@RequestParam(defaultValue = "thymeleaf") String name) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", name);
		mv.setViewName("index");
		return mv;
	}
}
