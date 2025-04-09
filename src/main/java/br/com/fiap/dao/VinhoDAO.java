package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.bean.Vinho;
import br.com.fiap.exception.NotFoundException;
import br.com.fiap.factory.ConnectionFactory;

public class VinhoDAO {

	private Vinho parseVinho(ResultSet result) throws SQLException {
		int idVinho = result.getInt("idVinho");
		String nomeVinho = result.getString("nomeVinho");
		String fotoVinho = result.getString("fotoVinho");
		Double preco = result.getDouble("preco");
		String nomeVinicola = result.getString("nomeVinicola");
		String cidade = result.getString("cidade");
		String teorAlcoolico = result.getString("teorAlcoolico");
		String docura = result.getString("docura");
		String fotoBandeira = result.getString("fotoBandeira");
		String blend = result.getString("blend");
		int quantidadeDisponivel = result.getInt("quantidadeDisponivel");
		return new Vinho(idVinho, nomeVinho, fotoVinho, preco, nomeVinicola, cidade, teorAlcoolico, docura,
				fotoBandeira, blend, quantidadeDisponivel);
	}

	public void create(Vinho vinho) throws SQLException {
		String sql = "INSERT INTO vinhos (nomeVinho, fotoVinho, preco, nomeVinicola, cidade, teorAlcoolico, docura, fotoBandeira, blend, quantidadeDisponivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stm.setString(1, vinho.getNomeVinho());
			stm.setString(2, vinho.getFotoVinho());
			stm.setDouble(3, vinho.getPreco());
			stm.setString(4, vinho.getNomeVinicola());
			stm.setString(5, vinho.getCidade());
			stm.setString(6, vinho.getTeorAlcoolico());
			stm.setString(7, vinho.getDocura());
			stm.setString(8, vinho.getFotoBandeira());
			stm.setString(9, vinho.getBlend());
			stm.setInt(10, vinho.getQuantidadeDisponivel());

			int affectedRows = stm.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet rs = stm.getGeneratedKeys()) {
					if (rs.next()) {
						int newId = rs.getInt(1);
						System.out.println("Novo vinho cadastrado com ID: " + newId);
					}
				}
			}
		}
	}

	public Vinho getById(int id) throws SQLException, NotFoundException {
		String sql = "SELECT * FROM vinhos WHERE idVinho = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();

			if (!result.next()) {
				throw new NotFoundException("Vinho não encontrado!");
			}

			return parseVinho(result);
		}
	}

	public List<Vinho> getAll() throws SQLException {
		String sql = "SELECT * FROM vinhos";
		List<Vinho> vinhos = new ArrayList<>();

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stm = connection.prepareStatement(sql);
				ResultSet result = stm.executeQuery()) {

			while (result.next()) {
				vinhos.add(parseVinho(result));
			}

		}

		return vinhos;
	}

	public void update(Vinho vinho) throws SQLException, NotFoundException {
		String sql = "UPDATE vinhos SET nomeVinho = ?, fotoVinho = ?, preco = ? , "
				+ "nomeVinicola = ? , cidade = ? , teorAlcoolico = ? , docura = ?, "
				+ "fotoBandeira = ?, blend = ?, quantidadeDisponivel = ?  WHERE idVinho = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setString(1, vinho.getNomeVinho());
			stm.setString(2, vinho.getFotoVinho());
			stm.setDouble(3, vinho.getPreco());
			stm.setString(4, vinho.getNomeVinicola());
			stm.setString(5, vinho.getCidade());
			stm.setString(6, vinho.getTeorAlcoolico());
			stm.setString(7, vinho.getDocura());
			stm.setString(8, vinho.getFotoBandeira());
			stm.setString(9, vinho.getBlend());
			stm.setInt(10, vinho.getQuantidadeDisponivel());
			stm.setInt(11, vinho.getIdVinho());

			int affectedRows = stm.executeUpdate();

			if (affectedRows == 0) {
				throw new NotFoundException("Vinho não encontrado para atualização.");
			}
		}
	}

	public void delete(int id) throws SQLException, NotFoundException {
		String sql = "DELETE FROM vinhos WHERE idVinho = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, id);
			int affectedRows = stm.executeUpdate();

			if (affectedRows == 0) {
				throw new NotFoundException("Vinho não encontrado para exclusão.");
			}
		}
	}
}
