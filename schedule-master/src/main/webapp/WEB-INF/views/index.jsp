<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" %>
<html>
<head>
<title>Index Page</title>
</head>

<body>

	<c:forEach items="${list}" var="u">
		<div>
			<a id="${u.age }">${u.name }</a>
			<span>${u.age}</span>
		</div>
	</c:forEach>
	<h1>Hello world! ${uname}</h1>
	<h2>
</body>
</html>