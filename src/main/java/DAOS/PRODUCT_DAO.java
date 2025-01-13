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
	                 "JOIN categorias b ON b.idCategorias = a.idCategoria";

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

			enviaNotificaoSucesso(request, response);

		} catch (Exception e) {
			enviaNotificaoErro(produto, request, response, e);
		}
	}

	public void enviaNotificaoSucesso(HttpServletRequest request, HttpServletResponse response) {
		String type = "Sucesso";
	    String status = "Produto inserido com Sucesso!";
	    
	    //Aqui estamos usando o conceito de Session:
	    // Os dados passados são armazenados de maneira temporaria e podem ser capturados enquanto estiver ativos 

	    // Armazenando os dados na sessão
	    request.getSession().setAttribute("type", type);
	    request.getSession().setAttribute("status", status);

		// Caso o insert tenha dado certo encaminha uma requisição ao ServLet no caminho abaixo
		try {
			response.sendRedirect("/CarvalhoStore/search/Home/Cadproduct");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void enviaNotificaoErro(Produto produto, HttpServletRequest request, HttpServletResponse response, Exception exception) {
				
		String type = "Erro";
		
		request.getSession().setAttribute("type", type);

		if (exception instanceof SQLIntegrityConstraintViolationException) { // Código já existente
		    String status = "Erro ao inserir produto código = " + produto.getCodigo() + " já cadastrado!";

		    request.getSession().setAttribute("status", status);

			// Essa requisição é capturada diretamente no arquivo CadProduct.jsp
			try {
				response.sendRedirect("/CarvalhoStore/search/Home/Cadproduct");

				/*
				 * Porque é enviada no caminho /search/Home/product, porque o ServerLet recebe
				 * esse caminho e chama o método cadastroProduto que receberá todas as
				 * categorias novamente e redicionará para o arquivo CadProduct.jsp
				 */
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			exception.printStackTrace();
		}
	}
}
