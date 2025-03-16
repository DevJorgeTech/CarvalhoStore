package DAOS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Model.Categoria;
import Model.Produto;

public class PRODUCT_DAO extends GENERIC_DAO {

	public ArrayList<Produto> selectAllProduct() {
		ArrayList<Produto> produtos = new ArrayList<>();

		String sql = "SELECT * FROM PRODUTOS";

		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				// Os número dentro dos getters devem seguir a ordem da tabela no banco

				String idProduto = rs.getString(1);
				String nomeProduto = rs.getString(2);
				double preco_venda = rs.getDouble(3);
				int idCategoria = rs.getInt(4);

				Categoria categoria = new Categoria(idCategoria, null);

				String codigo = rs.getString(5);

				int qnt_estoque = rs.getInt(6);

				String status = rs.getString(7);

				Timestamp dataCadastroTimestamp = rs.getTimestamp(8);

				Date dataCadastro = (dataCadastroTimestamp != null) ? new Date(dataCadastroTimestamp.getTime()) : null;

				produtos.add(new Produto(idProduto, nomeProduto, preco_venda, categoria, codigo, qnt_estoque, status,
						dataCadastro, null));
			}

			fecharConexao(con);

			return produtos;

		} catch (SQLException e) {
			fecharConexao(con);

			e.printStackTrace();

			return null;
		}
	}

	public ArrayList<Produto> selectAllProductJoinCategory() {
		ArrayList<Produto> produtos = new ArrayList<>();

		String sql = "SELECT a.idProdutos, a.nome, a.Preço_Venda, b.idCategorias, "
				+ "b.descricao, a.codigo, a.quantidade_estoque, a.status_produto, a.dataCadastro " + "FROM produtos a "
				+ "JOIN categorias b ON b.idCategorias = a.idCategoria " + "ORDER BY a.idProdutos;";

		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String idProduto = rs.getString(1);
				String nomeProduto = rs.getString(2);
				double preco_venda = rs.getDouble(3);

				int idCategoria = rs.getInt(4);
				String descricaoCategoria = rs.getString(5);
				Categoria categoria = new Categoria(idCategoria, descricaoCategoria);

				String codigo = rs.getString(6);
				int qnt_estoque = rs.getInt(7);
				String status = rs.getString(8);
				Timestamp dataCadastroTimestamp = rs.getTimestamp(9);
				Date dataCadastro = (dataCadastroTimestamp != null) ? new Date(dataCadastroTimestamp.getTime()) : null;

				produtos.add(new Produto(idProduto, nomeProduto, preco_venda, categoria, codigo, qnt_estoque, status,
						dataCadastro, null));
			}

			fecharConexao(con);

		} catch (SQLException e) {
			fecharConexao(con);
			e.printStackTrace();

		}

		return produtos;
	}

	public void selectProductById(Produto produto) {

		String sql = "SELECT a.nome, a.Preço_Venda, b.idCategorias, b.descricao, a.codigo, "
				+ "a.quantidade_estoque, a.dataCadastro " + "FROM produtos a "
				+ "JOIN categorias b ON b.idCategorias = a.idCategoria " + "WHERE a.IDPRODUTOS = ?";

		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, produto.getIdProduto());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				produto.setNome(rs.getString(1));
				produto.setPreco_Venda(rs.getDouble(2));

				Categoria categoria = new Categoria(rs.getInt(3), rs.getString(4));

				produto.setCategoria(categoria);
				produto.setCodigo(rs.getString(5));
				produto.setQnt_estoque(rs.getInt(6));
				produto.setDataCadastro(rs.getDate(7));
			}

			fecharConexao(con);

		} catch (SQLException e) {
			fecharConexao(con);

			e.printStackTrace();

		}

	}

	public ArrayList<Produto> selectProductLike(String like) {

		ArrayList<Produto> produtos = new ArrayList<>();

		String sql = "SELECT a.idProdutos, a.nome, a.Preço_Venda, "
				+ "b.idCategorias, b.descricao, a.codigo, a.quantidade_estoque, " + "a.status_produto, a.dataCadastro "
				+ "FROM produtos a " + "JOIN categorias b ON b.idCategorias = a.idCategoria "
				+ "WHERE LOWER(a.nome) LIKE LOWER(?) AND STATUS_PRODUTO = 'ATIVO'";

		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, "%" + like + "%");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Categoria categoria = new Categoria(rs.getInt(4), rs.getString(5));

				produtos.add(new Produto(rs.getString(1), // idProduto
						rs.getString(2), // nome
						rs.getDouble(3), // Preço_Venda
						categoria, rs.getString(6), // codigo
						rs.getInt(7), // qnt_estoque
						rs.getString(8), // status
						rs.getDate(9), // dataCadastro
						null));
			}

			fecharConexao(con);

			return produtos;

		} catch (Exception e) {
			fecharConexao(con);

			e.getStackTrace();

			return null;
		}

	}

	public void selectProductQnt_estoqueByCodigo(Produto produto) {
		String sql = "SELECT idProdutos, codigo, quantidade_estoque FROM PRODUTOS "
				+ "WHERE CODIGO = ? AND status_produto = 'ATIVO'";

		Connection con = conectar();

		try {

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, produto.getCodigo());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				produto.setIdProduto(rs.getString(1));
				produto.setCodigo(rs.getString(2));
				produto.setQnt_estoque(rs.getInt(3));
			}

			fecharConexao(con);

		} catch (SQLException e) {
			fecharConexao(con);
			e.printStackTrace();
		}
	}

	public String insertProduct(List<Produto> produtos) {
		
		String sql = "INSERT INTO produtos (nome, Preço_Venda, idCategoria, codigo, quantidade_estoque) "
				+ "VALUES (?, ?, ?, ?, ?)";

		Connection con = conectar();

		try {

			con.setAutoCommit(false);

			PreparedStatement pst = con.prepareStatement(sql);

			for (Produto produto : produtos) {
				pst.setString(1, produto.getNome());
				pst.setDouble(2, produto.getPreco_Venda());
				pst.setInt(3, produto.getCategoria().getIdCategory());
				pst.setString(4, produto.getCodigo());
				pst.setInt(5, produto.getQnt_estoque());

				pst.addBatch(); // Funcionalidade do JDBC que permite executar várias operações simultaneamente
			}

			pst.executeBatch(); // Executa todos os comandos de uma vez

			con.commit();

			fecharConexao(con);

			return "OK";

		} catch (Exception e) {
			try {
				con.rollback();
				
				fecharConexao(con);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return enviaNotificaoErro(e, new Produto(), null,1);
		}
	}
	
	public String gerenciaProdutos(List<Produto> produtosNotRecompra, List<Produto> produtosRecompra) {
		String sql1 = "INSERT INTO produtos (nome, Preço_Venda, idCategoria, codigo, quantidade_estoque) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		String sql2 = "UPDATE produtos " + "SET quantidade_estoque = ? "
				+ "WHERE idProdutos = ? and codigo = ? and status_produto = 'ATIVO'";
		
		Connection con = conectar();
		
		try {
			con.setAutoCommit(false);
			
			PreparedStatement pstInsert  = con.prepareStatement(sql1);
			
			for (Produto produto : produtosNotRecompra) {
				pstInsert.setString(1, produto.getNome());
				pstInsert.setDouble(2, produto.getPreco_Venda());
				pstInsert.setInt(3, produto.getCategoria().getIdCategory());
				pstInsert.setString(4, produto.getCodigo());
				pstInsert.setInt(5, produto.getQnt_estoque());

				pstInsert.addBatch(); // Funcionalidade do JDBC que permite executar várias operações simultaneamente
			}
			
			PreparedStatement pstUpdate = con.prepareStatement(sql2);
			
			for (Produto produto : produtosRecompra) {
				pstUpdate.setInt(1, produto.getQnt_estoque());
				pstUpdate.setString(2, produto.getIdProduto());
				pstUpdate.setString(3, produto.getCodigo());

				pstUpdate.addBatch(); // Funcionalidade do JDBC que permite executar várias operações simultaneamente
			}
			
			pstInsert.executeBatch();
			pstUpdate.executeBatch(); // Executa todos os comandos de uma vez

			con.commit();
			
			fecharConexao(con);
			
			return "OK";
			
		} catch (Exception e) {
			try {
				con.rollback();
				fecharConexao(con);
				return enviaNotificaoErro(e, new Produto(), null, 1);
			} catch (SQLException e1) {
				e1.printStackTrace();
				
				return "";
			}
		}
	}

//	public String updateProduct(Produto produto) {
//		String sql = "UPDATE produtos "
//				+ "SET nome = ?, Preço_Venda = ?, idCategoria = ?, codigo = ?, quantidade_estoque = ?, "
//				+ "status_produto = ?" + "WHERE idProdutos = ?";
//
//		Connection con = conectar();
//
//		try {
//
//			PreparedStatement pst = con.prepareStatement(sql);
//
//			pst.setString(1, produto.getNome());
//			pst.setDouble(2, produto.getPreco_Venda());
//			pst.setInt(3, produto.getCategoria().getIdCategory());
//			pst.setString(4, produto.getCodigo());
//			pst.setInt(5, produto.getQnt_estoque());
//			pst.setString(6, produto.getStatus());
//			pst.setString(7, produto.getIdProduto());
//
//			pst.executeUpdate();
//
//			fecharConexao(con);
//
//			return enviaNotificaoSucesso(2, Produto.class);
//
//		} catch (Exception e) {
//			fecharConexao(con);
//
//			return enviaNotificaoErro(2, Produto.class, e, null);
//		}
//
//	}

//	public String updateQntEstoqueProduct(Produto produto) {
//		String sql = "UPDATE produtos " + "SET quantidade_estoque = ? "
//				+ "WHERE idProdutos = ? and codigo = ? and status_produto = 'ATIVO'";
//
//		try {
//			Connection con = conectar();
//
//			PreparedStatement pst = con.prepareStatement(sql);
//
//			pst.setInt(1, produto.getQnt_estoque());
//			pst.setString(2, produto.getIdProduto());
//			pst.setString(3, produto.getCodigo());
//
//			pst.executeUpdate();
//
//			fecharConexao(con);
//
//			return enviaNotificaoSucesso(2, Produto.class);
//		} catch (SQLException e) {
//			return enviaNotificaoErro(2, produto, e, null);
//		}
//	}
}
