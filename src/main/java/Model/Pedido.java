package Model;

import java.sql.Date;

public class Pedido {

	private int idPedido;

	private String codigoPedido;

	private Date dataPedido;

	private int idFornecedor;

	private double vtotalPedido;

	public Pedido() {
		
	}

	public Pedido(int idPedido, String codigoPedido, Date dataPedido, int idFornecedor, double vtotalPedido) {
		this.idPedido = idPedido;
		this.codigoPedido = codigoPedido;
		this.dataPedido = dataPedido;
		this.idFornecedor = idFornecedor;
		this.vtotalPedido = vtotalPedido;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public double getVtotalPedido() {
		return vtotalPedido;
	}

	public void setVtotalPedido(double vtotalPedido) {
		this.vtotalPedido = vtotalPedido;
	}
}
