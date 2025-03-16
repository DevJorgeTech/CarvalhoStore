package Model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import DAOS.CATEGORY_DAO;

//Implementação da Interface JsonDeserializer com Objetivo de manipular JSON
public class ProdutosNotRecompraDeserializer implements JsonDeserializer<List<Produto>> {
	
	CATEGORY_DAO category_dao = new CATEGORY_DAO();	
	
	 @Override
	    public List<Produto> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	     
		 //Transforma o JSON em Objeto
		 JsonObject jsonObject = json.getAsJsonObject();
	        
	        // Extrair o objeto "Produtos"
	        JsonObject produtosJson = jsonObject.getAsJsonObject("Produtos");
	        
	        List<Produto> produtos = new ArrayList<>();
	        
	        // Passa por todas as chaves internas a "Produtos": Produto1, Produto2
	        for (String chaveProduto : produtosJson.keySet()) {
	        		        	
	        	//Captura os Objetos Filhos Produto1, Produto2......
				JsonObject produtoJson = produtosJson.getAsJsonObject(chaveProduto);
	            
	            if (!produtoJson.get("recompra").getAsBoolean()) {
	            	Produto produto = new Produto();
		            //produtoJson.get método para captura os att do objeto filho
		            produto.setNome(produtoJson.get("nome").getAsString());
		            produto.setCategoria(category_dao.selectCategoryById(produtoJson.get("categoria").getAsInt()));
		            produto.setPreco_Venda(produtoJson.get("preco_venda").getAsDouble());
		            produto.setCodigo(produtoJson.get("codigo").getAsString());
		            produto.setQnt_estoque(produtoJson.get("qnt_cx").getAsInt());
		            produto.setRecompra(produtoJson.get("recompra").getAsBoolean());
		            
		            produtos.add(produto);
				}
	        }
	        
	        return produtos;
	    }
}
