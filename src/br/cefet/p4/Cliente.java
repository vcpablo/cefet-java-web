package br.cefet.p4;

public class Cliente {

	private long id;
	private String nome;
	private Cidade cidade;

	public Cliente(long id, String nome, Cidade cidade) {
		this.id = id;
		this.nome = nome;
		this.setCidade(cidade);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
}
