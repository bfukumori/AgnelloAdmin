package br.com.fiap.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import br.com.fiap.bean.Vinho;
import br.com.fiap.dao.VinhoDAO;
import br.com.fiap.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/vinhos")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,     // 1MB
    maxFileSize = 1024 * 1024 * 5,       // 5MB
    maxRequestSize = 1024 * 1024 * 10    // 10MB
)
public class VinhoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VinhoDAO vinhoDAO;

    public VinhoServlet() {
        super();
        this.vinhoDAO = new VinhoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idVinhoParam = request.getParameter("idVinho");
        String tela = request.getParameter("tela");

        try {
            if (idVinhoParam != null) {
                int id = Integer.parseInt(idVinhoParam);
                Vinho vinho = vinhoDAO.getById(id);
                request.setAttribute("vinho", vinho);
                request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
            } else if (tela != null) {
                List<Vinho> vinhos = vinhoDAO.getAll();
                request.setAttribute("vinhos", vinhos);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                List<Vinho> vinhos = vinhoDAO.getAll();
                request.setAttribute("vinhos", vinhos);
                request.getRequestDispatcher("listaVinhos.jsp").forward(request, response);
            }
        } catch (NotFoundException e) {
            System.out.println(e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vinho não encontrado.");
        } catch (SQLException e) {
            System.out.println(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar vinhos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Recuperar campos do formulário
        String nomeVinho = request.getParameter("nomeVinho");
        String preco = request.getParameter("preco");
        String nomeVinicola = request.getParameter("nomeVinicola");
        String cidade = request.getParameter("cidade");
        String teorAlcoolico = request.getParameter("teorAlcoolico");
        String docura = request.getParameter("docura");
        String fotoBandeira = request.getParameter("fotoBandeira");
        String blend = request.getParameter("blend");
        String quantidadeDisponivel = request.getParameter("quantidadeDisponivel");

        // 2. Recuperar imagem
        Part filePart = request.getPart("fotoVinhoFile");
        String fileName = (filePart != null) ? filePart.getSubmittedFileName() : null;

        // 3. DEBUG
        System.out.println("DEBUG - DADOS DO FORMULÁRIO:");
        System.out.println("nomeVinho: " + nomeVinho);
        System.out.println("fileName: " + fileName);
        System.out.println("preco: " + preco);
        System.out.println("nomeVinicola: " + nomeVinicola);
        System.out.println("cidade: " + cidade);
        System.out.println("teorAlcoolico: " + teorAlcoolico);
        System.out.println("docura: " + docura);
        System.out.println("fotoBandeira: " + fotoBandeira);
        System.out.println("blend: " + blend);
        System.out.println("quantidadeDisponivel: " + quantidadeDisponivel);
        System.out.println("-----------------------------------");

        if (fotoBandeira == null || fotoBandeira.isBlank()) {
            fotoBandeira = "https://flagcdn.com/w160/br.png";
        }

        if (nomeVinho == null || nomeVinho.isBlank() ||
            fileName == null || fileName.isBlank() ||
            preco == null || preco.isBlank() ||
            nomeVinicola == null || nomeVinicola.isBlank() ||
            cidade == null || cidade.isBlank() ||
            teorAlcoolico == null || teorAlcoolico.isBlank() ||
            docura == null || docura.isBlank() ||
            blend == null || blend.isBlank() ||
            quantidadeDisponivel == null || quantidadeDisponivel.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMsg", "Verifique os campos novamente.");
            request.getRequestDispatcher("criarVinho.jsp").forward(request, response);
            return;
        }

        // 5. Salvar imagem na pasta imagens-vinhos
        String uploadPath = getServletContext().getRealPath("") + File.separator + "imagens-vinhos";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadPath + File.separator + uniqueFileName;
        filePart.write(filePath);

        String fotoVinhoUrl = "imagens-vinhos/" + uniqueFileName;

        // 6. Conversões numéricas
        double precoAtt = Double.parseDouble(preco);
        int quantidade = Integer.parseInt(quantidadeDisponivel);

        // 7. Criar objeto Vinho
        Vinho vinho = new Vinho();
        vinho.setNomeVinho(nomeVinho);
        vinho.setFotoVinho(fotoVinhoUrl);
        vinho.setPreco(precoAtt);
        vinho.setNomeVinicola(nomeVinicola);
        vinho.setCidade(cidade);
        vinho.setTeorAlcoolico(teorAlcoolico);
        vinho.setDocura(docura);
        vinho.setFotoBandeira(fotoBandeira);
        vinho.setBlend(blend);
        vinho.setQuantidadeDisponivel(quantidade);

        // 8. Inserir no banco
        try {
            vinhoDAO.create(vinho);
            response.setStatus(HttpServletResponse.SC_CREATED);
            request.setAttribute("successMsg", "Vinho cadastrado com sucesso!");
            List<Vinho> vinhos = vinhoDAO.getAll();
            request.setAttribute("vinhos", vinhos);
            request.getRequestDispatcher("listaVinhos.jsp").forward(request, response);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Erro: Vinho já cadastrado.");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            request.setAttribute("errorMsg", "Vinho já cadastrado.");
            request.getRequestDispatcher("criarVinho.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("Erro ao criar vinho no banco:");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar vinho.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idVinho = request.getParameter("idVinho");
        String nomeVinho = request.getParameter("nomeVinho");
        String fotoVinho = request.getParameter("fotoVinho");
        String preco = request.getParameter("preco");
        String nomeVinicola = request.getParameter("nomeVinicola");
        String cidade = request.getParameter("cidade");
        String teorAlcoolico = request.getParameter("teorAlcoolico");
        String docura = request.getParameter("docura");
        String fotoBandeira = request.getParameter("fotoBandeira");
        String blend = request.getParameter("blend");
        String quantidadeDisponivel = request.getParameter("quantidadeDisponivel");

        if (idVinho == "") {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor forneça o ID do vinho a ser editado.");
            return;
        }

        if (nomeVinho == "" || fotoVinho == "" || preco == "" || nomeVinicola == "" || cidade == ""
                || teorAlcoolico == "" || docura == "" || fotoBandeira == "" || blend == ""
                || quantidadeDisponivel == "") {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMsg", "Verifique os campos novamente.");
            request.getRequestDispatcher("editarVinho.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idVinho);
        double precoAtt = Double.parseDouble(preco);
        int quantidade = Integer.parseInt(quantidadeDisponivel);

        Vinho vinho = new Vinho();
        vinho.setIdVinho(id);
        vinho.setNomeVinho(nomeVinho);
        vinho.setFotoVinho(fotoVinho);
        vinho.setPreco(precoAtt);
        vinho.setNomeVinicola(nomeVinicola);
        vinho.setCidade(cidade);
        vinho.setTeorAlcoolico(teorAlcoolico);
        vinho.setDocura(docura);
        vinho.setFotoBandeira(fotoBandeira);
        vinho.setBlend(blend);
        vinho.setQuantidadeDisponivel(quantidade);

        try {
            vinhoDAO.update(vinho);
            response.setStatus(HttpServletResponse.SC_OK);
            request.setAttribute("successMsg", "Vinho atualizado com sucesso!");
            List<Vinho> vinhos = vinhoDAO.getAll();
            request.setAttribute("vinhos", vinhos);
            request.getRequestDispatcher("listaVinhos.jsp").forward(request, response);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            request.setAttribute("errorMsg", "Vinho já cadastrado.");
            request.getRequestDispatcher("editarVinho.jsp").forward(request, response);
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vinho não encontrado.");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar vinho.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idVinho = request.getParameter("idVinho");

        if (idVinho != null) {
            try {
                int id = Integer.parseInt(idVinho);
                vinhoDAO.delete(id);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vinho não encontrado.");
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao deletar vinho.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor forneça o ID do vinho a ser deletado.");
        }
    }
}
