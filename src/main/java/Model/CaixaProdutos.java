package Model;

public class CaixaProdutos {
	
	String codigoProduto;
	
	int qntProdutosCx;
	
	double valor_cx;

	
	public CaixaProdutos() {
		
	}
	
	public CaixaProdutos(String codigoProduto, int qntProdutosCx, double valor_cx) {
		this.codigoProduto = codigoProduto;
		this.qntProdutosCx = qntProdutosCx;
		this.valor_cx = valor_cx;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public int getQntProdutosCx() {
		return qntProdutosCx;
	}

	public void setQntProdutosCx(int qntProdutosCx) {
		this.qntProdutosCx = qntProdutosCx;
	}

	public double getValor_cx() {
		return valor_cx;
	}

	public void setValor_cx(double valor_cx) {
		this.valor_cx = valor_cx;
	}	
}
