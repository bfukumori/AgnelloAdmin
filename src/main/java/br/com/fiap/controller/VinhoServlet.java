package br.com.fiap.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.fiap.bean.Vinho;
import br.com.fiap.dao.VinhoDAO;
import br.com.fiap.exception.NotFoundException;

/**
 * Servlet implementation class VinhoServlet
 */
@WebServlet("/vinhos")
public class VinhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VinhoDAO vinhoDAO;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VinhoServlet() {
		super();
		try {
			this.vinhoDAO = new VinhoDAO();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao conectar ao banco", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String idVinho = request.getParameter("idVinho");
		
		if (idVinho != null) {
			int id = Integer.parseInt(idVinho);
			try {
				Vinho vinho = vinhoDAO.getById(id);
				System.out.println(vinho.getNomeVinho());
			} catch (NotFoundException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Vinho nao encontrado");
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar vinho");
			}
		} else {
			try {
				List<Vinho> vinhos = vinhoDAO.getAll();
				request.setAttribute("vinhos", vinhos);
//				request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);

			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar vinhos");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
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
		
		if (nomeVinho != null && fotoVinho != null && preco != null && nomeVinicola != null && cidade != null 
			&& teorAlcoolico != null && docura != null && fotoBandeira != null && blend != null && quantidadeDisponivel != null) {
			
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
//				response.sendRedirect("usuarios");
			} catch (SQLException e) {
				System.out.println(e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar Vinho");
			}
		} else {	
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Parametros Inválidos");
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
		
		if (idVinho != null && nomeVinho != null && fotoVinho != null && preco != null && nomeVinicola != null
			&& cidade != null && teorAlcoolico != null && docura != null && fotoBandeira != null && blend != null && quantidadeDisponivel != null) {
			
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
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar vinho");
			} catch (NotFoundException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Vinho nao encontrado");
			}
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Parametros inválidos");
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String idVinho = request.getParameter("idVinho");
		
		if (idVinho != null) {
			try {
				int id = Integer.parseInt(idVinho);
				vinhoDAO.delete(id);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao deletar vinho");
			} catch (NotFoundException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Vinho nao encontrado");
			} 
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Por favor forneca o Id do usuario a ser deletado");
		}
	}

}
