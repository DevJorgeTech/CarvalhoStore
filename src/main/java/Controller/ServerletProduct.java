package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openxmlformats.schemas.officeDocument.x2006.math.MathPrDocument;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mysql.cj.Session;

import DAOS.CATEGORY_DAO;
import DAOS.PRODUCT_DAO;
import Model.CaixaProdutos;
import Model.Categoria;
import Model.Produto;
import Model.ProdutoPedido;
import Model.ProdutosNotRecompraDeserializer;
import Model.ProdutosRecompraDeserializer;
import Model.ValorPedidoDeserializer;

/**
 * Servlet implementation class ServerletProduct
 */
@WebServlet(urlPatterns = { "/ServerletProduct", "/search/Home/Cadproduct", "/search/Home/insertProduto",
		"/search/Home/ListProduct", "/search/Home/UpProduct", "/search/Home/update", "/search/Home/searchProduct" })
@MultipartConfig // Necessária para capturar requests com parametros no body
public class ServerletProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PRODUCT_DAO product_dao = new PRODUCT_DAO();

	CATEGORY_DAO category_dao = new CATEGORY_DAO();

	ProdutoPedido produtoPedido = new ProdutoPedido();

	Produto produto = new Produto();

	List<Produto> produtosNotRecompra = new ArrayList<>();

	List<Produto> produtosRecompra = new ArrayList<>();

	List<CaixaProdutos> caixas = new ArrayList<>();

	public ServerletProduct() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		System.out.println(action + " doGet");

		if (action.equals("/search/Home/Cadproduct")) {
			cadastroProduto(request, response);
		} else if (action.equals("/search/Home/ListProduct")) {
			listaDeProdutosByUpdate(request, response);
		} else if (action.equals("/search/Home/UpProduct")) {
			produtoByUpdate(request, response);
		} else if (action.equals("/search/Home/searchProduct")) {
			searchProductLike(request, response);
		} else {
			// Encaminha para a página padrão se nenhuma ação for correspondida
			request.getRequestDispatcher("/search/Home/Home.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		System.out.println(action + " doPost ServerLetProduto");

		if (action.equals("/search/Home/insertProduto")) {
			validaProdutos(request, response);
		} else if (action.equals("/search/Home/update")) {
			updateProduto(request, response);
		}
	};

	private void cadastroProduto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ArrayList<Categoria> categorias = category_dao.selectAllCategorys();

		request.setAttribute("categorias", categorias);

		RequestDispatcher rd = request.getRequestDispatcher("/search/CadProduct/CadProduct.jsp");

		rd.forward(request, response);
	}

	private void validaProdutos(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		trataJsonProdutos(request, response);

		Boolean valorOk = validaValorPedido(request, response);

		if (valorOk) {
			gerenciarProdutos(request, response);
		}
	}

	private void trataJsonProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		// Cria o JSON linha a linha
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String json = sb.toString();
		
		System.out.println(json);

		Gson gson = new GsonBuilder().registerTypeAdapter(List.class, new ProdutosNotRecompraDeserializer())
				.excludeFieldsWithoutExposeAnnotation().create();
		
		this.produtosNotRecompra = gson.fromJson(json, List.class);

		Gson gson2 = new GsonBuilder().registerTypeAdapter(List.class, new ProdutosRecompraDeserializer())
				.excludeFieldsWithoutExposeAnnotation().create();

		this.produtosRecompra = gson2.fromJson(json, List.class);

		Gson gson3 = new GsonBuilder().registerTypeAdapter(List.class, new ValorPedidoDeserializer())
				.excludeFieldsWithoutExposeAnnotation().create();

		this.caixas = gson3.fromJson(json, List.class);
	}

	private Boolean validaValorPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		double valorTotalPedido = (double) session.getAttribute("valorTotalPedido");

		double valorTotalAux = caixas.stream().mapToDouble(CaixaProdutos::getValor_cx).sum();

		if (valorTotalPedido != valorTotalAux) {
			String resposta = product_dao.enviaNotificaoErro(null, null,
					"Valor total inserido não corresponde ao valor do pedido!", 3);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(resposta);

			return false;
		}

		return true;
	}

	private void gerenciarProdutos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		atualizaQntEstoque(produtosRecompra, caixas);
		
		String resposta = product_dao.gerenciaProdutos(produtosNotRecompra, produtosRecompra);

		if (resposta.equals("OK")) {
			enviaRequestToServerLetPedido(request, response);
		} else {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(resposta);
		}
	}

	private void atualizaQntEstoque(List<Produto> produtosRecompra, List<CaixaProdutos> cx) {
		int novo_estoque = 0;

		for (Produto produto : produtosRecompra) {
			product_dao.selectProductQnt_estoqueByCodigo(produto);
			for (CaixaProdutos caixaProdutos : cx) {
				if (produto.getCodigo().equals(caixaProdutos.getCodigoProduto())) {
					novo_estoque = produto.getQnt_estoque() + caixaProdutos.getQntProdutosCx();
					produto.setQnt_estoque(novo_estoque);
				}
			}
		}
		;
	}

	private void listaDeProdutosByUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ArrayList<Produto> produtos = product_dao.selectAllProductJoinCategory();

		request.setAttribute("produtos", produtos);

		RequestDispatcher rd = request.getRequestDispatcher("/search/UpProduct/Products.jsp");
		rd.forward(request, response);
	}

	private void produtoByUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		produto.setIdProduto(request.getParameter("idProduct"));

		product_dao.selectProductById(produto);

		request.setAttribute("productName", produto.getNome());
		request.setAttribute("productPrecoVenda", produto.getPreco_Venda());
		request.setAttribute("productCategory", produto.getCategoria());
		request.setAttribute("productCodigo", produto.getCodigo());
		request.setAttribute("productStatus", produto.getStatus());
		
		System.out.println(produto.getStatus());
				
		ArrayList<Categoria> categorias = category_dao.selectAllCategorysByDiferentThen(produto.getCategoria());

		request.setAttribute("categorias", categorias);

		RequestDispatcher rd = request.getRequestDispatcher("/search/UpProduct/UpdateProduct.jsp");

		rd.forward(request, response);
	}

	private void updateProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("updateProduto");
		
		request.setCharacterEncoding("UTF-8");
		produto.setNome(request.getParameter("nome"));
		produto.setPreco_Venda(Double.parseDouble(request.getParameter("preco_venda")));
		produto.setCodigo(request.getParameter("codigo"));
		produto.setCategoria(category_dao.selectCategoryById(Integer.parseInt(request.getParameter("category"))));
		produto.setStatus(request.getParameter("status"));

		String resposta = product_dao.updateProduct(produto);
		
		System.out.println(resposta);
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(resposta);

	}

	private void searchProductLike(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Gson gson = new Gson(); // Objeto para serialização de JSON (Conversão de JSON x JAVA e etc)

		JsonObject responseJson = new JsonObject(); // Criando o objeto JSON (Podemos motificar como quisermos)

		String like = request.getParameter("search");

		if (like.isEmpty()) {
			ArrayList<Produto> produtos = product_dao.selectAllProductJoinCategory();

			// Adicionando os produtos no objeto JSON
			responseJson.add("produtos", gson.toJsonTree(produtos));

		} else {
			ArrayList<Produto> produtos = product_dao.selectProductLike(like);
						
			// Adicionando os produtos no objeto JSON
			responseJson.add("produtos", gson.toJsonTree(produtos)); // Adicionamos uma chave principal
		}
				
		// Convertendo o objeto JSON para uma string
		String responseJsonString = gson.toJson(responseJson);
		
		System.out.println(responseJsonString);

		// Configurando a resposta HTTP
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Enviando a resposta
		response.getWriter().write(responseJsonString);

	}

	public void enviaRequestToServerLetPedido(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Produto> produtos = new ArrayList<>();

		produtos.addAll(produtosRecompra);
		produtos.addAll(produtosNotRecompra);

		montaProdutoPedido(produtos, request, response);

		RequestDispatcher rd = request.getRequestDispatcher("/search/Home/insertPedido");

		rd.forward(request, response);
	}

	private void montaProdutoPedido(List<Produto> produtos, HttpServletRequest request, HttpServletResponse response) {
		List<ProdutoPedido> produtosPedido = new ArrayList<>();

		HttpSession session = request.getSession(false);

		for (Produto produto : produtos) {
			ProdutoPedido produtoPedido = new ProdutoPedido();

			produtoPedido.setCodigo_pedido((String) session.getAttribute("codigoPedido"));
			produtoPedido.setCodigo_produto(produto.getCodigo());
			produtoPedido.setIdCategoria(produto.getCategoria().getIdCategory());

			for (CaixaProdutos cx : caixas) {
				if (cx.getCodigoProduto().equalsIgnoreCase(produto.getCodigo())) {
					produtoPedido.setQntProdutosCaixa(cx.getQntProdutosCx());
					produtoPedido.setValorCaixa(cx.getValor_cx());
				}
			}

			produtosPedido.add(produtoPedido);
		}
		request.setAttribute("produtosPedidos", produtosPedido);
	}
}
