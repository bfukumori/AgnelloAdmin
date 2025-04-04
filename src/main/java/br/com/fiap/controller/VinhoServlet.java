package br.com.fiap.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import br.com.fiap.bean.Vinho;
import br.com.fiap.dao.VinhoDAO;
import br.com.fiap.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/vinhos")
public class VinhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VinhoDAO vinhoDAO;

	public VinhoServlet() {
		super();
		this.vinhoDAO = new VinhoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idVinhoParam = request.getParameter("idVinho");

		try {

			if (idVinhoParam != null) {
				int id = Integer.parseInt(idVinhoParam);
				Vinho vinho = vinhoDAO.getById(id);
				request.setAttribute("vinho", vinho);
				request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		if (nomeVinho == "" || fotoVinho == "" || preco == "" || nomeVinicola == "" || cidade == ""
				|| teorAlcoolico == "" || docura == "" || fotoBandeira == "" || blend == ""
				|| quantidadeDisponivel == "") {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Verifique os campos novamente.");
			request.getRequestDispatcher("criarUsuario.jsp").forward(request, response);
			return;
		}

		double precoAtt = Double.parseDouble(preco);
		int quantidade = Integer.parseInt(quantidadeDisponivel);

		Vinho vinho = new Vinho();
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
			vinhoDAO.create(vinho);
			response.setStatus(HttpServletResponse.SC_CREATED);
			request.setAttribute("successMsg", "Vinho cadastrado com sucesso!");
			request.getRequestDispatcher("criarVinho.jsp").forward(request, response);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			request.setAttribute("errorMsg", "Vinho já cadastrado.");
			request.getRequestDispatcher("criarVinho.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar vinho.");
		}
	}

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
			request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
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
			request.getRequestDispatcher("editarVinho.jsp").forward(request, response);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			request.setAttribute("errorMsg", "Vinho já cadastrado.");
			request.getRequestDispatcher("editarVinho.jsp").forward(request, response);
			return;
		} catch (NotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vinho não encontrado.");
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar vinho.");
		}
	}

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
