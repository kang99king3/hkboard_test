<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 작성하기</title>
</head>
<body>
<h1>게시글 작성하기</h1>
<form action="HkController.do" method="post">
<input type="hidden" name="command" value="boardinsert"/>
	<table border="1">
		<col width="100px"><col width="400px">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="id" /></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" /></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="60" name="content"></textarea> </td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="글등록" /></td>
		</tr>
	</table>
</form>
</body>
</html>









