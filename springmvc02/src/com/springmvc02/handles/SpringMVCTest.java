package com.springmvc02.handles;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.entities.User;

@SessionAttributes(value={"user"}, types={String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	/**
	 * 1.RequestMapping�������η��������������� 2.�ඨ�崦���ṩ����������ӳ����Ϣ�������WEBӦ�õĸ�Ŀ¼
	 * ���������ṩ��һ��������ӳ����Ϣ��������ඨ�崦��URL�����ඨ�崦δ��ע@requestMapping,
	 * �򷽷�����ǵ�URL�����WEBӦ�õĸ�Ŀ¼��
	 */
	private static final String SUCCESS = "success";
	
	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver(){
		System.out.println("testViewAndViewResolver");
		return SUCCESS;
	}
	/**
	 * 1. �� @ModelAttribute ��ǵķ���, ����ÿ��Ŀ�귽��ִ��֮ǰ�� SpringMVC ����! 
	 * 2. @ModelAttribute ע��Ҳ����������Ŀ�귽�� POJO ���͵����, �� value ����ֵ�����µ�����:
	 * 1). SpringMVC ��ʹ�� value ����ֵ�� implicitModel �в��Ҷ�Ӧ�Ķ���, ���������ֱ�Ӵ��뵽Ŀ�귽���������.
	 * 2). SpringMVC ��һ value Ϊ key, POJO ���͵Ķ���Ϊ value, ���뵽 request ��. 
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id, 
			Map<String, Object> map){
		System.out.println("modelAttribute method");
		if(id != null){
			//ģ������ݿ��л�ȡ����
			User user = new User(1, "Tom", "123456", "tom@atguigu.com", 12);
			System.out.println("�����ݿ��л�ȡһ������: " + user);
			
			map.put("user", user);
		}
	}
	
	/**
	 * ��������:
	 * 1. ִ�� @ModelAttribute ע�����εķ���: �����ݿ���ȡ������, �Ѷ�����뵽�� Map ��. ��Ϊ: user
	 * 2. SpringMVC �� Map ��ȡ�� User ����, ���ѱ���������������� User ����Ķ�Ӧ����.
	 * 3. SpringMVC ������������Ŀ�귽���Ĳ���. 
	 * 
	 * ע��: �� @ModelAttribute ���εķ�����, ���뵽 Map ʱ�ļ���Ҫ��Ŀ�귽��������͵ĵ�һ����ĸСд���ַ���һ��!
	 * 
	 * SpringMVC ȷ��Ŀ�귽�� POJO ������εĹ���
	 * 1. ȷ��һ�� key:
	 * 1). ��Ŀ�귽���� POJO ���͵Ĳ���ľ��ʹ�� @ModelAttribute ��Ϊ����, �� key Ϊ POJO ������һ����ĸ��Сд
	 * 2). ��ʹ����  @ModelAttribute ������, �� key Ϊ @ModelAttribute ע��� value ����ֵ. 
	 * 2. �� implicitModel �в��� key ��Ӧ�Ķ���, ������, ����Ϊ��δ���
	 * 1). ���� @ModelAttribute ��ǵķ������� Map �б����, �� key �� 1 ȷ���� key һ��, ����ȡ��. 
	 * 3. �� implicitModel �в����� key ��Ӧ�Ķ���, ���鵱ǰ�� Handler �Ƿ�ʹ�� @SessionAttributes ע������, 
	 * ��ʹ���˸�ע��, �� @SessionAttributes ע��� value ����ֵ�а����� key, ���� HttpSession ������ȡ key ��
	 * ��Ӧ�� value ֵ, ��������ֱ�Ӵ��뵽Ŀ�귽���������. �����������׳��쳣. 
	 * 4. �� Handler û�б�ʶ @SessionAttributes ע��� @SessionAttributes ע��� value ֵ�в����� key, ��
	 * ��ͨ������������ POJO ���͵Ĳ���, ����ΪĿ�귽���Ĳ���
	 * 5. SpringMVC ��� key �� POJO ���͵Ķ��󱣴浽 implicitModel ��, �����ᱣ�浽 request ��. 
	 * 
	 * Դ�������������
	 * 1. ���� @ModelAttribute ע�����εķ���. ʵ���ϰ� @ModelAttribute ������ Map �е����ݷ����� implicitModel ��.
	 * 2. ��������������Ŀ�����, ʵ���ϸ�Ŀ����������� WebDataBinder ����� target ����
	 * 1). ���� WebDataBinder ����:
	 * ��. ȷ�� objectName ����: ������� attrName ����ֵΪ "", �� objectName Ϊ������һ����ĸСд. 
	 * *ע��: attrName. ��Ŀ�귽���� POJO ����ʹ���� @ModelAttribute ������, �� attrName ֵ��Ϊ @ModelAttribute 
	 * �� value ����ֵ 
	 * 
	 * ��. ȷ�� target ����:
	 * 	> �� implicitModel �в��� attrName ��Ӧ������ֵ. ������, ok
	 * 	> *��������: ����֤��ǰ Handler �Ƿ�ʹ���� @SessionAttributes ��������, ��ʹ����, ���Դ� Session ��
	 * ��ȡ attrName ����Ӧ������ֵ. �� session ��û�ж�Ӧ������ֵ, ���׳����쳣. 
	 * 	> �� Handler û��ʹ�� @SessionAttributes ��������, �� @SessionAttributes ��û��ʹ�� value ֵָ���� key
	 * �� attrName ��ƥ��, ��ͨ�����䴴���� POJO ����
	 * 
	 * 2). SpringMVC �ѱ���������������� WebDataBinder �� target ��Ӧ������. 
	 * 3). *SpringMVC ��� WebDataBinder �� attrName �� target ���� implicitModel. 
	 * �������� request �������. 
	 * 4). �� WebDataBinder �� target ��Ϊ�������ݸ�Ŀ�귽�������. 
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user){
		System.out.println("�޸�: " + user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes ���˿���ͨ��������ָ����Ҫ�ŵ��Ự�е�������(ʵ����ʹ�õ��� value ����ֵ),
	 * ������ͨ��ģ�����ԵĶ�������ָ����Щģ��������Ҫ�ŵ��Ự��(ʵ����ʹ�õ��� types ����ֵ)
	 * 
	 * ע��: ��ע��ֻ�ܷ����������. ���������ηŷ���. 
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map){
		User user = new User(1, "zhangsan","123456","123@qq.com",18);
		map.put("user", user);
		map.put("school", "xianyoudian");
		return SUCCESS;
	}
	/**
	 * Ŀ�귽��������� Map ����(ʵ����Ҳ������ Model ���ͻ� ModelMap ����)�Ĳ���. 
	 * @param map
	 * @return
	 */
	@RequestMapping("/testMap")
	public String ThreadStateMap(Map<String, Object> map){
		System.out.println(map.getClass().getName());
		map.put("names", Arrays.asList("zhansan","Lisi","Wangwu"));
		return SUCCESS;
	}
	
	/**
	 * Ŀ�귽���ķ���ֵ������ ModelAndView ���͡� 
	 * ���п��԰�����ͼ��ģ����Ϣ
	 * SpringMVC ��� ModelAndView �� model �����ݷ��뵽 request �������. 
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName=SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		//���ģ�����ݵ�ModelAndView�С�
		modelAndView.addObject("time",  new Date());
		return modelAndView;
	}
	/**
	 * ����ʹ�� Serlvet ԭ���� API ��ΪĿ�귽���Ĳ��� ����֧����������
	 * 
	 * HttpServletRequest 
	 * HttpServletResponse 
	 * HttpSession
	 * java.security.Principal 
	 * Locale InputStream 
	 * OutputStream 
	 * Reader 
	 * Writer
	 * @throws IOException 
	 */
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request,
			HttpServletResponse response, Writer out) throws IOException {
		System.out.println("testServletAPI, " + request + ", " + response);
		out.write("hello springmvc");
//		return SUCCESS;
	}
	/**
	 * Spring MVC �ᰴ����������� POJO �����������Զ�ƥ�䣬 �Զ�Ϊ�ö����������ֵ��֧�ּ������ԡ�
	 * �磺dept.deptId��dept.address.tel ��
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user){
		System.out.println("testPojo: "+user);
		return SUCCESS;
	}
	
	/**
	 * @CookieValue:ӳ��һ��Cookileֵ������ͬ@RequestParam
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/testCookileValue")
	public String testCookileValue(@CookieValue("JSESSIONID") String sessionid){
		System.out.println("testCookileValue:sessionId: "+sessionid);
		return SUCCESS;
	}
	/**
	 * �˽�: ӳ������ͷ��Ϣ �÷�ͬ @RequestParam
	 */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(
			@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader, Accept-Language: " + al);
		return SUCCESS;
	}
	
	/**
	 * @RequestParam ��ӳ���������. value ֵ����������Ĳ����� required �ò����Ƿ����. Ĭ��Ϊ true
	 *               defaultValue ���������Ĭ��ֵ
	 */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="username") String un,@RequestParam(value="age") int age){
		System.out.println("testRequestParam,username: "+un+",age: "+age);
		return SUCCESS;
	}
	
	/**
	 * Rest ���� URL. �� CRUD Ϊ��: ����: /order POST �޸�: /order/1 PUT update?id=1 ��ȡ:
	 * /order/1 GET get?id=1 ɾ��: /order/1 DELETE delete?id=1
	 * 
	 * ��η��� PUT ����� DELETE ������ ? 1. ��Ҫ���� HiddenHttpMethodFilter 2. ��Ҫ���� POST ����
	 * 3. ��Ҫ�ڷ��� POST ����ʱЯ��һ�� name="_method" ��������, ֵΪ DELETE �� PUT
	 * 
	 * �� SpringMVC ��Ŀ�귽������εõ� id ��? ʹ�� @PathVariable ע��
	 * 
	 */
	@RequestMapping(value = "/testRestPut/{id}", method = RequestMethod.PUT)
	public String testRestPut(@PathVariable Integer id) {
		System.out.println("testRest Put: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRestDelete/{id}", method = RequestMethod.DELETE)
	public String testRestDelete(@PathVariable Integer id) {
		System.out.println("testRest Delete: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest", method = RequestMethod.POST)
	public String testRest() {
		System.out.println("testRest POST");
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
	public String testRest(@PathVariable Integer id) {
		System.out.println("testRest GET: " + id);
		return SUCCESS;
	}
	
	/**
	 * @PathVariable ������ӳ��URL�е�ռλ����Ŀ�귽���Ĳ�����
	 * @param id
	 * @return
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id){
		System.out.println("testPathVariable"+id);
		return SUCCESS;
	}
	
	/**
	 * @RequestMapping֧��ͨ��������������*����ӳ���ʱ��������дһЩ�ַ���
	 * @return
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath(){
		System.out.println("testAndPath");
		return SUCCESS;
	}
    
	/**
	 * ����ʹ��params��headers�����Ӿ�ȷ��ӳ������params��handers֧�ָ��Ӿ�ȷ�ı��ʽ��
	 * ����headers��ֵ������"Accept-Language:zh-CN,zh;q=0.8"
	 * @return
	 */
	@RequestMapping(value="testParamsAndHeaders",
		params={"username","age!=11"},headers={"Accept-Language:zh-CN,zh;q=0.8"})
	public String stParamsAndHeaders(){
		System.out.println("stParamsAndHeaders..");
		return SUCCESS;
	}
	
	/**
	 * ʹ��method������ָ������ʽ
	 * @return
	 */
	@RequestMapping(value = "/testMethod",method=RequestMethod.POST)
	public String testMathod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping..");
		return SUCCESS;
	}
}
