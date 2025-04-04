<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Catálogo de Vinhos</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container mt-4">
		<h1 class="mb-4">Catálogo de Vinhos</h1>

		<div class="row row-cols-1 row-cols-md-3 g-4">
			<c:forEach items="${vinhos}" var="vinho">
				<div class="col">
					<div class="card h-100 shadow-sm">
						<div class="text-center p-3">
							<img src="${vinho.fotoVinho}" class="card-img-top"
								alt="${vinho.nomeVinho}"
								style="max-height: 200px; object-fit: contain;">
						</div>
						<div class="card-body">
							<div
								class="d-flex justify-content-between align-items-center mb-2">
								<h5 class="card-title">${vinho.nomeVinho}</h5>
								<img src="${vinho.fotoBandeira}" alt="Bandeira"
									style="height: 24px; width: auto;">
							</div>
							<p class="card-text">Vinícola: ${vinho.nomeVinicola}</p>
							<p class="card-text">Região: ${vinho.cidade}</p>
							<p class="card-text">Teor Alcoólico: ${vinho.teorAlcoolico}</p>
							<p class="card-text">Docura: ${vinho.docura}</p>
							<p class="card-text">Blend: ${vinho.blend}</p>
							<div
								class="d-flex justify-content-between align-items-center mt-3">
								<h4 class="text-primary">R$ ${vinho.preco}</h4>
								<span class="badge bg-success">${vinho.quantidadeDisponivel}
									em estoque</span>
							</div>
						</div>
						<div class="card-footer">
							<div class="d-grid gap-2">
								<button class="btn btn-outline-primary">Detalhes</button>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>
