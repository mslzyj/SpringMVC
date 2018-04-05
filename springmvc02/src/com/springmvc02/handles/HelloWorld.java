package com.springmvc02.handles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//��ʶ���������ʶΪ������
@Controller
public class HelloWorld {
	/**
	 * 1.ʹ��@RequestMappingע��ӳ��index.jsp�е�����hellworld����ִ������ķ���
	 * 2.����ֵ��ͨ����ͼ����������Ϊʵ�ʵ�������ͼ������InternalResourceViewResolver��ͼ��������
	 *   �������½�����
	 * 3.ͨ��         prefix(ǰ׺)+returnVal������ֵ��+��׺    �����ķ�ʽ�õ�ʵ�ʵ�������ͼ��
	 *   Ȼ����ת������
	 *   /WEB-INF/views/success.jsp
	 * @return
	 */
	@RequestMapping("/hellworld")  
	public String hello() {
		System.out.println("hello..");
		return "success";
	}
}
