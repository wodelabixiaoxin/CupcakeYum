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


<%! String driverName = "com.mysql.jdbc.Driver";%>
<%!String url = "jdbc:mysql://localhost/sampledb";%>
<%!String user = "john";%>
<%!String psw = "pass1234";%>




<form action="#">
<%

Connection con = null;
PreparedStatement ps = null;

UserDao userdao = new UserDao();
User user1 = null;

user1 = (User)session.getAttribute("session_user");
String username = user1.getUsername();

try
{
Class.forName(driverName);
con = DriverManager.getConnection(url,user,psw);





String sql = "SELECT * FROM tb_user where not username=?";
ps = con.prepareStatement(sql);
ps.setString(1,username);
ResultSet rs = ps.executeQuery(); 
%>
<p>Choose a person to follow :
<select name="cand" id="cand">
<%
while(rs.next())
{
String fname = rs.getString("username"); 
%>
<option value="<%=fname %>"><%=fname %></option>
<%
}
%>
</select>
</p>
<%
}
catch(SQLException sqe)
{ 
out.println(sqe);
}
%>

</form>

<form action="<c:url value='/AddFollowing'/>" method="post">

            Enter the name whom you want to follow <input type="text" name="following"> 
            <input type="submit" value="Add"/>



</form>


</body>
</html>