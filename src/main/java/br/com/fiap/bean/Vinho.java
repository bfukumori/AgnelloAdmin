package br.com.fiap.bean;

public class Vinho {
	private int idVinho;
	private String nomeVinho;
	private String fotoVinho;
	private double preco;
	private double desconto;
	private String nomeVinicola;
	private String teorAlcoolico;
	private String docura;
	private String fotoBandeira;
	private String blend;
	private int quantidadeDisponivel;
	
	public Vinho(){}	
	public Vinho(int idVinho, String nomeVinho, String fotoVinho, double preco, double desconto, String nomeVinicola,
			String teorAlcoolico, String docura, String fotoBandeira, String blend, int quantidadeDisponivel) {
		this.idVinho = idVinho;
		this.nomeVinho = nomeVinho;
		this.fotoVinho = fotoVinho;
		this.preco = preco;
		this.desconto = desconto;
		this.nomeVinicola = nomeVinicola;
		this.teorAlcoolico = teorAlcoolico;
		this.docura = docura;
		this.fotoBandeira = fotoBandeira;
		this.blend = blend;
		this.quantidadeDisponivel = quantidadeDisponivel;
	}
	public int getIdVinho() {
		return idVinho;
	}
	public void setIdVinho(int idVinho) {
		this.idVinho = idVinho;
	}
	public String getNomeVinho() {
		return nomeVinho;
	}
	public void setNomeVinho(String nomeVinho) {
		this.nomeVinho = nomeVinho;
	}
	public String getFotoVinho() {
		return fotoVinho;
	}
	public void setFotoVinho(String fotoVinho) {
		this.fotoVinho = fotoVinho;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public String getNomeVinicola() {
		return nomeVinicola;
	}
	public void setNomeVinicola(String nomeVinicola) {
		this.nomeVinicola = nomeVinicola;
	}
	public String getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(String teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}
	public String getDocura() {
		return docura;
	}
	public void setDocura(String docura) {
		this.docura = docura;
	}
	public String getFotoBandeira() {
		return fotoBandeira;
	}
	public void setFotoBandeira(String fotoBandeira) {
		this.fotoBandeira = fotoBandeira;
	}
	public String getBlend() {
		return blend;
	}
	public void setBlend(String blend) {
		this.blend = blend;
	}
	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}
}
