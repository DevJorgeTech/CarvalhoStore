package DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Categoria;

public class CATEGORY_DAO extends GENERIC_DAO{
	
	public ArrayList<Categoria> selectAllCategorys() {
		
		ArrayList<Categoria> categorias = new ArrayList<>();
		
		String sql = "SELECT * FROM CATEGORIAS";
	
		try {
			
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				String idProduto = rs.getString(1);
				String nomeProduto = rs.getString(2);

				
				categorias.add(new Categoria(idProduto,nomeProduto));
			}
			
			fecharConexao(con);
			
			return categorias;
			
		} catch (SQLException e) {
			System.out.println(e);
			
			return null;
		} 
	}
}
