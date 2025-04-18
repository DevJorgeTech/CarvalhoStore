package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOS.PRODUCT_PEDIDO_DAO;
import Model.Pedido;
import Model.Produto;
import Model.ProdutoPedido;

/**
 * Servlet implementation class ServerLetPedidoProduct
 */
@WebServlet(urlPatterns = { "/ServerLetPedidoProduct", "/search/Home/insertProductPedido" })
@MultipartConfig
public class ServerLetPedidoProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
				
	List<ProdutoPedido> listProdutosPedidos = new ArrayList<>();
	
	PRODUCT_PEDIDO_DAO product_pedido = new PRODUCT_PEDIDO_DAO();
	
	public ServerLetPedidoProduct() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		System.out.println(action + " doPost ServerLetPedidoProduct");
		
		if (action.equals("/search/Home/insertProductPedido")) {
			insertProductPedido(request, response);
		}
	}
	
	private void insertProductPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		listProdutosPedidos = (List<ProdutoPedido>) request.getAttribute("produtosPedidos");
					
		String resposta = product_pedido.insertProductsPedidos(listProdutosPedidos);
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(resposta);
		
	}
}
