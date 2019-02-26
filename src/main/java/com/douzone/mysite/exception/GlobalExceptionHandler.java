package com.douzone.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice //AOP코드가 작동하는데 콘트롤러 메소드에 시점을 기록해놓고 AOP하는 것
public class GlobalExceptionHandler {
	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request,HttpServletResponse response,Exception e) throws IOException, ServletException {
		
		//1.로깅작업
		StringWriter errors=new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error( errors.toString() );
		
		//2.시스템 오류 안내
//		ModelAndView mav=new ModelAndView();
//		mav.addObject("errors",errors.toString());
//		mav.setViewName("error/exception");
//		return mav;
		String accept= request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {/*json응답*/
			response.setStatus(HttpServletResponse.SC_OK);
			
			OutputStream out= response.getOutputStream();
			JsonResult jsonResult= JsonResult.fail(errors.toString());
			
			out.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes("UTF-8"));
			out.flush();
			out.close();
			
		}else { /*HTML응답*/
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
			// (request,response);
		}
	}
}
