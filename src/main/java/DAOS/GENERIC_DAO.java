package DAOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Produto;

public class GENERIC_DAO {

	private String driver = "com.mysql.cj.jdbc.Driver";

	private String url = "jdbc:mysql://127.0.0.1:3306/carvalhostore?useTimezone=true&serverTimezone=UTC";

	private String user = "root";

	private String senha = "12345";

	public Connection conectar() {
		Connection con = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, senha);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void fecharConexao(Connection conexao) {
		if (conexao != null) {
			try {
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar a conexão: " + e.getMessage());
			}
		}
	}
	
	public void selectElementById(String tableName, String idName, String idValue) {
	    String sql = "SELECT * FROM " + tableName + " WHERE " + idName + " = ?";
	    
	    try {
	        // Conecta ao banco de dados
	        Connection con = conectar();
	        PreparedStatement pst = con.prepareStatement(sql);
	        
	        // Define o valor para a cláusula WHERE
	        pst.setString(1, idValue);
	        
	        // Executa a consulta
	        ResultSet rs = pst.executeQuery();
	        
	        // Obtém o ResultSetMetaData para acessar informações sobre as colunas
	        ResultSetMetaData metaData = rs.getMetaData();
	        
	        // Obtém o número de colunas no ResultSet
	        int columnCount = metaData.getColumnCount();
	        
	        // Enquanto houver resultados, percorre as linhas e imprime os valores das colunas
	        while (rs.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                // Obtém o nome da coluna
	                String columnName = metaData.getColumnName(i);
	                
	                // Obtém o valor da coluna
	                Object columnValue = rs.getObject(i);
	                
	                // Imprime o nome da coluna e seu valor
	                System.out.println(columnName + ": " + columnValue);
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void enviaNotificaoSucesso(int action, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 1 = INSERT
		 * 2 = UPDATE
		 */
				
		String type = "Sucesso";
		
		if(action == 1) { 
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
		} else if (action == 2) {
			String status = "Produto alterado com Sucesso!";
			
			request.getSession().setAttribute("type", type);
		    request.getSession().setAttribute("status", status);
		    
		    try {
				response.sendRedirect("/CarvalhoStore/search/Home/ListProduct");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void enviaNotificaoErro(int action, Produto produto, HttpServletRequest request, HttpServletResponse response, Exception exception) {
		
		String type = "Erro";
		
		request.getSession().setAttribute("type", type);

		if (exception instanceof SQLIntegrityConstraintViolationException) { // Código já existente
		    String status = "Erro ao inserir produto código = " + produto.getCodigo() + " já cadastrado!";

		    request.getSession().setAttribute("status", status);

			// Essa requisição é capturada diretamente no arquivo CadProduct.jsp
			try {
				if (action == 1) {
					response.sendRedirect("/CarvalhoStore/search/Home/Cadproduct");
				} else if (action == 2) {
					response.sendRedirect("/CarvalhoStore/search/Home/UpProduct");
				}

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
