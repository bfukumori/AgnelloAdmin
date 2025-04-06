<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Criar usuário</title>
<%@ include file="header.jsp"%>
<script>
(function() {
  'use strict';
  window.addEventListener('load', function() {
    var form = document.getElementById('formUsuario');
    form.addEventListener('submit', function(event) {
      if (form.checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add('was-validated');
    }, false);
  }, false);
})();
</script>
</head>
<body>
	<%@ include file="menu.jsp"%>

	<div class="container mt-4">
		<c:if test="${not empty successMsg}">
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<strong>Sucesso: </strong>${successMsg}
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
			</div>
		</c:if>
		<c:if test="${not empty errorMsg}">
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<strong>Erro de validação: </strong>${errorMsg}
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
			</div>
		</c:if>
		
		<fieldset class="card p-4 border border-0 shadow">
			<legend class="display-5 fw-semibold mb-4">Criar usuário</legend>
			<form id="formUsuario" class="needs-validation d-flex flex-column gap-3" action="usuarios" method="post" novalidate>
				<div class="form-group">
					<label for="id-nome" class="text-secondary">Nome</label> 
					<input type="text" name="nome" id="id-nome" class="form-control" required
						pattern=".{3,100}">
					<div class="valid-feedback">
						Nome válido!
					</div>
					<div class="invalid-feedback">
						O nome deve ter entre 3 e 100 caracteres
					</div>
				</div>
				<div class="form-group">
					<label for="id-email" class="text-secondary">Email</label> 
					<input type="email" name="email" id="id-email" class="form-control" required>
					<div class="valid-feedback">
						Email válido!
					</div>
					<div class="invalid-feedback">
						Por favor, insira um email válido
					</div>
				</div>
				<div class="form-group">
					<label for="id-celular" class="text-secondary">Celular</label> 
					<input type="tel" name="celular" id="id-celular" class="form-control" required>
					<div class="valid-feedback">
						Celular válido!
					</div>
					<div class="invalid-feedback">
						Por favor, insira um número de celular válido
					</div>
				</div>
				<div class="form-group">
					<label for="id-senha" class="text-secondary">Senha</label> 
					<input type="password" name="senha" id="id-senha" class="form-control" required>
					<div class="valid-feedback">
						Senha válida!
					</div>
					<div class="invalid-feedback">
						Por favor, insira uma senha válida
					</div>
				</div>
				<div class="form-group">
					<label for="id-role" class="text-secondary">Função</label>
					<select class="form-select" id="id-role" name="role" required>
						<option value="">Selecione uma função</option>
						<option value="0">Administrador</option>
						<option value="1">Cliente</option>
						<option value="2">Funcionário</option>
					</select>
					<div class="valid-feedback">
						Função selecionada!
					</div>
					<div class="invalid-feedback">
						Por favor, selecione uma função
					</div>
				</div>
				<div class="form-group">
					<label for="id-dob" class="text-secondary">Data de aniversário</label> 
					<input type="date" name="dataAniversario" id="id-dob" class="form-control" required>
					<div class="valid-feedback">
						Data válida!
					</div>
					<div class="invalid-feedback">
						Por favor, insira uma data de aniversário válida
					</div>
				</div>
				<input type="submit" value="Salvar" class="btn btn-primary border border-0" style="background-color: #BE2C55">
			</form>
		</fieldset>
	</div>
	
	<%@ include file="footer.jsp"%>
</body>
</html>