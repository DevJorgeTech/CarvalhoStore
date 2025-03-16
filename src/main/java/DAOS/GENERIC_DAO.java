package DAOS;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import Model.Pedido;
import Model.Produto;
import Model.ProdutoPedido;

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

			// Enquanto houver resultados, percorre as linhas e imprime os valores das
			// colunas
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

//	public String enviaNotificaoSucesso(int action, Class<?> classe) {
//		/* 1 = INSERT, 2 = UPDATE */
//
//		Gson gson = new Gson();
//		
//        // Criando um Map para armazenar os dados
//		Map<String, Object> dados = new HashMap<>();
//		
//		dados.put("type", "Sucesso");
//
//		if (action == 1) {
//			if (Produto.class.isAssignableFrom(classe)) {
//                dados.put("status", "Produto inserido com sucesso!");
//            } else if (Pedido.class.isAssignableFrom(classe)) {
//                dados.put("status", "Pedido inserido com sucesso!");
//            }
//			
//		} else if (action == 2) {
//
//			// Criando um Map para armazenar os dados
//			dados.put("type", "Sucesso");
//			dados.put("status", "Produto alterado com sucesso!");
//
//
//		}
//		// Convertendo o Map em JSON
//
//		return gson.toJson(dados);
//
//	}

	public String enviaNotificaoSucesso(int action, Class<?> classe) {
		/* 1 = INSERT, 2 = UPDATE */

		Gson gson = new Gson();

		Map<String, Object> dados = new HashMap<>();

		dados.put("type", "Sucesso");

		switch (action) {
		case 1: {
			if (ProdutoPedido.class.isAssignableFrom(classe)) {
				dados.put("status", "Operação Concluída com Sucesso!");
			}
			
			return gson.toJson(dados);
		}
		case 2: {
			dados.put("status", "Produto alterado com sucesso!");
			
			return gson.toJson(dados);
		}
		default:
			throw new IllegalArgumentException("Valor não identicado para a ação: " + action);
		}
	}

	public <E> String enviaNotificaoErro(Exception exception, E entidade, String msgPersonalizada, int action) {

		Gson gson = new Gson();
		Map<String, Object> dados = new HashMap<>();
		dados.put("type", "Erro");

		switch (action) {
		case 1: { // INSERT				
			if (exception instanceof BatchUpdateException && exception.getMessage().contains("Duplicate entry")) { // Código já existente						
				String duplicadoValor = exception.getMessage().split("'")[1];
				
				if (entidade instanceof Produto) {
					dados.put("status", "Erro ao inserir produto código = " + duplicadoValor + " já cadastrado!");
				} else if (entidade instanceof Pedido) {
					dados.put("status","Erro ao inserir pedido código = " + duplicadoValor + " já cadastrado!");
				} else {
					dados.put("status", "Erro desconhecido! Operação: INSERT");
				}
			} else {
				System.out.println("Erro Exception igual a null ou desconhecida!");
				System.out.println(entidade.getClass());
				System.out.println(exception.getCause());
			}

			return gson.toJson(dados);
		}
		case 2: { // UPDATE
			// TODO
		}
		case 3: { // PERSONALIZADA
			dados.put("status", msgPersonalizada);

			return gson.toJson(dados);
		}
		default:
			throw new IllegalArgumentException("Valor não identicado para a ação: " + action);
		}
	}
}
