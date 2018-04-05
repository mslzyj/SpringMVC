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
	 * 由@ModelAttribute标记的方法，会在每个目标方法执行之前被SpringMVC调用
	 * @param user
	 * @return
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		System.out.println("modelModelAttribute method");
		if (id != null) {
			// 从数据库中获取对象
			User user = new User(1, "BB", "123456", "BB@qq.com", 12, null);
			map.put("user", user);
		}
	}

	/**
	 * 运行流程： 1.执行@ModelAttribute注解修饰的方法：从数据库中取出对象，将对象放到Map中，键为user
	 * 2.Springmvc从map中取出user对象，并把表单的请求参数付给该User对象的对应属性
	 * 3.SpringMVC把上述对象传入目标方法的参数
	 * 4.在@ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法传入参类型的第一个小写的字符串一致。
	 * 即map.put("user", user);中的"user"和public String testModelAttribute(User
	 * user)中的user一致
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user) {
		System.out.println("修改： " + user);
		return SUCCESS;
	}

	/**
	 * @SessionAttributes除了可以通过属性名指定需要放到回话中的属性外（实际上使用的是value属性值） 还可以通过模型属性的对象类型指定哪些模型属性需要放到回话中（实际上使用的是types属性值）
	 * 
	 *                                                           该注解只能放到类的上面，而不能放到方法的上面
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
	 * 目标方法可以添加map类型的参数
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
	 * 目标方法的返回值可以是modelAndView类型 其中可以包含视图和模型信息
	 * SpringMvc会把ModelAndView的model中的额数据放到request域对象中
	 * 
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		// 添加模型数据到ModelAndView中
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}

	/**
	 * 可以使用Servlet的原生的API的目标方法作为参数，具体支持以下类型： HttpServletRequest
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
	 * Spring MVC会按照请求参数名和POJO属性名进行自动匹配。 自动为该属性填充属性值，支持级联属性
	 * 如：dept.deptId,dept.address.tel等
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
	 * @CookieValue:映射一个Cook值，属性同@RequestParam
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("testCookieValue:sessionId: " + sessionId);
		return SUCCESS;
	}

	/**
	 * 用法同@RequestParam,映射请求头信息
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
	 * 如果将age值去掉,则： public String
	 * testRequestParam(@RequestParam(value="username") String
	 * un,@RequestParam(value="age") int age)改为 public String
	 * testRequestParam(@RequestParam(value="username") String
	 * un,@RequestParam(value="age",required=false,defaultValue="0") int
	 * age),如果不加defaultValue="0"则需要将int改为Integer
	 * 
	 * @RequestParam映射请求参数，其中value值，即为请求参数的参数名 required:该参数是否为必须，默认为true
	 *                                         defaultValue请求参数的默认值
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
	 * Rest风格为URl 以CRUD为例： 新增： /order POST 修改： /order /1 PUT update?id=1 获取：
	 * /order /1 DET get?id=1 删除： /order /1 DELETE delete?id=1
	 * 
	 * 如何发送PUT请求和DELETE请求？ 1.需要配置HidenHttpMethodFiolter 2.需要发送POST请求
	 * 3.需要在发送POST请求一个带name="_method"的隐藏域，值为DELETE后PUT
	 * 
	 * 在SpringMVC的目标方法中得到id: 使用@PathVariable 注解.
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
	 * @PathVariable可以离开映射URL中的占位符到目标方法的参数中。
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
	 * 了解：可以使用params和headers来更加精确的映射请求，params和heasers支持简单的表达式
	 * 
	 * @return
	 */
	@RequestMapping(value = "testParamsAndHeaders", params = { "username", "age!=10" })
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}

	/**
	 * 使用method属性来指定请求方式：常用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testmethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	/**
	 * 1.@RequestMapping除了修饰方法，还可以修饰类 2.区别： （1）类定义处：提供初步的请求信息，相对于WEB应用的根目录
	 * （2）方法处：提供进一步的细分映射信息，相对于类定义处的URL，若类定义处未标注@RequestMapping
	 * ，则方法处标记的URL相对于WEB应用的根目录。
	 * 
	 * @return
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
}
