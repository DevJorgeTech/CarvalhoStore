<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CarvalhoStore</title>
<link rel="icon" type="image/png"
	href="/CarvalhoStore/Imagens/icon_carvalho.png">
<link rel="stylesheet" href="style.css">
<!--Biblioteca de icones-->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
</head>
<body>
	<nav id="menu_lateral">
		<div id="btn-expandir">
			<!--BotÃ£o que abrirÃ¡ o menu  -->
			<i class="bi bi-list"></i>
			<!-- Icone escolhido no site: https://icons.getbootstrap.com -->
		</div>

		<ul>
			<!-- Itens do menu -->
			<li class="item_menu"><a href="Cadproduct"> <span
					class="icone"> <i class="bi bi-pencil-square"></i>
				</span> <span class="text-link" translate="no" style="display: none;">Cadastrar
						Produtos</span>
			</a></li>
			<li class="item_menu"><a href="ListProduct"> <span
					class="icone"> <i class="bi bi-arrow-repeat"></i>
				</span> <span class="text-link" style="display: none;">Alterar
						Produtos</span>
			</a></li>
			<li class="item_menu"><a href=""> <span class="icone">
						<i class="bi bi-box-seam-fill"></i>
				</span> <span class="text-link" style="display: none;">Estoque</span>
			</a></li>
			<li class="item_menu"><a href=""> <span class="icone">
						<i class="bi bi-bar-chart-steps"></i>
				</span> <span class="text-link" style="display: none;">Relatórios</span>
			</a></li>
			<li class="item_menu"><a href=""> <span class="icone">
						<i class="bi bi-person-rolodex"></i>
				</span> <span class="text-link" style="display: none;">Conta</span>
			</a></li>
		</ul>
	</nav>

	<div id="conteiner_main"></div>

	<div id="wrapper_toast">
		<div class="toast">
			<div class="container-1">
				<i class="fas fa-check-circle"></i>
			</div>
			<div class="container-2">
				<p id="type"></p>
				<p id="status"></p>
			</div>
		</div>
	</div>

	<script src="script_btnExtender.js"></script>
	<script src="Conteiner_Main.js"></script>
	<script src="Toast.js"></script>
	<script src="/CarvalhoStore/search/Script_Main/submitForms.js"></script>
	<script src="/CarvalhoStore/search/Script_Main/inputs.js"></script>
</body>
</html>