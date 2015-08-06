package net.aimeizi.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.aimeizi.dubbo.entity.User;
import net.aimeizi.dubbo.service.DemoService;
import net.aimeizi.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 *
 * dubbo ������
 *
 * @Reference ע����Ҫ�� dubbo consumer������������
 *
 * <dubbo:annotation/>
 *	<context:component-scan base-package="net.aimeizi.dubbo.controller">
 *	<context:include-filter type="annotation" expression="com.alibaba.dubbo.config.annotation.Reference"/>
 * </context:component-scan>
 *
 * ��Ҫʹ��@Autowired��@Resourceע����Ҫ��ʽ����bean
 *
 * ʹ��@Autowired��@Resourceע��ʱ��Ҫʹ��dubbo:reference������
 * <dubbo:reference interface="net.aimeizi.dubbo.service.UserService" id="userService"/>
 * <dubbo:reference interface="net.aimeizi.dubbo.service.DemoService" id="demoService"/>
 *
 * ���ϵ����þ���Ҫ��spring mvc��DispatcherServlet��������ʽ����dubbo consumer������.��/WEB-INF/applicationContext-dubbo-consumer.xml ������Controller�з���NullPointException
 * <servlet>
 *	<servlet-name>mvc-dispatcher</servlet-name>
 *		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *	<init-param>
 *	<param-name>contextConfigLocation</param-name>
 *		<param-value>/WEB-INF/applicationContext*.xml,/WEB-INF/mvc-dispatcher-servlet.xml</param-value>
 *	</init-param>
 *	<load-on-startup>1</load-on-startup>
 * </servlet>
 *
 */
@Controller
public class HelloController {

	@Reference
//	@Autowired
//	@Resource
	private DemoService demoService;

	@Reference
//	@Autowired
//	@Resource
	private UserService userService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}

	@RequestMapping(value = "/testDemo", method = RequestMethod.POST)
	public String getEnteredWordLength(ModelMap model, @RequestParam("username") String enteredWord) {
		int length = this.demoService.getLength(enteredWord);
		model.addAttribute("length", length);
		return "helloRes";
	}

	@RequestMapping(value = "/userAddView", method = RequestMethod.GET)
	public String userAddView (){
		return "userAdd";
	}
	
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public String userAdd(Model model, String userName,String userEnName, String country, String company) {
		User user = new User();
		user.setUserName(userName);
		user.setUserEnName(userEnName);
		user.setCompany(company);
		User saveUser = this.userService.save(user);
		model.addAttribute("saveUser", saveUser);
		return "userAddRes";
	}
}