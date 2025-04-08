<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de vinhos</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container mt-4">
		<h1 class="mb-4">Vinhos</h1>
		<table class="table table-striped">
			<tr>
				<th>Nome do Vinho</th>
				<th>Preço</th>
				<th>Nome Vinicola</th>
				<th>Cidade</th>
				<th>Teor Alcoolico</th>
				<th>Docura</th>
				<th>Foto Bandeira</th>
				<th>Docura</th>
				<th>Quantidade Disponivel</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach items="${vinhos}" var="v">
				<tr>
					<td>${v.nomeVinho}</td>
					<td>${v.preco}</td>
					<td>${v.nomeVinicola}</td>
					<td>${v.cidade}</td>
					<td>${v.teorAlcoolico}</td>
					<td>${v.docura}</td>
					<td><img src="${v.fotoBandeira}" class="card-img-top"
								alt="${v.nomeVinho}"
								style="max-height: 200px; object-fit: contain;"></td>
					<td>${v.blend}</td>
					<td>${v.quantidadeDisponivel}</td>
					<td><a href="vinhos?idVinho=${v.idVinho}"
						class="btn btn-outline-primary">Editar</a></td>
					<td><a href="vinhos?idVinho=${v.idVinho}&metodo=DELETE"
						class="btn btn-outline-danger">Deletar</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>