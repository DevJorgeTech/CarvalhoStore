package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOS.CATEGORY_DAO;
import DAOS.PRODUCT_DAO;
import Model.Categoria;
import Model.Produto;

/**
 * Servlet implementation class ServerletProduct
 */
@WebServlet(urlPatterns = {"/ServerletProduct", "/search/Home/Cadproduct","/search/Home/insert","/search/Home/UpProduct"})
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
				
		System.out.println(action + " doGet");
		
		if (action.equals("/search/Home/Cadproduct")) {
			cadastroProduto(request,response);
		} else if (action.equals("/search/Home/insert")) { // Recebe a requisição enviada pelo forms de inserirProduto
			inserirContato(request,response);
		} else if (action.equals("/search/Home/UpProduct")) { 
			listaDeContatosByUpdate(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void cadastroProduto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		ArrayList<Categoria> categorias = category_dao.selectAllCategorys();
		
		request.setAttribute("categorias", categorias);
		
		RequestDispatcher rd = request.getRequestDispatcher("/search/CadProduct/CadProduct.jsp");
		
		rd.forward(request, response);		
	}
	
	private void inserirContato(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//Prepara o Objeto para Inserção
		produto.setNome(request.getParameter("nome"));		
		produto.setVp((Double.parseDouble(request.getParameter("vp"))));		
		produto.setCodigo(request.getParameter("codigo"));				
		produto.setCategoria(category_dao.selectCategoryById(Integer.parseInt(request.getParameter("category"))));	
		produto.setDataCadastro(null);
				
		//Passa o objeto e as requisição para o método de inserir
		product_dao.insertProduct(produto, request, response);
		
	}
	
	private void listaDeContatosByUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    ArrayList<Produto> produtos = product_dao.selectAllProductJoinCategory();
	    
	    request.setAttribute("produtos", produtos);
	    
	    RequestDispatcher rd = request.getRequestDispatcher("/search/UpProduct/Products.jsp");
	    rd.forward(request, response);    
	}
}
