<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- js 파일의 경우 절대경로로 지정해주어야 한다. -->
<script src="/mvcWebBoard/js/member.js"></script> 
</head>
<body>
	<form action="joinOk.log" method="post" name="reg_form">
			ID : <input type="text" name="id" size="20"><br/>
			PW : <input type="password" name="pw" size="20"><br />
			PW Confirm : <input type="password" name="pw_check" size="20"><br />
			Name : <input type="text" name="name" size="20"><br />
			Email : <input type="text" name="eMail" size="20"><br />
			Address : <input type="text" name="address" size="50"><br />
			<input type="button" value="회원가입" onclick="infoConfirm()"> &nbsp;&nbsp;&nbsp;
			<input type="reset" value="취소" onclick="javascript:window.location='login.log'">
	</form>

</body>
</html>