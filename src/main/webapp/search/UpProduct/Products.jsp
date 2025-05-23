<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.Produto"%>
<%
	ArrayList<Produto> produtos = (ArrayList<Produto>) request.getAttribute("produtos");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarvalhoStore</title>
<link rel="stylesheet" href="/CarvalhoStore/search/UpProduct/ProductsStyle.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
	<div id="conteiner-products">
		<h1>Tabela de Produtos</h1>

		<div id="conteiner-search">
			<form action="searchProduct" method="get" id="formSearch">
				<input type="search" id="search" name="search"
					value="${param.search}">
				<!-- Estou capturando o valor de um parametro da url -->
				<button type="button" id="button-search-cancel">
					<i class="bi bi-x"></i>
				</button>
			</form>
		</div>

		<div class="tabela-container">
			<table>
				<thead>
					<tr>
						<th>ID Produto</th>
						<th>Nome</th>
						<th>Preço_Venda</th>
						<th>Categoria</th>
						<th>Código</th>
						<th>Data de Cadastro</th>
						<th>Ação</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (int i = 0; i < produtos.size(); i++) {
					%>
					<tr>
						<td><%=produtos.get(i).getIdProduto()%></td>
						<td><%=produtos.get(i).getNome()%></td>
						<td><%=produtos.get(i).getPreco_Venda()%></td>
						<td><%=produtos.get(i).getCategoria().getDescricao()%></td>
						<td><%=produtos.get(i).getCodigo()%></td>
						<td><%=produtos.get(i).getFormattedDate()%></td>
						<td><a
							href="UpProduct?idProduct=<%=produtos.get(i).getIdProduto()%>"
							class="Botao1">Editar</a></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>

	</div>

	<script src="/CarvalhoStore/search/UpProduct/validacaoProducts.js"></script>
	<script src="/CarvalhoStore/search/UpProduct/atualizaTable.js"></script>
</body>
</html>