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
<link rel="icon" type="image/png"
	href="/CarvalhoStore/Imagens/icon_carvalho.png">
<link rel="stylesheet" href="/CarvalhoStore/search/UpProduct/UpdateStyle.css">
</head>
<body>
	<div id="conteiner-products">
		<table>
			<caption><b>Tabela de Produtos</b></caption>
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
						<td><input type="button" value="Editar" class="Botao1"></td>
					</tr>
				<%} %>
			</tbody>
		</table>
	</div>
</body>
</html>