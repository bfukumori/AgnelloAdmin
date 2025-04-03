package br.com.fiap.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.bean.Usuario;
import br.com.fiap.dao.UsuarioDAO;
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
		try {
			List<Usuario> usuarios = usuarioDao.getAll();
			request.setAttribute("usuarios", usuarios);
			request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar usuários");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String celular = request.getParameter("celular");
		String senha = request.getParameter("senha");
		int role = Integer.parseInt(request.getParameter("role"));
		String dataAniversario = request.getParameter("dataAniversario");

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setCelular(celular);
		usuario.setSenha(senha);
		usuario.setRole(role);
		usuario.setDataAniversario(java.sql.Date.valueOf(dataAniversario));

		try {
			usuarioDao.create(usuario);
			response.setStatus(HttpServletResponse.SC_CREATED);
			request.setAttribute("successMsg", "Usuário cadastrado com sucesso!");
			request.getRequestDispatcher("criarUsuario.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar usuário");
		}
	}

}
