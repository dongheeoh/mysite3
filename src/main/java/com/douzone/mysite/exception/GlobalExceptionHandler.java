package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //AOP코드가 작동하는데 콘트롤러 메소드에 시점을 기록해놓고 AOP하는 것
public class GlobalExceptionHandler {
	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(
			HttpServletRequest request,Exception e) {
		
		//1.로깅작업
		StringWriter errors=new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error( errors.toString() );
		
		//2.시스템 오류 안내
		ModelAndView mav=new ModelAndView();
		mav.addObject("errors",errors.toString());
		mav.setViewName("error/exception");
		return mav;
	}
}
