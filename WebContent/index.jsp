<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@ include file="header.jsp"%>
<script>
(function() {
  'use strict';
  window.addEventListener('load', function() {
    var form = document.getElementById('formLogin');
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
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">
        <div class="col-md-6 col-lg-4">
            <c:if test="${not empty successMsg}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Sucesso: </strong>${successMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Erro: </strong>${errorMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <fieldset class="card p-4 border border-0 shadow">
                <div class="text-center mb-4">
                    <img alt="logo" src="resources/img/wine-bottle.png" width="40" height="40">
                    <legend class="display-5 fw-semibold mt-2">Login</legend>
                </div>
                <form id="formLogin" class="needs-validation d-flex flex-column gap-3" action="login" method="post" novalidate>
                    <div class="form-group">
                        <label for="id-email" class="text-secondary">Email</label> 
                        <input type="email" name="email" id="id-email" class="form-control" required>
                        <div class="invalid-feedback">
                            Por favor, insira um email válido
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="id-senha" class="text-secondary">Senha</label> 
                        <input type="password" name="senha" id="id-senha" class="form-control" required>
                        <div class="invalid-feedback">
                            Por favor, insira sua senha
                        </div>
                    </div>
                    <input type="submit" value="Entrar" class="btn btn-primary border border-0" style="background-color: #BE2C55">
                    <div class="text-center mt-3">
                        <a href="#" class="text-decoration-none" style="color: #BE2C55">Esqueci minha senha</a>
                    </div>
                </form>
            </fieldset>
        </div>
    </div>
    
    <%@ include file="footer.jsp"%>
</body>
</html>