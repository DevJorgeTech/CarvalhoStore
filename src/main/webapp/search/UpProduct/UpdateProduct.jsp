<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.Categoria"%>
<%@ page import="java.util.ArrayList"%>
<%
ArrayList<Categoria> categorias = (ArrayList<Categoria>) request.getAttribute("categorias");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarvalhoStore</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="icon" type="image/png"
	href="/CarvalhoStore/Imagens/icon_carvalho.png">
<link rel="stylesheet"
	href="/CarvalhoStore/search/UpProduct/UpdateStyle.css">
</head>
<body>
	<div id="form-container">
		<form name="frmUpdateProduct"
			action="update?idProduct =<%out.print(request.getParameter("idProduct"));%>"
			method="post"
			id="frmUpdateProduct">
			<h1>Alterar Produto</h1>
			<table id="tabela">
				<tr>
					<td><input type="text" name="nome" required minlength="1"
						placeholder="Nome do Produto" class="Caixa1"
						value="${productName}"></td>
				</tr>
				<tr>
					<td><input type="text" name="vp" id="vp" required
						minlength="1" pattern="[\d.,]*" onchange="formatarNumero(event)"
						placeholder="Valor Por Unidade" class="Caixa2"
						value="${productVp}"></td>
				</tr>
				<tr>
					<td><input type="text" name="codigo" required minlength="1"
						placeholder="Código do Produto" class="Caixa1"
						value="${productCodigo}"></td>
				</tr>
				<tr>
					<td><select id="category" name="category" required>
							<%
							Categoria category = (Categoria) request.getAttribute("productCategory");
							%>
							<option
								value="<%=String.valueOf(categorias.get(0).getIdCategory())%>"><%=category.getDescricao()%></option>
							<%
							for (int i = 0; i < categorias.size(); i++) {
							%>
							<option
								value="<%=String.valueOf(categorias.get(i).getIdCategory())%>"><%=categorias.get(i).getDescricao()%></option>
							<%
							}
							%>
					</select></td>
				</tr>
			</table>

			<div id="button-container">
				<input type="submit" value="Alterar" class="Botao1">
				<!-- Envia os dados do Forms para o ServerLet -->
				<input type="reset" value="Cancelar" class="Botao1">
			</div>
		</form>

		<button class="Botao2" onclick="voltarMenu(event)">
			<i class="bi bi-arrow-left">Voltar</i>
		</button>
	</div>

	<script src="/CarvalhoStore/search/UpProduct/validacaoUpdate.js"></script>

	<script src="/CarvalhoStore/search/Script_Main/links_internos.js"></script>

</body>
</html>