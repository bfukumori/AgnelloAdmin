package br.com.fiap.dao;

import br.com.fiap.exception.NotFoundException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class UsuarioDAO {
    private static Connection connection;

    public UsuarioDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    private Usuario parseUser(ResultSet result) throws SQLException {
        int idUsuario = result.getInt("idUsuario");
        String nomeUsuario = result.getString("nomeUsuario");
        String emailUsuario = result.getString("emailUsuario");
        String senha = result.getString("senha");
        String celular = result.getString("celular");
        int role = result.getInt("role");
        Date dataAniversario = result.getDate("dataAniversario");
        return new Usuario(idUsuario, nomeUsuario, emailUsuario, celular, senha, role, dataAniversario);
    }

    // Alterar query SQL e adicionar novos campos
    public void create(Usuario usuario) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO T_USERS (id, name, email, password) values (seq_user.nextval, ?, ?, ?)", new String[]{"id"});
        stm.setString(1, usuario.getNomeUsuario());
        stm.setString(2, usuario.getEmailUsuario());
        stm.setString(3, usuario.getPassword());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            usuario.setIdUsuario(generatedKeys.getInt(1));
        }
    }

    public Usuario getById(int id) throws SQLException, NotFoundException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM T_USERS WHERE id = ?");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        return parseUser(result);
    }

    public List<User> getAll() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM T_USERS");
        ResultSet result = stm.executeQuery();
        List<User> users = new ArrayList<>();

        while (result.next()) {
            users.add(parseUser(result));
        }

        return users;
    }

    public void update(User user) throws SQLException , NotFoundException{
        PreparedStatement stm = connection.prepareStatement("UPDATE T_USERS SET name = ?, email = ?, password = ? WHERE id = ?");
        stm.setString(1, user.getName());
        stm.setString(2, user.getEmail());
        stm.setString(3, user.getPassword());
        stm.setInt(4, user.getId());
        int line = stm.executeUpdate();

        if (line == 0) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }

    public void delete(int id) throws SQLException, NotFoundException {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM T_USERS WHERE id = ?");
        stm.setInt(1, id);
        int line = stm.executeUpdate();

        if (line == 0) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }
}
