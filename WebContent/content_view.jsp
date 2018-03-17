<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function fnId(arg){
	location.href="/mvcWebBoard/modify_form.do?bId="+arg;
}
</script>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td> ID </td>
				<td> ${content_view.bId} </td>
			</tr>
			<tr>
				<td> 조회수 </td>
				<td> ${content_view.bHit} </td>
			</tr>
			<tr>
				<td> 이름 </td>
				<td> ${content_view.bName}</td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> ${content_view.bTitle}</td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> ${content_view.bContent} </td>
			</tr>
			<tr >
				<td colspan="2"> 
					<input type="button" value="수정" onclick="fnId('${content_view.bId}')"> &nbsp;&nbsp; 
					<a href="list.do">목록</a> &nbsp;&nbsp; 
					<a href="delete.do?bId=${content_view.bId}">삭제</a> &nbsp;&nbsp; 
					<a href="reply_view.do?bId=${content_view.bId}">답변</a>
				</td>
			</tr>
	</table>

</body>
</html>