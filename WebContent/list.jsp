<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- jstl 사용 --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List</title>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>ID</td>
			<td>Name</td>
			<td>Title</td>
			<td>Date</td>
			<td>Hit</td>
		</tr>
		<!-- list.do를 uri로 입력하였을 때 request 객체로 ArrayList<BDto> list가 set 되어 넘어옴. 모든 BDto객체를 for문으로 출력 -->
		<!-- list의 크기 만큼 for문을 돌림 -->
		<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.bId}</td>
			<td>${dto.bName}</td>
			<td>
				<c:forEach begin="1" end="${dto.bIndent}"> - </c:forEach> <!-- bIndent의 크기만큼 -를 출력해줌 -->
				<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a> <!--dto의 제목을 클릭하였을때 content_view.do uri로 get방식을 사용하여 bId를 넘겨 이동함 -->
			</td>
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5"><a href="write_view.do">글작성</a></td>
		</tr>
	
	</table>

</body>
</html>