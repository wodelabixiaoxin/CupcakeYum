<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>--%>


<%@ page import="user.dao.UserDao" %>
<%@ page import="user.domain.User" %>
<%@ page import="java.sql.*, com.mysql.jdbc.Driver"%>
<%@ page import="com.mysql.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script type="text/javascript">
      function open_fun() {
        document.getElementById('link').innerHTML = "<a href='javascript:clo_fun()'>CLOSE</a>";
      }
      function clo_fun() {
        document.getElementById('link').innerHTML = "<a href='javascript:open_fun()'>OPEN</a>";
      }
    </script>
</head>
<body>
	<h3 align="center"> User Table </h3>
	<table border="1" width="70%" align="center">
		<tr>
			<th>name</th>
			<th>email</th>
			<th>follow status</th>
		</tr>
		<c:forEach items="${UserList}" var="user">
			<tr>
				<td>${user.username }</td>
				<td>${user.email }</td>
				<td><a href="<c:url value='/findAll3'/>" target="body" onClick="javascript:this.innerHTML = 'Followed'">Follow</a></td>
			</tr>
		</c:forEach>
	</table>

	<form action="#">
	<%
		String dbName = "sampledb2";
		String userName = "john";
		String password = "pass1234";
		String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
		String port = "3306";

		Connection con = null;
		PreparedStatement ps = null;
		UserDao userdao = new UserDao();
		User user1 = null;

		user1 = (User)session.getAttribute("session_user");
		String username = user1.getUsername();

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			String sql = "SELECT * FROM user where not username=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
	%>

	<p>Choose a person to follow :
	