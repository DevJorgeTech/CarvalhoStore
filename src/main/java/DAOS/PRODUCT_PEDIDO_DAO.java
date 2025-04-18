package DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Model.ProdutoPedido;

public class PRODUCT_PEDIDO_DAO extends GENERIC_DAO {

	public String insertProductsPedidos(List<ProdutoPedido> listProdutosPedidos) {
		String sql = "INSERT INTO produtos_pedidos "
				+ "(Codigo_Pedido,Codigo_Produto,Qnt_Produtos_Caixa,Valor_Caixa)" + "VALUES"
				+ "(?,?,?,?)";

		Connection con = conectar();

		try {

			con.setAutoCommit(false);

			PreparedStatement pst = con.prepareStatement(sql);
			
			for (ProdutoPedido produtoPedido : listProdutosPedidos) {
				pst.setString(1, produtoPedido.getCodigo_pedido());
				pst.setString(2, produtoPedido.getCodigo_produto());
				pst.setInt(3, produtoPedido.getQntProdutosCaixa());
				pst.setDouble(4, produtoPedido.getValorCaixa());
				
				pst.addBatch();
			}

			pst.executeBatch();
			
			con.commit();

			fecharConexao(con);

			return enviaNotificaoSucesso(1, ProdutoPedido.class);

		} catch (Exception e) {

			try {
				con.rollback();
				fecharConexao(con);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			return enviaNotificaoErro(e, new ProdutoPedido(), null, 1);
		}
	}
}
