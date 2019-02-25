package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Myintercetor01 implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// handler호출 전 ( 요청전)
		// handler 호출 여부를 결정(boolean반환값에 따라)
		System.out.println("Myintercetor01 : preHandle");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler 호출된 후 / 호출이 된 후라 return이 없음 ( 응답중)
 		System.out.println("Myintercetor01 : postHandle");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
