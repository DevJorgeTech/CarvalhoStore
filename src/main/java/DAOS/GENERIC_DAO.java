package DAOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;

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

//	public void enviarStatusErro(Exception erro) {
//
//		if (erro instanceof SQLIntegrityConstraintViolationException) {
//			
//			String id = "1";
//
//			String type = "Erro Código de Produto já existente!";
//			
//			enviarRequisicaoServletComHttpClientErros(type, id);
//		}
//	}

//	private void enviarRequisicaoServletComHttpClientErros(String type, String id) {
//		try {
//			request.setAttribute("erro", "Erro ao inserir produto: " + e.getMessage());
//	        
//	        // Redireciona para a página JSP
//	        try {
//	            RequestDispatcher dispatcher = request.getRequestDispatcher("pagina.jsp");
//	            dispatcher.forward(request, response);
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Erro ao enviar requisição Class: GENERIC_DAO.");
//		}
//	}
}
