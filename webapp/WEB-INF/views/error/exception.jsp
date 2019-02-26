<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>죄송합니다.-${uri }</h1>
	<p>
	<!--  
		사용자 요청이 갑자기 많아져서<br>
		서비스에 일시적인 장애가 발생하였습니다.<br>
		잠시후 다시 시도해 주세요.
		-->
		<strong>예외발생</strong>
	</p>
	
	<p>
		==================================================<br>
	</p>
	
	<p>
		${exception };
	</p>
	
</body>
</html>