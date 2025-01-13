<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.Categoria"%>
<%@ page import="java.util.ArrayList"%>
<% 	
	ArrayList<Categoria> categorias = (ArrayList<Categoria>) request.getAttribute("categorias");
%>

<%
	//Captura os dados da sessão enviado pela PRODUCT_DAO
	String status = (String) session.getAttribute("status");
	String type = (String) session.getAttribute("type");
	
	// Limpa os dados da sessão, com isso a URL se mantém limpa e o Toast, pode ser ativado
    session.removeAttribute("status");
    session.removeAttribute("type");
    
    //Limpar os dados da sessão evita que quando a página for recarregada o Toast fique ativando
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="icon" type="image/png" href="/CarvalhoStore/Imagens/icon_carvalho.png">
<link rel="stylesheet" href="/CarvalhoStore/search/CadProduct/Style.css">
<title>CarvalhoStore</title>
</head>
<body>
	<div id="form-container">
		<form name="frmCadProduct" action="insert" id="frmCadProduct">
			<h1>Adicionar Produto</h1>
				<table id="tabela">
					<tr>
						<td><input type="text" name="nome" required minlength="1" placeholder="Nome do Produto"
							class="Caixa1"></td>
					</tr>
					<tr>
						<td><input type="text" name="vp" id="vp" 
							required minlength="1" 
							pattern="[\d.,]*"  
							onchange="formatarNumero(event)" 
							placeholder="Valor Por Unidade"
							class="Caixa2"></td>
					</tr>
					<tr>
						<td><input type="text" name="codigo" required minlength="1" placeholder="Código do Produto"
							class="Caixa1"></td>
					</tr>
					<tr>
						<td>
							<select id="category" name="category" required>
								<option value="0" disabled selected>Selecione uma categoria</option>
								<%for (int i = 0; i < categorias.size(); i++) { %>
									<option value="<%=categorias.get(i).getIdCategory()%>"><%=categorias.get(i).getDescricao()%></option>
								<%} %>
							</select>
						</td>
					</tr>
				</table>

				<div id="button-container">
					<input type="submit" value="Adicionar" class="Botao1"> <!-- Envia os dados do Forms para o ServerLet -->
					<input type="reset" value="Cancelar" class="Botao1" onclick="cancelar()">
				</div>
		</form>
		
		<button class="Botao2" onclick="voltarMenu()">
			<i class="bi bi-arrow-left">Voltar</i>
  		</button>
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
	
	<script src="/CarvalhoStore/search/CadProduct/validacao.js"></script>	
	
	<script type="text/javascript">
        var status = "<%= status != null ? status : "" %>";
        if (status) {
            mostrarNotificação();
        }
    </script>	
</body>
</html>