package Model;

public class ProdutoPedido {

	private String codigo_pedido;

	private String codigo_produto;

	private int idCategoria;

	private int qntProdutosCaixa;

	private double valorCaixa;

	public ProdutoPedido() {
		
	}

	public ProdutoPedido(String codigo_pedido, String codigo_produto, int idCategoria, int qntProdutosCaixa, double valorCaixa) {
		this.codigo_pedido = codigo_pedido;
		this.codigo_produto = codigo_produto;
		this.idCategoria = idCategoria;
		this.qntProdutosCaixa = qntProdutosCaixa;
		this.valorCaixa = valorCaixa;
	}

	public String getCodigo_pedido() {
		return codigo_pedido;
	}

	public void setCodigo_pedido(String codigo_pedido) {
		this.codigo_pedido = codigo_pedido;
	}

	public String getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(String codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getQntProdutosCaixa() {
		return qntProdutosCaixa;
	}

	public void setQntProdutosCaixa(int qntProdutosCaixa) {
		this.qntProdutosCaixa = qntProdutosCaixa;
	}

	public double getValorCaixa() {
		return valorCaixa;
	}

	public void setValorCaixa(double valorCaixa) {
		this.valorCaixa = valorCaixa;
	}
}
