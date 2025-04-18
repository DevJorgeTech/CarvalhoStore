<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.Categoria"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.google.gson.Gson"%>
<%
ArrayList<Categoria> categorias = (ArrayList<Categoria>) request.getAttribute("categorias");

String categoriasJson = new Gson().toJson(categorias);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="/CarvalhoStore/search/CadProduct/Style.css?v=1.0.1">
<title>CarvalhoStore</title>
</head>
<body>
	<div id="form-container">
		<form name="frmCadProduct" action="insertProduto" method="POST"
			id="frmCadProduct">

			<div id="titule">
				<h1>Adicionar Produto</h1>
			</div>

			<div id="main-container">

				<div id="section-pedido">
					<div class="pair">
						<label for="codigo_pedido">Código do Pedido: </label> <input
							type="text" id="codigo_pedido" name="codigo_pedido"
							class="Caixa2" readonly value="${param.codigo}">
					</div>
					<div class="pair">
						<label for="valor_pedido">Valor do Pedido: </label> <input
							type="text" id="valor_pedido" class="Caixa2" readonly
							value="${param.valorT}">
					</div>
				</div>


				<div id="section-produto">
					<table id="tb_produtos">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Preço_Venda</th>
								<th>Categoria</th>
								<th>Código</th>
								<th>Produtos na Caixa</th>
								<th>Valor da Caixa</th>
								<th>Recompra?</th>
								<th>Excluir</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>

			<div id="button-container">
				<input type="button" value="Adicionar Produto" class="Botao1"
					id="btn_adicionar" onclick="adicionarLinha()"> 
				<input type="submit" value="Salvar" class="Botao1" id="btnSalvar" disabled>
				<input type="reset" value="Cancelar" class="Botao1" id="btnCancelar" disabled>
			</div>
		</form>
		<!-- Fim do Forms -->
	</div>

	<script src="/CarvalhoStore/search/CadProduct/CadProduct.js"></script>
	<script>
        var categorias = <%=categoriasJson%>;
    </script>
</body>
</html>