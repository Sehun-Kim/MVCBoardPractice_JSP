<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<form action="reply.do" method="post">
			<input type="hidden" name="bId" value="${reply_content.bId}">
			<input type="hidden" name="bGroup" value="${reply_content.bGroup}">
			<input type="hidden" name="bStep" value="${reply_content.bStep}">
			<input type="hidden" name="bIndent" value="${reply_content.bIndent}">
			<tr>
				<td> ID </td>
				<td> ${reply_content.bId} </td>
			</tr>
			<tr>
				<td> 조회수 </td>
				<td> ${reply_content.bHit} </td>
			</tr>
			<tr>
				<td> 이름 </td>
				<td> <input type="text" name="bName" value="${reply_content.bName}"></td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="bTitle" value="${reply_content.bTitle}"></td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea rows="10"  name="bContent">${reply_content.bContent}</textarea></td>
			</tr>
			<tr >
				<td colspan="2"><input type="submit" value="저장"> <a href="list.do" > 목록 </a></td>
			</tr>
		</form>
	</table>

</body>
</html>