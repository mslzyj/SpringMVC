package com.springmvc01.test.handlers;

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

@SessionAttributes(value = { "user" }, types = { String.class })
@RequestMapping("/springmvc")
@Controller
public class SpringMvcTest {
	private static final String SUCCESS = "success";

	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver() {
		System.out.println("testViewAndViewResolver");
		return SUCCESS;
	}

	/**
	 * ��@ModelAttribute��ǵķ���������ÿ��Ŀ�귽��ִ��֮ǰ��SpringMVC����
	 * @param user
	 * @return
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		System.out.println("modelModelAttribute method");
		if (id != null) {
			// �����ݿ��л�ȡ����
			User user = new User(1, "BB", "123456", "BB@qq.com", 12, null);
			map.put("user", user);
		}
	}

	/**
	 * �������̣� 1.ִ��@ModelAttributeע�����εķ����������ݿ���ȡ�����󣬽�����ŵ�Map�У���Ϊuser
	 * 2.Springmvc��map��ȡ��user���󣬲��ѱ����������������User����Ķ�Ӧ����
	 * 3.SpringMVC������������Ŀ�귽���Ĳ���
	 * 4.��@ModelAttribute���εķ����У����뵽Mapʱ�ļ���Ҫ��Ŀ�귽����������͵ĵ�һ��Сд���ַ���һ�¡�
	 * ��map.put("user", user);�е�"user"��public String testModelAttribute(User
	 * user)�е�userһ��
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user) {
		System.out.println("�޸ģ� " + user);
		return SUCCESS;
	}

	/**
	 * @SessionAttributes���˿���ͨ��������ָ����Ҫ�ŵ��ػ��е������⣨ʵ����ʹ�õ���value����ֵ�� ������ͨ��ģ�����ԵĶ�������ָ����Щģ��������Ҫ�ŵ��ػ��У�ʵ����ʹ�õ���types����ֵ��
	 * 
	 *                                                           ��ע��ֻ�ܷŵ�������棬�����ܷŵ�����������
	 * @param map
	 * @return
	 */
	@RequestMapping("testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user = new User(1, "AA", "123", "123@qq.com", 11, null);
		map.put("user", user);
		map.put("school", "xian");
		return SUCCESS;
	}

	/**
	 * Ŀ�귽���������map���͵Ĳ���
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		System.out.println(map.getClass().getName());
		map.put("names", Arrays.asList("AA", "BB", "CC"));
		return SUCCESS;
	}

	/**
	 * Ŀ�귽���ķ���ֵ������modelAndView���� ���п��԰�����ͼ��ģ����Ϣ
	 * SpringMvc���ModelAndView��model�еĶ����ݷŵ�request�������
	 * 
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		// ���ģ�����ݵ�ModelAndView��
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}

	/**
	 * ����ʹ��Servlet��ԭ����API��Ŀ�귽����Ϊ����������֧���������ͣ� HttpServletRequest
	 * HttpServletRsponse HttpSession java.security.Principal Locale InputStream
	 * OutputStream Reader Writer
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/testServletAPI2")
	public String testServletAPI2(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("testServletAPI," + request + "," + response);
		return SUCCESS;
	}

	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer out)
			throws IOException {
		System.out.println("testServletAPI2:" + request + "," + response);
		out.write("test writer");
	}

	/**
	 * Spring MVC�ᰴ�������������POJO�����������Զ�ƥ�䡣 �Զ�Ϊ�������������ֵ��֧�ּ�������
	 * �磺dept.deptId,dept.address.tel��
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo: " + user);
		return SUCCESS;
	}

	/**
	 * @CookieValue:ӳ��һ��Cookֵ������ͬ@RequestParam
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("testCookieValue:sessionId: " + sessionId);
		return SUCCESS;
	}

	/**
	 * �÷�ͬ@RequestParam,ӳ������ͷ��Ϣ
	 * 
	 * @param al
	 * @return
	 */
	@RequestMapping("testRequestHeader")
	public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader,Accept-Language:" + al);
		return SUCCESS;
	}

	/**
	 * �����ageֵȥ��,�� public String
	 * testRequestParam(@RequestParam(value="username") String
	 * un,@RequestParam(value="age") int age)��Ϊ public String
	 * testRequestParam(@RequestParam(value="username") String
	 * un,@RequestParam(value="age",required=false,defaultValue="0") int
	 * age),�������defaultValue="0"����Ҫ��int��ΪInteger
	 * 
	 * @RequestParamӳ���������������valueֵ����Ϊ��������Ĳ����� required:�ò����Ƿ�Ϊ���룬Ĭ��Ϊtrue
	 *                                         defaultValue���������Ĭ��ֵ
	 * @param un
	 * @param age
	 * @return
	 */
	@RequestMapping(value = "/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un, @RequestParam(value = "age") int age) {
		System.out.println("testRequestParam,username: " + un + ",age: " + age);
		return SUCCESS;
	}

	/**
	 * Rest���ΪURl ��CRUDΪ���� ������ /order POST �޸ģ� /order /1 PUT update?id=1 ��ȡ��
	 * /order /1 DET get?id=1 ɾ���� /order /1 DELETE delete?id=1
	 * 
	 * ��η���PUT�����DELETE���� 1.��Ҫ����HidenHttpMethodFiolter 2.��Ҫ����POST����
	 * 3.��Ҫ�ڷ���POST����һ����name="_method"��������ֵΪDELETE��PUT
	 * 
	 * ��SpringMVC��Ŀ�귽���еõ�id: ʹ��@PathVariable ע��.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/testRest.do/{id}", method = RequestMethod.GET)
	public String restPut(@PathVariable int id) {
		System.out.println("putget " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
	public String restDelete(@PathVariable int id) {
		System.out.println("delete " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest", method = RequestMethod.POST)
	public String testRest() {
		System.out.println("testRest  POST");
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
	public String testRest(@PathVariable Integer id) {
		System.out.println("testRest  GET:" + id);
		return SUCCESS;
	}

	/**
	 * @PathVariable�����뿪ӳ��URL�е�ռλ����Ŀ�귽���Ĳ����С�
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/tetsPathVariable/{id}")
	public String tetsPathVariable(@PathVariable("id") Integer id) {
		System.out.println("tetsPathVariable:" + id);
		return SUCCESS;
	}

	@RequestMapping("/tetsAntPath/*/abc")
	public String tetsAntPath() {
		System.out.println("tetsAntPath");
		return SUCCESS;
	}

	/**
	 * �˽⣺����ʹ��params��headers�����Ӿ�ȷ��ӳ������params��heasers֧�ּ򵥵ı��ʽ
	 * 
	 * @return
	 */
	@RequestMapping(value = "testParamsAndHeaders", params = { "username", "age!=10" })
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}

	/**
	 * ʹ��method������ָ������ʽ������
	 * 
	 * @return
	 */
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testmethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	/**
	 * 1.@RequestMapping�������η����������������� 2.���� ��1���ඨ�崦���ṩ������������Ϣ�������WEBӦ�õĸ�Ŀ¼
	 * ��2�����������ṩ��һ����ϸ��ӳ����Ϣ��������ඨ�崦��URL�����ඨ�崦δ��ע@RequestMapping
	 * ���򷽷�����ǵ�URL�����WEBӦ�õĸ�Ŀ¼��
	 * 
	 * @return
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
}
