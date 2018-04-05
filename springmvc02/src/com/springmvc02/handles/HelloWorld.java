package com.springmvc02.handles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//标识，将此类标识为控制器
@Controller
public class HelloWorld {
	/**
	 * 1.使用@RequestMapping注解映射index.jsp中的请求（hellworld），执行下面的方法
	 * 2.返回值会通过视图解析器解析为实际的物理视图，对于InternalResourceViewResolver视图解析器，
	 *   会做如下解析：
	 * 3.通过         prefix(前缀)+returnVal（返回值）+后缀    这样的方式得到实际的物理视图，
	 *   然后做转发操作
	 *   /WEB-INF/views/success.jsp
	 * @return
	 */
	@RequestMapping("/hellworld")  
	public String hello() {
		System.out.println("hello..");
		return "success";
	}
}
