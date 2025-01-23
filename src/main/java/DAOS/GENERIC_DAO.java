package DAOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

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

	public String enviaNotificaoSucesso(int action) {
		/* 1 = INSERT, 2 = UPDATE */

		Gson gson = new Gson();
		Map<String, Object> dados = new HashMap<>();

		if (action == 1) {
			// Criando um Map para armazenar os dados
			dados.put("type", "Sucesso");
			dados.put("status", "Produto inserido com sucesso!");
			
		} else if (action == 2) {

			// Criando um Map para armazenar os dados
			dados.put("type", "Sucesso");
			dados.put("status", "Produto alterado com sucesso!");


		}
		// Convertendo o Map em JSON

		return gson.toJson(dados);

	}

	public String enviaNotificaoErro(int action, Produto produto, Exception exception) {

		Gson gson = new Gson();
		Map<String, Object> dados = new HashMap<>();
		
		if (exception instanceof SQLIntegrityConstraintViolationException) { // Código já existente
			
			dados.put("type", "Erro");
			dados.put("status", "Erro ao inserir produto código = " + produto.getCodigo() + " já cadastrado!");
			
		} else {
			exception.printStackTrace();
		}
		
		return gson.toJson(dados);
	}
}
