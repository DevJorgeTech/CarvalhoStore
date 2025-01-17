package DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Categoria;

public class CATEGORY_DAO extends GENERIC_DAO {

	public ArrayList<Categoria> selectAllCategorys() {

		ArrayList<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT * FROM CATEGORIAS";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int idCategory = rs.getInt(1);
				String descricao = rs.getString(2);

				categorias.add(new Categoria(idCategory, descricao));
			}

			fecharConexao(con);

			return categorias;

		} catch (SQLException e) {
			System.out.println(e);

			return null;
		}
	}

	public Categoria selectCategoryById(int idCategory) {
		
		Categoria categoria = new Categoria();

		String sql = "SELECT * FROM CATEGORIAS WHERE IDCATEGORIAS = ?";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setInt(1, idCategory);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int idCategory1 = rs.getInt(1);
				String descricao = rs.getString(2);
				
				categoria.setIdCategory(idCategory1);
				categoria.setDescricao(descricao);
				
			}

			
			fecharConexao(con);

			return categoria;

		} catch (SQLException e) {
			System.out.println(e);

			return null;
		}
	}
	
	public ArrayList<Categoria> selectAllCategorysByDiferentThen(Categoria categoria) {

		ArrayList<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT * FROM CATEGORIAS WHERE IDCATEGORIAS != ?";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setInt(1, categoria.getIdCategory());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int idCategory = rs.getInt(1);
				String descricao = rs.getString(2);

				categorias.add(new Categoria(idCategory, descricao));
			}

			fecharConexao(con);

			return categorias;

		} catch (SQLException e) {
			System.out.println(e);

			return null;
		}
	}
}
