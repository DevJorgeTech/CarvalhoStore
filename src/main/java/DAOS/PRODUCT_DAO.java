package DAOS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Categoria;
import Model.Produto;

public class PRODUCT_DAO extends GENERIC_DAO {

	public ArrayList<Produto> selectAllProduct() {
		ArrayList<Produto> produtos = new ArrayList<>();

		String sql = "SELECT * FROM PRODUTOS";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				
				// Os número dentro dos getters devem seguir a ordem da tabela no banco
				
				String idProduto = rs.getString(1);
				String nomeProduto = rs.getString(2);
				double vp = rs.getDouble(3);
				int idCategoria = rs.getInt(4);
				
	            Categoria categoria = new Categoria(idCategoria,null);
				
				String codigo = rs.getString(5);
	            Timestamp dataCadastroTimestamp = rs.getTimestamp(6);				
	            Date dataCadastro = (dataCadastroTimestamp != null) ? new Date(dataCadastroTimestamp.getTime()) : null;
                					          	            
	            produtos.add(new Produto(idProduto, nomeProduto, vp, codigo, categoria, dataCadastro));
			}

			fecharConexao(con);

			return produtos;

		} catch (SQLException e) {
			System.out.println(e);

			return null;
		}
	}
	
	public ArrayList<Produto> selectAllProductJoinCategory() {
	    ArrayList<Produto> produtos = new ArrayList<>();

	    String sql = "SELECT a.idProdutos, a.nome, a.vp, b.idCategorias, b.descricao, a.codigo, a.dataCadastro " +
	             "FROM produtos a " +
	             "JOIN categorias b ON b.idCategorias = a.idCategoria " +
	             "ORDER BY a.idProdutos";


	    try (Connection con = conectar();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {

	        while (rs.next()) {
	            String idProduto = rs.getString(1);
	            String nomeProduto = rs.getString(2);
	            double vp = rs.getDouble(3);

	            int idCategoria = rs.getInt(4);
	            String descricaoCategoria = rs.getString(5);
	            Categoria categoria = new Categoria(idCategoria, descricaoCategoria); 

	            String codigo = rs.getString(6);
	            Timestamp dataCadastroTimestamp = rs.getTimestamp(7);
	            Date dataCadastro = (dataCadastroTimestamp != null) ? new Date(dataCadastroTimestamp.getTime()) : null;

	            produtos.add(new Produto(idProduto, nomeProduto, vp, codigo, categoria, dataCadastro));
	        }
	    } catch (SQLException e) {
	        System.out.println(e);
	    }

	    return produtos;
	}
	
	public void selectProductById(Produto produto){
						
		String sql = "SELECT a.nome, a.vp, b.idCategorias, b.descricao, a.codigo, a.dataCadastro " +
	             "FROM produtos a " +
	             "JOIN categorias b ON b.idCategorias = a.idCategoria " +
	             "WHERE IDPRODUTOS = ?";

		
		try {
			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, produto.getIdProduto());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				produto.setNome(rs.getString(1));          	            
				produto.setVp(rs.getDouble(2));          	
				
				Categoria categoria = new Categoria(rs.getInt(3),rs.getString(4));

				produto.setCategoria(categoria);          	            
				produto.setCodigo(rs.getString(5));          	            
				produto.setDataCadastro(rs.getDate(6));          	            
			}

			fecharConexao(con);

		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	public ArrayList<Produto> selectProductLike(String like) {
		
		ArrayList<Produto> produtos = new ArrayList<>();
		
		String sql = "SELECT a.idProdutos, a.nome, a.vp, b.idCategorias, b.descricao, a.codigo, a.dataCadastro  \r\n"
		           + "FROM produtos a  \r\n"
		           + "JOIN categorias b ON b.idCategorias = a.idCategoria  \r\n"
		           + "WHERE lower(a.nome) LIKE lower(?)";
		
		try {
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, "%" + like + "%");
			
			ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()) {
				Categoria categoria = new Categoria(rs.getInt(4),rs.getString(5));
				
				produtos.add(new Produto(
					    rs.getString(1),  // idProduto
					    rs.getString(2),  // nome
					    rs.getDouble(3),  // vp
					    rs.getString(6),  // codigo
					    categoria,        // categoria
					    rs.getDate(7)     // dataCadastro
				));
			}
			
			return produtos;
			
		} catch (Exception e) {
			e.getStackTrace();
			
			return null;
		}

	}

	public void insertProduct(Produto produto, HttpServletRequest request, HttpServletResponse response) {
		
		String sql = "INSERT INTO produtos (nome, vp, idCategoria, codigo, dataCadastro) VALUES (?,?,?,?,?)";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, produto.getNome());
			pst.setDouble(2, produto.getVp());
			pst.setInt(3, produto.getCategoria().getIdCategory());
			pst.setString(4, produto.getCodigo());
			pst.setDate(5, produto.getDataCadastro());

			pst.executeUpdate();

			fecharConexao(con);

			enviaNotificaoSucesso(1, request, response);

		} catch (Exception e) {
			enviaNotificaoErro(1, produto, request, response, e);
		}
	}
	
	public void updateProduct(Produto produto, HttpServletRequest request, HttpServletResponse response) {
		String sql = "UPDATE PRODUTOS " +
	             	 "SET NOME = ?, VP = ?, IDCATEGORIA = ?, CODIGO = ? " +
	             	 "WHERE IDPRODUTOS = ?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, produto.getNome());
			pst.setDouble(2, produto.getVp());
			pst.setInt(3, produto.getCategoria().getIdCategory());
			pst.setString(4, produto.getCodigo());
			pst.setString(5, produto.getIdProduto());
			
			pst.executeUpdate();
			
			fecharConexao(con);
			
			enviaNotificaoSucesso(2, request, response);
						
		} catch (Exception e) {
			enviaNotificaoErro(2, produto, request, response, e);
		}

	}
}
