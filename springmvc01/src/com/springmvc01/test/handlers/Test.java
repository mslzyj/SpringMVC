package com.springmvc01.test.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {

	/**
	 * 1.ʹ��@RequestMapping("/test")ӳ�����URL
	 * 2.����ֵ��ͨ����ͼ����������Ϊʵ�ʵ�������ͼ������InternalResourceViewResolver��ͼ�������������µĽ�����
	 * ͨ��prefix+returnVal+��׺�����ķ�ʽ�õ�ʵ�ʵ�������ͼ��Ȼ��Ŷ��ת��
	 * /META-INF/views/success.jsp
	 * @return  
	 */
	
	@RequestMapping("/test")
	public String test() {
		System.out.println("Test SpringMvc");
		return "success";
	}
}
