package Model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ValorPedidoDeserializer implements JsonDeserializer<List<CaixaProdutos>>{
	@Override
    public List<CaixaProdutos> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
     
	 //Transforma o JSON em Objeto
	 JsonObject jsonObject = json.getAsJsonObject();
        
        // Extrair o objeto "Produtos"
        JsonObject produtosJson = jsonObject.getAsJsonObject("Produtos");
        
        List<CaixaProdutos> caixaProdutos = new ArrayList<>();
        
        // Passa por todas as chaves internas a "Produtos": Produto1, Produto2
        for (String chaveProduto : produtosJson.keySet()) {
        		        	
        	//Captura os Objetos Filhos Produto1, Produto2......
			JsonObject produtoJson = produtosJson.getAsJsonObject(chaveProduto);
			                        
			CaixaProdutos cx = new CaixaProdutos();
			
			cx.setCodigoProduto(produtoJson.get("codigo").getAsString());
			cx.setQntProdutosCx(produtoJson.get("qnt_cx").getAsInt());
			cx.setValor_cx(produtoJson.get("valor_cx").getAsDouble());
			
			caixaProdutos.add(cx);
        }
        
        return caixaProdutos;
    }
}
