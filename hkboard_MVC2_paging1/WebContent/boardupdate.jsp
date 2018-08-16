<%@page import="com.hk.dtos.HkDto"%>
<%@page import="com.hk.daos.HkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정하기</title>
</head>
<body>
<%
	HkDto dto=(HkDto)request.getAttribute("dto");
%>
<h1>글 수정하기</h1>
<form action="HkController.do" method="post" >
<input type="hidden" name="command" value="boardupdate"/>
<input type="hidden" name="seq" value="<%=dto.getSeq()%>"/>
<table border="1">
	<tr>
		<th>번호</th>
		<td><%=dto.getSeq()%></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>작성일</th>
		<td><%=dto.getRegdate()%></td>
	</tr>
	
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" value="<%=dto.getTitle()%>"/></td>
	</tr>
	<tr>
		<th>내용</th>
		<td>
			<textarea rows="10" cols="60" name="content"><%=dto.getContent()%></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="수정완료"/>
			<input type="button" value="목록" onclick="location.href='HkController.do?command=boardlist'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>