<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.Produto"%>
<%
    ArrayList<Produto> produtos = (ArrayList<Produto>) request.getAttribute("produtos");
%>
<%
	//Captura os dados da sessão enviado pela PRODUCT_DAO
	String status = (String) session.getAttribute("status");
	String type = (String) session.getAttribute("type");
	
	// Limpa os dados da sessão, com isso a URL se mantém limpa e o Toast, pode ser ativado
	session.removeAttribute("status");
    session.removeAttribute("type");
    
%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarvalhoStore</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="icon" type="image/png" href="/CarvalhoStore/Imagens/icon_carvalho.png">
<link rel="stylesheet" href="/CarvalhoStore/search/UpProduct/ProductsStyle.css">
</head>
<body>
	<div id="conteiner-products">
		<h1>Tabela de Produtos</h1>
		
		<div id="conteiner-search">
			<form action="search" id="formSearch">
				<input type="search" id="search" name="search" 
				value="${param.search}"> <!-- Estou capturando o valor de um parametro da url -->
				<button type="button" id="button-search-cancel"><i class="bi bi-x"></i></button>
			</form>
		</div>
		
		<table>			
			<thead>
				<tr>
					<th>ID Produto</th>
					<th>Nome</th>
					<th>Valor por Unidade (VP)</th>
					<th>Categoria</th>
					<th>Código</th>
					<th>Data de Cadastro</th>
					<th>Ação</th>
				</tr>
			</thead>
			<tbody>
				<%for (int i = 0; i < produtos.size(); i++) {%>
					<tr>
						<td><%=produtos.get(i).getIdProduto()%></td>
						<td><%=produtos.get(i).getNome()%></td>
						<td><%=produtos.get(i).getVp()%></td>
						<td><%=produtos.get(i).getCategoria().getDescricao()%></td>
						<td><%=produtos.get(i).getCodigo()%></td>
						<td><%=produtos.get(i).getDataCadastro()%></td>
						<td><a href="UpProduct?idProduct=<%=produtos.get(i).getIdProduto()%>" class="Botao1">Editar</a></td>
					</tr>
				<%} %>
			</tbody>
		</table>
	</div>
	
		<div id="wrapper_toast">
		<div class="toast <%=type%>">
			<div class="container-1">
				<i class="fas fa-check-circle"></i>
			</div>
			<div class="container-2">
				<p><%=type %></p>
				<p><%=status %></p>
			</div>
		</div>
	</div> 
	
	<script src="/CarvalhoStore/search/UpProduct/validacaoProducts.js"></script>	
	
	<script type="text/javascript">
        var status = "<%= status != null ? status : "" %>";
        if (status) {
            mostrarNotificação();
        }
    </script>
</body>
</html>