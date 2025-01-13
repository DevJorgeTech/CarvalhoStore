package Model;

public class Categoria {
	
	private int idCategory;
	
	private String descricao;
	
	public Categoria() {
		
	}

	public Categoria(int idCategory, String descricao) {
		this.idCategory = idCategory;
		this.descricao = descricao;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
}
