<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de vinhos</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container">
		<h1>Vinhos</h1>
		<table class="table table-striped">
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Celular</th>
			</tr>
			<c:forEach items="${usuarios}" var="u">
				<tr>
					<td>${u.nome}</td>
					<td>${u.email}</td>
					<td>${u.celular}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>