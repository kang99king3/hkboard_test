<%@page import="com.hk.dtos.HkDto"%>
<%@page import="com.hk.daos.HkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세보기</title>
</head>
<body>
<% 
// 	HkDto dto=(HkDto)request.getAttribute("dto");
%>
<%@include file="header.jsp" %>
<h1>글 상세보기</h1>
<table border="1">
	<tr>
		<th>번호</th>
		<td>${dto.seq}</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${dto.name}</td>
	</tr>
	<tr>
		<th>작성일</th>
		<td>${dto.regdate}</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${dto.title}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>
			<textarea rows="10" cols="60" readonly="readonly">${dto.content}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="button" value="수정"  
			onclick="location.href='HkController.do?command=updateform&seq=${dto.seq}'" />
			<input type="button" value="삭제"
			onclick="location.href='HkController.do?command=delboard&seq=${dto.seq}'"/>
			<input type="button" value="목록"
			 onclick="location.href='HkController.do?command=boardlist'"/>
		</td>
	</tr>
</table>
</body>
</html>











