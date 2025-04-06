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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDao;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        this.usuarioDao = new UsuarioDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		if (email == null || senha == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Verifique os campos novamente.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}		
		
		try {
			usuarioDao.login(email, senha);
			response.setStatus(HttpServletResponse.SC_CREATED);
			request.setAttribute("successMsg", "Usuário encontrado");
	        response.sendRedirect(request.getContextPath() + "/vinhos?tela=home");
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Problemas no servidor.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor.");
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("errorMsg", "Usuario nao encontrado.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Usuário nao encontrado.");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Usuário não encontrado.");
		}
		
	}

}
