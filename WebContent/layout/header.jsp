<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>URL 방식 안됨!!! URI 방식으로 요청하기!!!</h1>
	<ul>
		<li><a href=" <%=contextPath%>/user?gubun=login">로그인</a></li>
		<!-- 상대경로  user?gubun=login -->
		<li><a href="  <%=contextPath%>/user?gubun=join">회원가입</a></li>
		<!-- 절대경로 /hello/user?gubun=login  컨텍스트 루트 패스를 변경하면 다 고쳐야 됨-->
		<li><a href="  <%=contextPath%>/user?gubun=selectOne">유저정보보기</a></li>
		<li><a href="  <%=contextPath%>/user?gubun=updateOne">유저수정하기</a></li>
		<li><a href="  <%=contextPath%>/board?gubun=deleteOne">게시글삭제하기</a></li>
		<li><a href="  <%=contextPath%>/board?gubun=selectAll">게시글입력하기</a></li>
		<li><a href="  <%=contextPath%>/board?gubun=insertOne">게시글전체보기</a></li>
		<li><a href="  <%=contextPath%>/board?gubun=updateOne">게시글수정하기</a></li>
	</ul>