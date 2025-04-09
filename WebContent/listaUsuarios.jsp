<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de usuários</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>

	<div class="container mt-4">
		<h1 class="mb-4">Usuários</h1>
		<table class="table table-striped">
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Celular</th>
				<th>Função</th>
				<th>Data de aniversário</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach items="${usuarios}" var="u">
				<tr>
					<td>${u.nome}</td>
					<td>${u.email}</td>
					<td>${u.celular}</td>
					<td><c:choose>
							<c:when test="${u.role==1}">Administrador</c:when>
							<c:when test="${u.role==2}">Cliente</c:when>
							<c:otherwise>Funcionário</c:otherwise>
						</c:choose></td>
					<td><fmt:formatDate value="${u.dataAniversario}"
							pattern="dd/MM/yyyy" timeZone="America/Sao_Paulo" /></td>
					<td><a href="usuarios?idUsuario=${u.idUsuario}&metodo=PUT"
						class="btn btn-outline-primary">Editar</a></td>
					<td><a href="usuarios?idUsuario=${u.idUsuario}&metodo=DELETE"
						class="btn btn-outline-danger">Deletar</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>