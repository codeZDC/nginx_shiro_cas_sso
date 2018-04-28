package com.spring.mybatis.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;

public class MyFilter extends LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest req, ServletResponse res) throws Exception {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("codeZ_node1")||cookie.getName().equals("codeZ_node2")){
				cookie.setMaxAge(0);
				cookie.setPath("/node");
				cookie.setValue("codeZ_clear");
				response.addCookie(cookie);
			}
		}
		// TODO Auto-generated method stub
		return super.preHandle(request, response);
	}
}

