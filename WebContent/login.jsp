<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function fnJoin(){
	location.href="/mvcWebBoard/joinForm.log";
}

function fnAlert(arg){
	if(arg == -1){
		alert("ID가 존재하지 않습니다.");
	}else if( arg == 0){
		alert("비밀번호가 일치하지 않습니다.");
	}else if( arg == 2){
		alert("가입실패 ID가 존재합니다.")
	}else if(arg == 3){
		alert("SYSTEM 에러 다시 시도해주세요.")
	}
}
</script>
</head>
<body>
	<script type="text/javascript">
		fnAlert(<%=request.getAttribute("alert")%>)
	</script>
	<form action="loginOk.log" method="post">
		<!--session객체에 id값이 저장되어있을 때는 id input 태그에 값이 보여짐 -->
		ID : <input type="text" name="id" value=<% if(session.getAttribute("id") != null) out.println("id"); %>><br/>
		PW : <input type="password" name="pw"><br/>
		<input type="submit" value="login">&nbsp;&nbsp;
		<!-- javascript를 사용하여 회원가입 버튼을 클릭하면 joinForm.jsp 페이지로 이동시킴 -->
		<input type="button" value="회원가입" onclick="fnJoin()">
	</form>
</body>
</html>