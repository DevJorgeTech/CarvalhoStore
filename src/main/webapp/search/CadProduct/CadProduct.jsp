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
<link rel="stylesheet" href="/CarvalhoStore/search/CadProduct/Style.css?v=1.0.1">
<title>CarvalhoStore</title>
</head>
<body>
	<div id="form-container">
		<form name="frmCadProduct" action="insert" method="POST"
			id="frmCadProduct">
			<h1>Adicionar Produto</h1>
			<table id="tabela">
				<tr>
					<td><input type="text" name="nome" required minlength="1"
						placeholder="Nome do Produto" class="Caixa1"></td>
				</tr>
				<tr>
					<td><input type="text" name="vp" id="vp" required
						minlength="1" pattern="[\d.,]*" placeholder="Valor Por Unidade"
						class="Caixa2"></td>
				</tr>
				<tr>
					<td><input type="text" name="codigo" required minlength="1"
						placeholder="Código do Produto" class="Caixa1"></td>
				</tr>
				<tr>
					<td><select id="category" name="category" required>
							<option value="0" disabled selected>Selecione uma
								categoria</option>
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
				<input type="submit" value="Adicionar" class="Botao1">
				<!-- Envia os dados do Forms para o ServerLet -->
				<input type="reset" value="Cancelar" class="Botao1">
			</div>
		</form>
	</div>

	<script src="/CarvalhoStore/search/CadProduct/validacaoCadProduct.js"></script>

</body>
</html>