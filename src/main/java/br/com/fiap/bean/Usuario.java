package br.com.fiap.bean;

import java.sql.Date;

public class Usuario {
	private int idUsuario;
	private String nome;
	private String email;
	private String celular;
	private String senha;
	private int role;
	private Date dataAniversario;

	public Usuario() {
	}

	public Usuario(int idUsuario, String nome, String email, String celular, int role, Date dataAniversario) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.celular = celular;
		this.role = role;
		this.dataAniversario = dataAniversario;
	}

	public Usuario(int idUsuario, String nome, String email, String celular, String senha, int role,
			Date dataAniversario) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.celular = celular;
		this.senha = senha;
		this.role = role;
		this.dataAniversario = dataAniversario;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nomeUsuario) {
		this.nome = nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailUsuario) {
		this.email = emailUsuario;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

}
