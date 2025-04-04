<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edição de vinhos</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>

	<div class="container mt-4">

		<c:if test="${not empty successMsg}">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<strong>Sucesso: </strong>${successMsg}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>

		</c:if>
		<c:if test="${not empty errorMsg}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<strong>Erro de validação: </strong>${errorMsg}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>

		</c:if>
		<fieldset class="card p-4 border border-0 shadow">
			<legend class="display-5 fw-semibold mb-4">Editar vinho</legend>
			<form action="usuarios" method="post"
				class="d-flex flex-column gap-3">
				<div class="form-group">
					<label for="id-nome" class="text-secondary">Nome</label> <input
						type="text" name="nome" id="id-nome" class="form-control">
				</div>
				<div class="form-group">
					<label for="id-email" class="text-secondary">Email</label> <input
						type="email" name="email" id="id-email" class="form-control">
				</div>
				<div class="form-group">
					<label for="id-celular" class="text-secondary">Celular</label> <input
						type="text" name="celular" id="id-celular" class="form-control">
				</div>
				<div class="form-group">
					<label for="id-senha" class="text-secondary">Senha</label> <input
						type="password" name="senha" id="id-senha" class="form-control">
				</div>
				<div>
					<label for="id-role" class="text-secondary">Função</label> <select
						class="form-select" aria-label="Função" id="id-role" name="role">
						<option value="1">Administrador</option>
						<option value="2">Cliente</option>
						<option value="3">Funcionário</option>
					</select>
				</div>
				<div class="form-group">
					<label for="id-dob" class="text-secondary">Data de
						aniversário</label> <input type="date" name="dataAniversario" id="id-dobr"
						class="form-control">
				</div>
				<input type="submit" value="Salvar"
					class="btn btn-primary border border-0"
					style="background-color: #BE2C55">
			</form>
		</fieldset>

	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>