package DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				String idProduto = rs.getString(1);
				String nomeProduto = rs.getString(2);
				double vp = rs.getDouble(1);
				String codigo = rs.getString(3);
				int categoria = rs.getInt(1);

				produtos.add(new Produto(idProduto, nomeProduto, vp, codigo, categoria));
			}

			fecharConexao(con);

			return produtos;

		} catch (SQLException e) {
			System.out.println(e);

			return null;
		}
	}

	public void insertProduct(Produto produto, HttpServletRequest request, HttpServletResponse response) {

		String sql = "INSERT INTO produtos (nome, vp, idCategoria, codigo) VALUES (?,?,?,?)";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, produto.getNome());
			pst.setDouble(2, produto.getVp());
			pst.setInt(3, produto.getCategoria());
			pst.setString(4, produto.getCodigo());

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
		} 
	}
}
