<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.Fornecedor"%>
<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<Fornecedor> fornecedores = (ArrayList<Fornecedor>) request.getAttribute("fornecedores");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarvalhoStore</title>
<link rel="stylesheet"
	href="/CarvalhoStore/search/CadPedido/CadPedido.css?v=1.0.1">
</head>
<body>
	<div id="form-container">
		<form name="frmCadPedido" action="salvarPedido" method="POST"
			id="frmCadPedido">
			<h1>Adicionar Pedido</h1>
			<table id="tabela">
				<tr>
					<td><input type="text" id="codigo" name="codigo" 
						placeholder="Código do Pedido" class="Caixa1" ></td>
				</tr>
				<tr>
					<td><select id="fornecedor" name="fornecedor" required>
							<option value="0" disabled selected>Selecione um fornecedor</option>
								<%
							for (int i = 0; i < fornecedores.size(); i++) {
							%>
							<option
								value="<%=String.valueOf(fornecedores.get(i).getIdFornecedor())%>"><%=fornecedores.get(i).getNome()%></option>
							<%
							}
							%>
					</select></td>
				</tr>
				<tr>
					<td><input type="text" id="valorT" name="valorT" required minlength="1"
						placeholder="Valor Total do Pedido" class="Caixa1" pattern="[\d.,]*"></td>
				</tr>
			</table>

			<div id="button-container">
				<input type="submit" value="Adicionar" class="Botao1">
				<!-- Envia os dados do Forms para o ServerLet -->
				<input type="reset" value="Cancelar" class="Botao1">
			</div>
		</form>
	</div>
	
	<script src="/CarvalhoStore/search/CadPedido/CadPedido.js"></script>
</body>
</html>