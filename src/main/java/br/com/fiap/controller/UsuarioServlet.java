package br.com.fiap.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import br.com.fiap.bean.Usuario;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDao;

	public UsuarioServlet() {
		super();
		this.usuarioDao = new UsuarioDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String metodo = request.getParameter("metodo");

		if ("DELETE".equalsIgnoreCase(metodo)) {
			doDelete(request, response);
			return;
		}

		String idUsuarioParam = request.getParameter("idUsuario");

		try {

			if (idUsuarioParam != null) {
				int id = Integer.parseInt(idUsuarioParam);
				Usuario usuario = usuarioDao.getById(id);
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
			} else {
				List<Usuario> usuarios = usuarioDao.getAll();
				request.setAttribute("usuarios", usuarios);
				request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);
			}
		} catch (NotFoundException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuário não encontrado.");
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar usuários.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String celular = request.getParameter("celular");
		String senha = request.getParameter("senha");
		String role = request.getParameter("role");
		String dataAniversario = request.getParameter("dataAniversario");

		if (nome == "" || email == "" || celular == "" || senha == "" || dataAniversario == "" || role == "") {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Verifique os campos novamente.");
			request.getRequestDispatcher("criarUsuario.jsp").forward(request, response);
			return;
		}

		int roleInt = Integer.parseInt(role);

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setCelular(celular);
		usuario.setSenha(senha);
		usuario.setRole(roleInt);
		usuario.setDataAniversario(java.sql.Date.valueOf(dataAniversario));

		try {
			usuarioDao.create(usuario);
			response.setStatus(HttpServletResponse.SC_CREATED);
			request.setAttribute("successMsg", "Usuário cadastrado com sucesso!");
			request.getRequestDispatcher("criarUsuario.jsp").forward(request, response);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			request.setAttribute("errorMsg", "Usuário já cadastrado com esse email ou telefone.");
			request.getRequestDispatcher("criarUsuario.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar usuário.");
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idUsuario = request.getParameter("idUsuario");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String celular = request.getParameter("celular");
		String senha = request.getParameter("senha");
		String role = request.getParameter("role");
		String dataAniversario = request.getParameter("dataAniversario");

		if (idUsuario == "") {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor forneça o ID do usuário a ser editado.");
			return;
		}

		if (nome == "" || email == "" || celular == "" || senha == "" || dataAniversario == "" || role == "") {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Verifique os campos novamente.");
			request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
			return;
		}

		int id = Integer.parseInt(idUsuario);
		int roleInt = Integer.parseInt(role);

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setCelular(celular);
		usuario.setSenha(senha);
		usuario.setRole(roleInt);
		usuario.setDataAniversario(java.sql.Date.valueOf(dataAniversario));

		try {
			usuarioDao.update(usuario);
			response.setStatus(HttpServletResponse.SC_OK);
			request.setAttribute("successMsg", "Usuário atualizado com sucesso!");
			request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			request.setAttribute("errorMsg", "Usuário já cadastrado com esse email ou telefone.");
			request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
			return;
		} catch (NotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuário não encontrado.");
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar usuário.");
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idUsuario = request.getParameter("idUsuario");

		if (idUsuario != null) {
			try {
				int id = Integer.parseInt(idUsuario);
				usuarioDao.delete(id);
				response.setStatus(HttpServletResponse.SC_OK);
				response.sendRedirect("usuarios");
			} catch (NotFoundException e) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuário não encontrado.");
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao deletar usuário.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor forneça o ID do usuário a ser deletado.");
		}
	}

}
