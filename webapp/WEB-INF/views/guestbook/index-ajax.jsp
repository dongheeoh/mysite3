<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
	paddiong:10px;
	font-weight:bold;
	font-size : 1.0em;
}
#dialog-delete-form input[type="password"]{
	padding:5px;
	outline : none;
	width : 180px;
	border:1px solid #888;
} 
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
//jquery plug-in 추가
/* (function($){ //$ 걍변수임 jquery객체니깐 이래씀
	$.fn.hello = function(){
		console.log( $(this).attr("id") + "------> hello" );
	}				    //딱 이걸쓴 엘레먼트가 this가되겠찌
})(jQuery);
 */

var page = 0;
var isEnd = false; //끝인가? 
		
var messageBox = function(title,message){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);	
	$("#dialog-message").dialog({
		modal:true,
		buttons:{
			"확인": function(){
				$(this).dialog("close");
			}
		}
	});	
}

var render = function(vo, mode){
	//현업에가면 이렇게 안한다. -> 템플릿(template) 라이브러리를 써서 빼낸다.
	// ex) ejs, underscore, mustache 
	var htmls = 
		"<li data-no='"+ vo.no + "'>" + //data-no왜? 삭제할때 써야지!  
			"<strong>"+ vo.name +"</strong>"+
			"<p>"+ vo.text + "</p>" +
			"<strong></strong>"+
			"<a href='' data-no='"+ vo.no +"'>삭제</a>"+ //중요함 동적인상황에서 삭제를한다?? 없는상태에서 삭제를한다? 
		"</li>";										
		
	if(mode == true){
		$("#list-guestbook").prepend(htmls);		
	}else{	
		$("#list-guestbook").append(htmls);
	}
}

var fetchList = function(){
	if(isEnd == true){
		return;
	}
	++page;
	$.ajax({
		url:"/mysite2/api/guestbook?a=ajax-list&p="+page, //리퀘스트 매핑,파라미터받고하면 ? a=list이딴거안해도됨 프리티 url가능 
		type:"get",
		dataType:"json",
		data:"",//잭슨이바ㅜ꺼줌?먼솔
		success: function(response){
			
			if(response.result =="fail"){
				console.warn(response.data);
				return;
			}
			console.log(response.data);
			//페이지 끝 검출
			if(response.data.length <5 ){
				isEnd =true;
				$("#btn-next").prop("disabled", true);
			}
			
			//rendering
			$.each(response.data, function(index, vo){
				render(vo, false);
			});
			
		},
		error:function(xhr, status, e){ // xhr왜안씀? 객체니깐  통신더할려면 쓰던가. 근데 지금은 에러만출력하고 끝내니깐 
			console.error(status + ":" + e);
		}
	});
}
var pushPost = function(){
	
	//validate form data 유효성검사
	var name = $("#input-name").val();
	var message = $("#tx-content").val();
	var password = $("#input-password").val();

	if(name == ""){
		messageBox("글남기기", "이름은 필수 입력 항목입니다.");
		return;
	} 
	if(message == ""){
		messageBox("글남기기", "메세지는 필수 입력 항목입니다.");
		return;
	} 
	if(password == ""){
		messageBox("글남기기", "패스워드는 필수 입력 항목입니다.");
		return;
	} 
	
	$.ajax({
		url:"/mysite2/api/guestbook", //리퀘스트 매핑,파라미터받고하면 ? a=list이딴거안해도됨 프리티 url가능 
		type:"post",
		dataType:"json",
		data:"a=ajax-insert&name="+ name +
					   "&message=" + message +
					   "&password=" + password ,
					   
		success: function(response){
			
 			if(response.result =="fail"){
				console.warn(response.data);
				return;
			}			
			//rendering
			$( "#add-form" )[0].reset();
			render(response.data, true);
			
		},
		error:function(xhr, status, e){ // xhr왜안씀? 객체니깐  통신더할려면 쓰던가. 근데 지금은 에러만출력하고 끝내니깐 
			console.error(status + ":" + e);
		}
	});
	
}
var deletePost = function(){
	
	//validate form data 
	var no = $("#hidden-no").val();
	var password = $("#password-delete").val();	
	
  	$.ajax({
		url:"/mysite2/api/guestbook", 
		type:"post",
		dataType:"json",
		data:"a=ajax-delete&no="+ no +
				    "&password=" + password,
					   
		success: function(response){
			
 			if(response.result =="fail"){
				console.warn(response.data);
				return;
			}			
 			
 			if(response.data == "-1"){
				console.log(response.data); 
 				$(".error").attr("style", "displa:block").html();
 				$("#dialog-delete-form input[type='password']").val('');
 				
 			}else{
				//remove
				console.log("삭제됨 : " + response.data); 
				$("#list-guestbook li[data-no='"+ response.data +"']").remove();
				$("#dialog-delete-form").dialog("close");
 			}		
		},
		error:function(xhr, status, e){ // xhr왜안씀? 객체니깐  통신더할려면 쓰던가. 근데 지금은 에러만출력하고 끝내니깐 
			console.error(status + ":" + e);
		}
	}); 	
}
//---------------------------------------------------------------

$(function(){	
	//최초리스트 가져오기 처음 5개가져오기
	fetchList(); 	
	
	//보내기 (타임라인작성)
	$("#add-form").submit(function(event){
		//submit의 기본동작(post) 막기. 
		event.preventDefault();
		pushPost();
	});

	//무한스크롤링
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		if( scrollTop + windowHeight + 10 > documentHeight){
			fetchList();
		}
	}); 
	
	//삭제처리
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen:false,
		modal: true,
		buttons:{
			"삭제" : function(){
				console.log("ajax 삭제 작업");
				//$.ajax({})
				//결과를보고 다이얼로그를 닫아라
				//ajax이좋은점?? 웹에서는 리다이렉트해서뭐근ㅁ럼나렇
				deletePost();				
			}, 
			"취소" : function(){
				dialogDelete.dialog("close");
			}
		},
		close : function(){
			console.log("close시 뒤처리");
			$(".error").attr("style", "display:none");
			$("#dialog-delete-form input[type='password']").val('');
		}
	});
	
	// live event : 생성된엘리먼트에 이벤트달기
	$(document).on("click", "#list-guestbook li a", function(event){
		event.preventDefault(); //이걸달아놔야 이동안한다.
		console.log("여기 " + $(this));
		console.log("clicked!!" + "::" + $(this).data("no"));
		$("#hidden-no").attr( "value", $(this).data("no"));
		dialogDelete.dialog("open");	
	}); //도큐먼트에게 클릭이벤트를거는거임
	
})
</script>
</head>
<body>
	<div id="container">

		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp" />
		</div>

		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				
				<button id="btn-next">다음</button>
				
				<ul id="list-guestbook">
					<!-- 여기추가됨  -->
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password"  id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding-top:40px;"></p>
			</div>						
		</div>

		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
				<c:param name="menu" value="guestbook-ajax"/>
			</c:import>
		</div>

		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp" />
		</div>
	</div>
</body>
</html>