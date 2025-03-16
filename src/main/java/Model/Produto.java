package Model;

import java.sql.Date;

import com.google.gson.annotations.Expose;

public class Produto {
	
	private String idProduto;
	
	@Expose
	private String nome;

	@Expose
	private double preco_Venda;
	
	@Expose
	private Categoria categoria;

	@Expose
	private String codigo;
	
	@Expose
	private int qnt_estoque;
	
	@Expose
	private String status;
	
	@Expose
	private Date dataCadastro;
	
	@Expose
	private Boolean recompra;
	
	public Produto() {
		
	}

	public Produto(String idProduto,String nome, double preco_Venda, Categoria categoria, String codigo, int qnt_estoque, String status,
			Date dataCadastro,Boolean recompra) {
		this.idProduto = idProduto;
		this.nome = nome;
		this.preco_Venda = preco_Venda;
		this.categoria = categoria;
		this.codigo = codigo;
		this.qnt_estoque = qnt_estoque;
		this.status = status;
		this.dataCadastro = dataCadastro;
		this.recompra = recompra;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public double getPreco_Venda() {
		return preco_Venda;
	}

	public void setPreco_Venda(double preco_Venda) {
		this.preco_Venda = preco_Venda;
	}

	public int getQnt_estoque() {
		return qnt_estoque;
	}

	public void setQnt_estoque(int qnt_estoque) {
		this.qnt_estoque = qnt_estoque;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	public Boolean getRecompra() {
		return recompra;
	}

	public void setRecompra(Boolean recompra) {
		this.recompra = recompra;
	}
}
