<%@page import="com.cos.hello.model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../layout/header.jsp" %>

<h1>회원정보보기</h1>
<table border = "1">
	<tr>
		<th>번호</th>
		<th>유저네임</th>
		<th>패스워드</th>
		<th>이메일</th>
	</tr>
	
	<tr>	
		<td>${user.id}</td>
		<td>${user.username}</td>
		<td>${user.password}</td>
		<td>${user.email}</td>
	</tr>

</table>
</body>
</html>