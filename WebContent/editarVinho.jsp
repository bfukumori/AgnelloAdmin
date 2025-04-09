<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar vinho</title>
    <%@ include file="header.jsp"%>

    <script>
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

    function updateFlagUrl(select) {
        const countryCode = select.value;
        if (!countryCode) return;

        const flagUrl = "https://flagcdn.com/h40/" + countryCode + ".png";
        const input = document.getElementById('fotoBandeira');
        const img = document.getElementById('flagPreview');

        if (input) input.value = flagUrl;
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

            const selected = select.value;
            if (selected) updateFlagUrl(select);
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
            <strong>Erro: </strong>${errorMsg}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <fieldset class="card p-4 border border-0 shadow">
        <legend class="display-5 fw-semibold mb-4">Editar vinho</legend>
        <form class="needs-validation d-flex flex-column gap-3"
              action="vinhos" method="post" enctype="multipart/form-data" novalidate>

            <div class="form-group">
                <label for="id-nomeVinho" class="text-secondary">Nome do Vinho</label>
                <input type="text" name="nomeVinho" id="id-nomeVinho" class="form-control"
                       value="${vinhos.nomeVinho}">
            </div>

            <div class="form-group">
                <label for="id-fotoVinho" class="text-secondary">Foto do Vinho</label>
                <input type="file" name="fotoVinhoFile" id="id-fotoVinho" class="form-control"
                       accept="image/*" onchange="previewImage(this)">
                <div class="mt-2">
                    <img id="fotoPreview" src="${vinhos.fotoVinho}" alt="Foto atual do vinho"
                         style="max-width: 160px; height: auto; ${empty vinho.foto ? 'display: none;' : ''}">
                </div>
            </div>

            <div class="form-group">
                <label for="id-preco" class="text-secondary">Preço</label>
                <input type="number" name="preco" id="id-preco" class="form-control"
                       value="${vinhos.preco}" required>
            </div>

            <div class="form-group">
                <label for="id-nomeVinicola" class="text-secondary">Nome da Vinícola</label>
                <input type="text" name="nomeVinicola" id="id-nomeVinicola" class="form-control"
                       value="${vinhos.nomeVinicola}" required>
            </div>

            <div class="form-group">
                <label for="id-cidade" class="text-secondary">Cidade</label>
                <input type="text" name="cidade" id="id-cidade" class="form-control"
                       value="${vinhos.cidade}" required>
            </div>

            <div class="form-group">
                <label for="id-teorAlcoolico" class="text-secondary">Teor Alcoólico</label>
                <input type="text" name="teorAlcoolico" id="id-teorAlcoolico" class="form-control"
                       value="${vinhos.teorAlcoolico}" required>
            </div>

            <div class="form-group">
                <label for="id-docura" class="text-secondary">Doçura</label>
                <input type="text" name="docura" id="id-docura" class="form-control"
                       value="${vinhos.docura}" required>
            </div>

            <div class="form-group">
                <label for="id-pais" class="text-secondary">País de Origem</label>
                <select class="form-select" id="id-pais" name="pais" required>
                    <option value="" disabled>Selecione um país</option>
                    <option value="br" ${vinho.pais == 'br' ? 'selected' : ''}>Brasil</option>
                    <option value="ar" ${vinho.pais == 'ar' ? 'selected' : ''}>Argentina</option>
                    <option value="cl" ${vinho.pais == 'cl' ? 'selected' : ''}>Chile</option>
                    <option value="fr" ${vinho.pais == 'fr' ? 'selected' : ''}>França</option>
                    <option value="it" ${vinho.pais == 'it' ? 'selected' : ''}>Itália</option>
                    <option value="pt" ${vinho.pais == 'pt' ? 'selected' : ''}>Portugal</option>
                    <option value="es" ${vinho.pais == 'es' ? 'selected' : ''}>Espanha</option>
                    <option value="us" ${vinho.pais == 'us' ? 'selected' : ''}>Estados Unidos</option>
                    <option value="au" ${vinho.pais == 'au' ? 'selected' : ''}>Austrália</option>
                    <option value="nz" ${vinho.pais == 'nz' ? 'selected' : ''}>Nova Zelândia</option>
                    <option value="za" ${vinho.pais == 'za' ? 'selected' : ''}>África do Sul</option>
                </select>
                <input type="hidden" name="fotoBandeira" id="fotoBandeira" value="${vinho.fotoBandeira}">
                <div class="mt-2">
                    <img id="flagPreview" src="${vinhos.fotoBandeira}" alt="Bandeira do país"
                         style="max-width: 160px; height: auto; ${empty vinho.fotoBandeira ? 'display: none;' : ''}">
                </div>
            </div>

            <div class="form-group">
                <label for="id-blend" class="text-secondary">Blend</label>
                <input type="text" name="blend" id="id-blend" class="form-control"
                       value="${vinhos.blend}" required>
            </div>

            <div class="form-group">
                <label for="id-quantidadeDisponivel" class="text-secondary">Quantidade Disponível</label>
                <input type="number" name="quantidadeDisponivel" id="id-quantidadeDisponivel" class="form-control"
                       value="${vinhos.quantidadeDisponivel}" required>
            </div>

            <input type="submit" value="Atualizar" class="btn btn-primary border border-0" style="background-color: #BE2C55">
            <input type="hidden" name="method" value="PUT">
                <input type="hidden" name="idVinho" value="${vinhos.idVinho}">
        </form>
    </fieldset>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>
