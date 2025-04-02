package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.bean.Usuario;
import br.com.fiap.exception.NotFoundException;
import br.com.fiap.factory.ConnectionFactory;

public class UsuarioDAO {
	private static Connection connection;

	public UsuarioDAO() throws SQLException {
		connection = ConnectionFactory.getConnection();
	}

	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	private Usuario parseUser(ResultSet result) throws SQLException {
		int idUsuario = result.getInt("idUsuario");
		String nome = result.getString("nome");
		String email = result.getString("email");
		String celular = result.getString("celular");
		int role = result.getInt("role");
		Date dataAniversario = result.getDate("dataAniversario");
		return new Usuario(idUsuario, nome, email, celular, role, dataAniversario);
	}

	public void create(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios (nome, email, celular, senha, role, data_aniversario) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stm.setString(1, usuario.getNome());
			stm.setString(2, usuario.getEmail());
			stm.setString(3, usuario.getCelular());
			stm.setString(4, usuario.getSenha());
			stm.setInt(5, usuario.getRole());
			stm.setDate(6, usuario.getDataAniversario());

			int affectedRows = stm.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet rs = stm.getGeneratedKeys()) {
					if (rs.next()) {
						int newId = rs.getInt(1);
						System.out.println("Novo usuário cadastrado com ID: " + newId);
					}
				}
			}
		}
	}

	public Usuario getById(int id) throws SQLException, NotFoundException {
		String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();

			if (!result.next()) {
				throw new NotFoundException("Usuário não encontrado!");
			}

			return parseUser(result);
		}
	}

	public List<Usuario> getAll() throws SQLException {
		String sql = "SELECT * FROM usuarios";
		List<Usuario> users = new ArrayList<>();

		try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet result = stm.executeQuery()) {

			while (result.next()) {
				users.add(parseUser(result));
			}
		}

		return users;
	}

	public void update(Usuario user) throws SQLException, NotFoundException {
		String sql = "UPDATE usuarios SET nome = ?, email = ?, celular = ? WHERE id_usuario = ?";

		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setString(1, user.getNome());
			stm.setString(2, user.getEmail());
			stm.setString(3, user.getCelular());
			stm.setInt(4, user.getIdUsuario());

			int affectedRows = stm.executeUpdate();

			if (affectedRows == 0) {
				throw new NotFoundException("Usuário não encontrado para atualização.");
			}
		}
	}

	public void delete(int id) throws SQLException, NotFoundException {
		String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, id);
			int affectedRows = stm.executeUpdate();

			if (affectedRows == 0) {
				throw new NotFoundException("Usuário não encontrado para exclusão.");
			}
		}
	}
}
