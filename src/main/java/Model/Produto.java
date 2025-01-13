package Model;

public class Produto {
	
	private String idProduto;
	
	private String nome;
	
	private double vp;
	
	private String codigo;
	
	private int categoria;
	
	public Produto() {
		
	}

	public Produto(String idProduto, String nome, double vp, String codigo, int categoria) {
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.vp = vp;
		this.codigo = codigo;
		this.categoria = categoria;
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

	public double getVp() {
		return vp;
	}

	public void setVp(double vp) {
		this.vp = vp;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}	
}
