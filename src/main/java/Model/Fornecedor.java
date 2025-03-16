package Model;

import java.sql.Date;

public class Fornecedor {

	private int idFornecedor;

	private String nome;

	private String contato;

	private String cnpj;
	
	private Date dataDeCadastro;

	public Fornecedor() {
		
	}

	public Fornecedor(int idFornecedor, String nome, String contato, String cnpj, Date dataDeCadastro) {
		this.idFornecedor = idFornecedor;
		this.nome = nome;
		this.contato = contato;
		this.cnpj = cnpj;
		this.dataDeCadastro = dataDeCadastro;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataDeCadastro() {
		return dataDeCadastro;
	}

	public void setDataDeCadastro(Date dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}
}
