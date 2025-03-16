package DAOS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Model.Fornecedor;
import Model.Pedido;
import Model.Produto;

public class PEDIDO_DAO extends GENERIC_DAO {

	public ArrayList<Fornecedor> selectAllFornecedores() {

		ArrayList<Fornecedor> fornecedores = new ArrayList<>();

		String sql = "SELECT * FROM fornecedor";
		
		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Timestamp dataCadastroTimestamp = rs.getTimestamp(5);

				Date dataCadastro = (dataCadastroTimestamp != null) ? new Date(dataCadastroTimestamp.getTime()) : null;

				fornecedores.add(
						new Fornecedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), dataCadastro));
			}
			
			fecharConexao(con);

		} catch (Exception e) {
			
			fecharConexao(con);
			
			System.out.println("Erro ao buscar fornecedores!");
			System.out.println(e);
		}

		return fornecedores;
	}
		
	public Boolean selectPedidoByCodigoIfExist(String codigo) {
		String sql = "SELECT EXISTS (SELECT 1 FROM PEDIDO WHERE Codigo_Pedido = ?)";
		
		Boolean existPedido = false;

		Connection con = conectar();	
		
		try {
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, codigo);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				existPedido = rs.getBoolean(1);
			}
			
			fecharConexao(con);

			return existPedido;			
							
		} catch (Exception e) {
			
			fecharConexao(con);
			
			e.printStackTrace();
			
			return false;
		}
	}
	
	
	public int insertPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (Codigo_Pedido, idFornecedor, Vtotal_Pedido) VALUES (?, ?, ?)";
        
        Connection con = conectar();
        
		try {
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, pedido.getCodigoPedido());
			pst.setInt(2, pedido.getIdFornecedor());
			pst.setDouble(3, pedido.getVtotalPedido());
			
			int resposta = pst.executeUpdate();
			
			fecharConexao(con);
			
			return resposta;
			
		} catch (SQLException e) {
			
			fecharConexao(con);
			
			System.out.println("Erro ao inserir pedido!");
			
			e.printStackTrace();
			
			return 0;
		}

	}

}
