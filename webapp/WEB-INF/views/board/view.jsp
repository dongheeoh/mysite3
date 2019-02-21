<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${view.title } [글쓴이 : ${view.name}]</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${view.contents }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/list">글목록</a>
					<a href="${pageContext.servletContext.contextPath }/board/modifyform?no=${view.no}">글수정</a>
					<a href="${pageContext.servletContext.contextPath }/board/replyform?no=${view.no}">답글달기</a>		
				</div>
				
				
			<!-- 댓글보여주기 -->
				<c:set var="count" value="0"/>		
				<c:set var="commentcount" value="${fn:length(commentlist)}"/>
				<hr>
					<br/>
					<strong>댓글(${commentcount }개)</strong>			
				<c:forEach items="${commentlist}" var="vo" varStatus="status">
				<hr>
				  <ol>
					<li>
					
						
					
						<table style="padding-left:${15*vo.depth}px">
							<tr >
								<td >
									<c:if test="${vo.depth > 0}" >
										<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
									</c:if>
									[${ count = count+1 }] |
								 </td>
							<c:choose>
								<c:when test="${ vo.userNo > 0 }">			 
									<td style="color:red"><strong>${vo.name }</strong></td>
								</c:when>
								<c:otherwise>
									<td>${vo.name }</td>
								</c:otherwise>
							</c:choose>	 			
								<td> | ${vo.writeDate }</td>
								<td><a href="${pageContext.servletContext.contextPath }/board/commentdeleteform?no=${vo.no}&viewno=${view.no}">삭제</a></td>
								<td><a href="${pageContext.servletContext.contextPath }/board/commentmodifyform?no=${vo.no}&viewno=${view.no}">수정</a></td>
								<td><a href="${pageContext.servletContext.contextPath }/board/commentreplyform?no=${vo.no}&viewno=${view.no}">대댓글</a></td>
							</tr>
							<tr>
								<td colspan=4>
								${fn:replace(vo.contents,  newline, "<br>") }
								</td>
							</tr>
						</table>
									
						<br>
					</li>
				  </ol>
				</c:forEach>
			<!--  -->
				
			<!-- 댓글폼 로그인 유무에따라 -->
				<c:choose>
					<c:when test="${ empty authuser }">
						<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board">
							<input type = "hidden" name = "a" value="comment">
							<input type = "hidden" name = "no" value="${param.no}">
							<input type = "hidden" name = "kwd" value="${param.kwd}">
							
							<table class="tbl-ex">
								
								<tr>
									<td>닉네임 : <input type="text" name="name" value=""></td>
								</tr>
								<tr>
									<td>비밀번호 :<input type="password" name="password" value=""></td>
								</tr>
								
								<tr>
									<td>
										<textarea id="content" name="contents"></textarea>
									</td>
								</tr>
								
							</table>
							<input type="submit" value="댓글달기">
						</form>
					</c:when>
					
					<c:otherwise>
						<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board">
							<input type = "hidden" name = "a" value="comment">
							<input type = "hidden" name = "no" value="${view.no}">
							
							<table class="tbl-ex">
								
								<tr>
									<td>닉네임 : ${authuser.name }</td>
								</tr>			
								<tr>
									<td>
										<textarea id="content" name="contents"></textarea>
									</td>
								</tr>
								
							</table>
							<input type="submit" value="댓글달기">
						</form>
					</c:otherwise>
					
				</c:choose>
			<!-- 댓글폼 -->
				
			</div>
		</div>
		
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
				<c:param name="menu" value="board"/>
			</c:import>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>	
		</div>
		
	</div>
</body>
</html>