package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DAOS.CATEGORY_DAO;
import DAOS.PRODUCT_DAO;
import Model.Categoria;
import Model.Produto;

/**
 * Servlet implementation class ServerletProduct
 */
@WebServlet(urlPatterns = { "/ServerletProduct", "/search/Home/Cadproduct", "/search/Home/insert",
		"/search/Home/ListProduct", "/search/Home/UpProduct", "/search/Home/update", "/search/Home/search" })
@MultipartConfig // Necessária para capturar requests com parametros no body
public class ServerletProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PRODUCT_DAO product_dao = new PRODUCT_DAO();

	CATEGORY_DAO category_dao = new CATEGORY_DAO();

	Produto produto = new Produto();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServerletProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
		} else if (action.equals("/search/Home/search")) {
			searchProductLike(request, response);
		} else {
			// Encaminha para a página padrão se nenhuma ação for correspondida
			request.getRequestDispatcher("/search/Home/Home.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		System.out.println(action + " doPost");

		if (action.equals("/search/Home/insert")) {
			inserirProduto(request, response);
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

	private void inserirProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		produto.setNome(request.getParameter("nome"));
		produto.setVp((Double.parseDouble(request.getParameter("vp"))));
		produto.setCodigo(request.getParameter("codigo"));
		produto.setCategoria(category_dao.selectCategoryById(Integer.parseInt(request.getParameter("category"))));
		produto.setDataCadastro(null);

		String resposta = product_dao.insertProduct(produto, request, response);

		System.out.println(resposta);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(resposta);
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
		request.setAttribute("productVp", produto.getVp());
		request.setAttribute("productCategory", produto.getCategoria());
		request.setAttribute("productCodigo", produto.getCodigo());

		ArrayList<Categoria> categorias = category_dao.selectAllCategorysByDiferentThen(produto.getCategoria());

		request.setAttribute("categorias", categorias);

		RequestDispatcher rd = request.getRequestDispatcher("/search/UpProduct/UpdateProduct.jsp");

		rd.forward(request, response);
	}

	private void updateProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		produto.setNome(request.getParameter("nome"));
		produto.setVp(Double.parseDouble(request.getParameter("vp")));
		produto.setCodigo(request.getParameter("codigo"));
		produto.setCategoria(category_dao.selectCategoryById(Integer.parseInt(request.getParameter("category"))));

		String resposta = product_dao.updateProduct(produto, request, response);
		
		System.out.println(resposta);
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(resposta);

	}

	private void searchProductLike(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Gson gson = new Gson(); // Objeto para serialização de JSON (Conversão de JSON x JAVA e etc)

	    JsonObject responseJson = new JsonObject();  // Criando o objeto JSON (Podemos motificar como quisermos)

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
	    
	    // Configurando a resposta HTTP
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    // Enviando a resposta
	    response.getWriter().write(responseJsonString);
	    
		System.out.println(responseJsonString);

	}
}
