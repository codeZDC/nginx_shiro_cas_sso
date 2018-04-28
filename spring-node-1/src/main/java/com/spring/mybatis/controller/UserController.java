package com.spring.mybatis.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.mybatis.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Value("${shiro.logoutUrl}")
	private String shiro_logoutUrl;
	@Value("${cas.nodes}")
	private String cas_nodes;

	@RequestMapping("/loginSuccess")
	public String loginSuccess() {

		logger.info("登录成功");

		return "../index";
	}

	// 让cookie失效
	@RequestMapping("/clearCookies")
	public String clearCookies(HttpServletRequest request,HttpServletResponse response) {
		//session.invalidate();
		
		//return " node1   session已经失效!";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("codeZ_node1")||cookie.getName().equals("codeZ_node2")){
				cookie.setMaxAge(0);
				cookie.setPath("/node");
				cookie.setValue("codeZ_clear");
				response.addCookie(cookie);
			}/*else	if(cookie.getName().equals("JSESSIONID")){
				cookie.setMaxAge(520);
				cookie.setPath("/cas");
				cookie.setValue("codeZ_clear");
				response.addCookie(cookie);
			}*/
		}
		// 跳转到cas注销页面
		return "redirect:/logout";
		
	}

	@RequestMapping("preLogout")
	public String prelogout() {
		String[] urls = cas_nodes.split(",");
		// 首先将集群中的会话全部失效掉
		for (String url : urls) {
			System.err.println(url+"正在清除... ");
			System.err.println(HttpClientUtil.doPost(url, null));;
		}
		
		// 跳转到cas注销页面
		return "redirect:/logout";
	}

}
