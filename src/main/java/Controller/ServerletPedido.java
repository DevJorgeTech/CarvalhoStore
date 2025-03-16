package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import DAOS.PEDIDO_DAO;
import Model.Fornecedor;
import Model.Pedido;

/**
 * Servlet implementation class ServerletPedido
 */
@WebServlet(urlPatterns = {"/ServerletPedido","/search/Home/CadPedido","/search/Home/salvarPedido","/search/Home/insertPedido"})
@MultipartConfig // Necessária para capturar requests com parametros no body
public class ServerletPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PEDIDO_DAO pedido_dao = new PEDIDO_DAO();
	
	Pedido pedido = new Pedido();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerletPedido() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		System.out.println(action + " doGet ServerLetPedido");
		
		if (action.equals("/search/Home/CadPedido")) {
			cadastroPedido(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();	
        		
		System.out.println(action + " doPost ServerLetPedido");
		
		if (action.equals("/search/Home/salvarPedido")) {
			salvarPedido(request, response);
		} else if (action.equals("/search/Home/insertPedido")) {
			insertPedido(request, response);
		}
	}

	private void cadastroPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Fornecedor> fornecedores = pedido_dao.selectAllFornecedores();

		request.setAttribute("fornecedores", fornecedores);

		RequestDispatcher rd = request.getRequestDispatcher("/search/CadPedido/CadPedido.jsp");

		rd.forward(request, response);
	}
	
	private Boolean validaPedido(HttpServletRequest request, HttpServletResponse response) {
		pedido.setCodigoPedido(request.getParameter("codigo"));	
		
		Boolean existPedido = pedido_dao.selectPedidoByCodigoIfExist(pedido.getCodigoPedido());
		
		return existPedido;
	}
	
	private void salvarPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Boolean pedidoExist = validaPedido(request, response);
				
		if (!pedidoExist) {
			
			pedido.setIdFornecedor(Integer.parseInt(request.getParameter("fornecedor")));
			pedido.setVtotalPedido(Double.parseDouble(request.getParameter("valorT")));
			
	        HttpSession session = request.getSession();
	        
	        session.setAttribute("codigoPedido", pedido.getCodigoPedido());
	        session.setAttribute("valorTotalPedido", pedido.getVtotalPedido());

			System.out.println("DADOS PEDIDO:");
			System.out.println(pedido.getCodigoPedido());
			System.out.println(pedido.getIdFornecedor());
			System.out.println(pedido.getVtotalPedido());
			
			Gson gson = new Gson();
			
			Map<String, Object> dados = new HashMap<>();
			
			dados.put("type", "Sucesso");
			dados.put("status", "Dados salvos com Sucesso");
			
			String resposta = gson.toJson(dados);
					
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    
		    response.getWriter().write(resposta);
		} else {
			String resposta = pedido_dao.enviaNotificaoErro(null,null,"Erro ao inserir produto " 
																	+ pedido.getCodigoPedido() + " já exite!",3); 		
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    
		    response.getWriter().write(resposta);
		}	    
	}
		
	private void insertPedido(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int status = pedido_dao.insertPedido(pedido);
		
		if (status >= 1) {
			enviaRequestProductPedido(request, response);
		} else {
			pedido_dao.enviaNotificaoErro(null, null, "Erro ao Inserir Produto!", 3);
		}
	}
	
	private void enviaRequestProductPedido(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher("/search/Home/insertProductPedido");

		rd.forward(request, response);	    
	}
	
}
