package com.springmvc01.test.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {

	/**
	 * 1.使用@RequestMapping("/test")映请求的URL
	 * 2.返回值会通过视图解析器解析为实际的区里视图，对于InternalResourceViewResolver视图解析器会做如下的解析：
	 * 通过prefix+returnVal+后缀这样的方式得到实际的物理视图，然悔哦做转发
	 * /META-INF/views/success.jsp
	 * @return  
	 */
	
	@RequestMapping("/test")
	public String test() {
		System.out.println("Test SpringMvc");
		return "success";
	}
}
