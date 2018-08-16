<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.hk.dtos.HkDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.daos.HkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글목록 리스트</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<style type="text/css">
	tr:nth-child(odd) {
		background: orange;	
}
	a{text-decoration: none;}
	#content{width: 600px; margin: 100px auto;}
</style>
</head>
<%
	//java 코드 작성 영역(실행부) : 포탈이 열렸어요
// 	HkDao dao=new HkDao();
// 	List<HkDto> list=dao.getAllBoard(); //이코드들은 필요가 없어요~~controller에서 작성
// 	Object obj=request.getAttribute("list");
// 	List<HkDto> list=(List<HkDto>)obj;

	List<HkDto> list=(List<HkDto>)request.getAttribute("list");
	int prePageNum=(Integer)request.getAttribute("prePageNum");
	int nextPageNum=(Integer)request.getAttribute("nextPageNum");
	int startPage=(Integer)request.getAttribute("startPage");
	int endPage=(Integer)request.getAttribute("endPage");
%>
<body>
<%-- <%@include file="header.jsp" %> --%>
<div id="content">
<h1>글목록 보기</h1>

<table border="1">
	<col width="50px"><col width="100px">
	<col width="300px"><col width="100px">
	<tr>
		<th>번호</th>
		<th>아이디</th>
		<th>제목</th>
		<th>작성자</th>
	</tr>
	<%
	for(int i=0;i<list.size();i++){
		HkDto dto=list.get(i);
	%> 
	<tr>
		<td><%=dto.getSeq()%></td>
		<td><%=dto.getId()%></td>
		<td><a class="contentview" href="HkController.do?command=boarddetail&seq=<%=dto.getSeq()%>"><%=dto.getTitle()%></a></td>
		<td><%=dto.getRegdate()%></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td colspan="4">
			<a href="HkController.do?command=boardlist&pnum=<%=prePageNum%>">◀</a>
			<%
				for(int i=startPage;i<=endPage;i++){
				%>
				<a href="HkController.do?command=boardlist&pnum=<%=i%>"><%=i%></a>
				<%
				}
			%>
			<a href="HkController.do?command=boardlist&pnum=<%=nextPageNum%>">▶</a>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<a href="HkController.do?command=boardwrite">글쓰기</a>
		</td>
	</tr>
</table>
</div>
</body>
</html>










