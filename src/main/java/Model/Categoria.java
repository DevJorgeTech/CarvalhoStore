package Model;

public class Categoria {
	
	private String idCategory;
	
	private String descricao;

	public Categoria() {
		
	}

	public Categoria(String idCategory, String descricao) {
		this.idCategory = idCategory;
		this.descricao = descricao;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
}
