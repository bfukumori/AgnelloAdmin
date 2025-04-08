<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Criar vinho</title>
    <%@ include file="header.jsp"%>

    <script>
    // Validação do formulário
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var form = document.getElementById('formVinho');
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        }, false);
    })();

    // Preview da imagem do vinho
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const preview = document.getElementById('fotoPreview');
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    // Atualiza a URL da bandeira com base no país
    function updateFlagUrl(select) {
        const countryCode = select.value;
        console.log("countryCode:", countryCode); // Adicione este log para ver o valor no console
        if (!countryCode) {
            console.warn("Nenhum país selecionado.");
            return;
        }

     // Alternativa: concatenação
        const flagUrl = "https://flagcdn.com/h40/" + countryCode + ".png";

        const input = document.getElementById('fotoBandeira');
        const img = document.getElementById('flagPreview');

        if (input) {
            input.value = flagUrl;
            console.log("Set fotoBandeira:", flagUrl);
        }

        if (img) {
            img.src = flagUrl;
            img.style.display = 'block';
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        const select = document.getElementById("id-pais");
        if (select) {
            select.addEventListener("change", function () {
                updateFlagUrl(this);
            });
        }
    });
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
        <legend class="display-5 fw-semibold mb-4">Criar vinho</legend>
        <form id="formVinho" class="needs-validation d-flex flex-column gap-3"
              action="vinhos" method="post" enctype="multipart/form-data" novalidate>

            <div class="form-group">
                <label for="id-nomeVinho" class="text-secondary">Nome do Vinho</label>
                <input type="text" name="nomeVinho" id="id-nomeVinho" class="form-control" required pattern=".{3,100}">
            </div>

            <div class="form-group">
                <label for="id-fotoVinho" class="text-secondary">Foto do Vinho</label>
                <input type="file" name="fotoVinhoFile" id="id-fotoVinho" class="form-control" accept="image/*" required onchange="previewImage(this)">
                <div class="mt-2">
                    <img id="fotoPreview" src="" alt="Pré-visualização da foto" style="display: none; max-width: 160px; height: auto;">
                </div>
            </div>

            <div class="form-group">
                <label for="id-preco" class="text-secondary">Preço</label>
                <input type="number" step="0.01" min="0.01" name="preco" id="id-preco" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="id-nomeVinicola" class="text-secondary">Nome da Vinícola</label>
                <input type="text" name="nomeVinicola" id="id-nomeVinicola" class="form-control" required pattern=".{3,100}">
            </div>

            <div class="form-group">
                <label for="id-cidade" class="text-secondary">Cidade</label>
                <input type="text" name="cidade" id="id-cidade" class="form-control" required pattern=".{3,100}">
            </div>

            <div class="form-group">
                <label for="id-teorAlcoolico" class="text-secondary">Teor Alcoólico</label>
                <input type="text" name="teorAlcoolico" id="id-teorAlcoolico" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="id-docura" class="text-secondary">Doçura</label>
                <input type="text" name="docura" id="id-docura" class="form-control" required pattern=".{3,50}">
            </div>

            <div class="form-group">
                <label for="id-pais" class="text-secondary">País de Origem</label>
<select class="form-select" id="id-pais" name="pais" required onchange="updateFlagUrl(this)">
                    <option value="" disabled selected>Selecione um país</option>
                    <option value="br">Brasil</option>
                    <option value="ar">Argentina</option>
                    <option value="cl">Chile</option>
                    <option value="fr">França</option>
                    <option value="it">Itália</option>
                    <option value="pt">Portugal</option>
                    <option value="es">Espanha</option>
                    <option value="us">Estados Unidos</option>
                    <option value="au">Austrália</option>
                    <option value="nz">Nova Zelândia</option>
                    <option value="za">África do Sul</option>
                </select>
                <input type="hidden" name="fotoBandeira" id="fotoBandeira">
                <div class="mt-2">
                    <img id="flagPreview" src="" alt="Bandeira do país" style="display: none; max-width: 160px; height: auto;">
                </div>
            </div>

            <div class="form-group">
                <label for="id-blend" class="text-secondary">Blend</label>
                <input type="text" name="blend" id="id-blend" class="form-control" required pattern=".{3,200}">
            </div>

            <div class="form-group">
                <label for="id-quantidadeDisponivel" class="text-secondary">Quantidade Disponível</label>
                <input type="number" name="quantidadeDisponivel" id="id-quantidadeDisponivel" class="form-control" required min="0" step="1">
            </div>

            <input type="submit" value="Salvar" class="btn btn-primary border border-0" style="background-color: #BE2C55">
        </form>
    </fieldset>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>
