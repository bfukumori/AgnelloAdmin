<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de vinhos</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container">
		<h1>Criar vinho</h1>
		<form action="usuarios" method="post">
			<div class="form-group">
				<label for="id-nome">Nome</label> <input type="text" name="nome"
					id="id-nome" class="form-control">
			</div>
			<div class="form-group">
				<label for="id-email">Email</label> <input type="text" name="email"
					id="id-email" class="form-control">
			</div>
			<div class="form-group">
				<label for="id-celular">Celular</label> <input type="text"
					name="celular" id="id-celular" class="form-control">
			</div>
			<div class="form-group">
				<label for="id-senha">Senha</label> <input type="text" name="senha"
					id="id-senha" class="form-control">
			</div>
			<div class="form-group">
				<label for="id-role">Função</label> <input type="text" name="role"
					id="id-role" class="form-control">
			</div>
			<div class="form-group">
				<label for="id-dob">Data de aniversário</label> <input type="date"
					name="dataAniversario" id="id-dobr" class="form-control">
			</div>
			<input type="submit" value="Salvar" class="btn btn-primary">
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>