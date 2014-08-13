<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
</head>  
<body>
	<form method="post" action="<c:url value="/user.html"/>">
	<table>
		<caption>用户注册</caption>
		<tr>
			<td>用户名：<input type="text" name="name"/></td>
		</tr>
        <tr>
			<td>密&nbsp;&nbsp;码：<input type="password" name="pass"/></td>
		</tr>
        <tr>
			<td>体&nbsp;&nbsp;重：<input type="text" name="weight"/></td>
		</tr>
		<tr align="center">
        	<td colspan="2"><input type="submit" value="提交"/>
            <input type="reset" value="重置"/></td>
        </tr>
	</table>
	</form>
</body>
</html>
