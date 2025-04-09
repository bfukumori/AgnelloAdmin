<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
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
			<c:forEach items="${vinhos}" var="v">
				<div class="col">
					<div class="card h-100 shadow-sm">
						<c:if test="${v.fotoVinho}">
						<div class="text-center p-3">
							<img src="${v.fotoVinho}" class="card-img-top"
								alt="${v.nomeVinho}"
								style="max-height: 200px; object-fit: contain;">
						</div>
						</c:if>
						<div class="card-body">
							<div
								class="d-flex justify-content-between align-items-center mb-2">
								<h5 class="card-title">${v.nomeVinho}</h5>
								<img src="${v.fotoBandeira}" alt="Bandeira"
									style="height: 24px; width: auto;">
							</div>
							<p class="card-text">Vinícola: ${v.nomeVinicola}</p>
							<p class="card-text">Região: ${v.cidade}</p>
							<p class="card-text">Teor Alcoólico: ${v.teorAlcoolico}</p>
							<p class="card-text">Docura: ${v.docura}</p>
							<p class="card-text">Blend: ${v.blend}</p>
							<div
								class="d-flex justify-content-between align-items-center mt-3">
								<h4 class="text-primary">R$ ${v.preco}</h4>
								<span class="badge bg-success">${v.quantidadeDisponivel}
									em estoque</span>
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
