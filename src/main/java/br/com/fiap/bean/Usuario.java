package br.com.fiap.bean;

import java.util.Date;


public class Usuario {
	private int idUsuario;
	private String nomeUsuario;
	private String emailUsuario;
	private String celular;
	private String senha;
	private int role;
	private Date dataAniversario;
	
	public Usuario() {}	
	public Usuario(int idUsuario, String nomeUsuario, String emailUsuario, String celular, String senha, int role,
			Date dataAniversario) {
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
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
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
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
