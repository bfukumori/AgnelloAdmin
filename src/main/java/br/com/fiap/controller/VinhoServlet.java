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
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
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

		String metodo = request.getParameter("metodo");
		String idVinhoParam = request.getParameter("idVinho");
		String tela = request.getParameter("tela");

		if ("DELETE".equalsIgnoreCase(metodo)) {
			doDelete(request, response);
			return;
		}

		try {
			if (idVinhoParam != null) {
				int id = Integer.parseInt(idVinhoParam);
				Vinho vinho = vinhoDAO.getById(id);
				request.setAttribute("vinhos", vinho);
				request.getRequestDispatcher("editarVinho.jsp").forward(request, response);
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

		String idVinho = request.getParameter("idVinho");

		if (idVinho != null) {
			doPut(request, response);
			return;
		}

		request.setCharacterEncoding("UTF-8");

		String nomeVinho = request.getParameter("nomeVinho");
		String preco = request.getParameter("preco");
		String nomeVinicola = request.getParameter("nomeVinicola");
		String cidade = request.getParameter("cidade");
		String teorAlcoolico = request.getParameter("teorAlcoolico");
		String docura = request.getParameter("docura");
		String fotoBandeira = request.getParameter("fotoBandeira");
		String blend = request.getParameter("blend");
		String quantidadeDisponivel = request.getParameter("quantidadeDisponivel");

		Part filePart = request.getPart("fotoVinhoFile");
		String fileName = (filePart != null) ? filePart.getSubmittedFileName() : null;

		String uploadPath = getServletContext().getRealPath("") + File.separator + "imagens-vinhos";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
		String filePath = uploadPath + File.separator + uniqueFileName;
		filePart.write(filePath);

		String fotoVinhoUrl = "imagens-vinhos/" + uniqueFileName;

		double precoAtt = Double.parseDouble(preco);
		int quantidade = Integer.parseInt(quantidadeDisponivel);

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

		try {
			vinhoDAO.create(vinho);
			response.setStatus(HttpServletResponse.SC_CREATED);
			request.setAttribute("successMsg", "Vinho cadastrado com sucesso!");
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
				response.sendRedirect("vinhos");
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
