<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String name = (String)session.getAttribute("name"); // session에 저장된 이름을 가져옴
	String id = (String)session.getAttribute("id"); // session에 저장된 id값을 가져옴
%>  

<h1><%= name %>님 안녕하세요.</h1>

<form action="logout.log" method="post">
	<input type="submit" value="logout">&nbsp;&nbsp;&nbsp;
	<input type="button" value="정보수정" onclick="javascript:window.location='modifyUser.log'">
</form>