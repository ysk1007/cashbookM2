<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<%
	String admin = (String)session.getAttribute("admin");
%>
<body>
	<form action="updateAdminPw" method="post">
		계정 : <input type="text" name="admin" value="<%=admin%>" readonly>
		<br>
		비밀번호 : <input type="password" name="pw" placeholder="기존 비밀번호 입력">
		<br>
		새로운 비밀번호 : <input type="password" name="newPw" placeholder="새로운 비밀번호 입력">
		<button type="submit">수정</button>
	</form>
</body>
</html>