package com.douzone.dto;

public class JsonResult {
	private String result; //"success", "fail"
	private String message; // result 가 fail일때  에러내용
							//          success이면 null
	private Object data;   // result 가 fail일때  null
	                       //          success이면 객체를 보내라
	private JsonResult(String result,String message,Object data) {
		this.result=result;
		this.message=message;
		this.data=data;
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult("success", null, data);
	}
	public static JsonResult fail(String message) {
		return new JsonResult("fail", message, null);
	}
	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	
	
}
