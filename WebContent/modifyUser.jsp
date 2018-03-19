<%@page import="com.javalec.ex.dto.MDto"%>
<%@page import="com.javaex.ex.dao.MDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
	String id = (String)session.getAttribute("id"); // session객체에 저장된 id값을 가져옴
	MDao dao = MDao.getInstance(); // MDao의 객체를 가져옴
	MDto dto = dao.getMember(id); // id 값에 해당하는 dto 객체를 가져옴
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- js 파일의 경우 절대경로로 지정해주어야 한다. -->
<script src="/mvcWebBoard/js/member.js"></script> 
</head>
<body>
	<form action="modifyOk.log" method="post" name="reg_form">
		ID : <%= dto.getId() %><br/>
		PW : <input type="password" name="pw" size="20"><br />
		PW Confirm : <input type="password" name="pw_check" size="20"><br />
		Name : <%=dto.getName() %><br />
		Email : <input type="text" name="eMail" size="20" value=<%= dto.geteMail() %>><br />
		Address : <input type="text" name="address" size="50" value=<%=dto.getAddress() %>><br />
		<input type="button" value="수정" onclick="updateInfoConfirm()">&nbsp;&nbsp;&nbsp; 
		<input type="reset" value="취소" onclick="javascript:window.location='list.do'">
	</form>

</body>
</html>