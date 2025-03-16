function formatarNumero(inputId) {
	let value = document.getElementById(inputId).value.replace(/,/g, ".");
	let isFirstPoint = false;
	let isSecondPoint = false;
	let novaStr = "";

	for (let i = 0; i < value.length; i++) {
		if (value[i] === ".") {
			if (!isFirstPoint) {
				isFirstPoint = true;
				novaStr += value[i];
			} else if (!isSecondPoint) {
				isSecondPoint = true;
			}
		} else {
			novaStr += value[i];
		}
	}

	document.getElementById(inputId).value = novaStr;
};

function enviaSearchProduct(idInputSearch, idDataList, idItensRecompra) { // Função Utilizada Para Inputs de Pesquisa de Produto via Like
	const inputSearch = document.getElementById(idInputSearch).value;

	const url = `/CarvalhoStore/search/Home/searchProduct?search=${encodeURIComponent(inputSearch)}`;

	fetch(url, {
		method: "GET",
	}).then(response => response.json())
		.then(data => {
			populaDataList(idDataList, data.produtos, idItensRecompra);
		})
		.catch(error => console.error('Erro ao enviar os dados!', error));
};

function populaDataList(idDataList, produtos, idItensRecompra) {
	let dataList = document.getElementById(idDataList);

	while (dataList.firstChild) {
		dataList.removeChild(dataList.firstChild);
	}

	produtos.forEach(produto => {
		let option = document.createElement("option");
		option.value = produto.nome;
		dataList.appendChild(option);
	});

	let inputNome = document.getElementById("inpt_nome_" + idItensRecompra);

	inputNome.addEventListener("change", function() {

		let produtoSelecionado = produtos.find(produto => produto.nome === inputNome.value);

		if (produtoSelecionado) {
			document.getElementById("inpt_Preco_Venda_" + idItensRecompra).value = produtoSelecionado.preco_Venda;
			document.getElementById("inpt_codigo_" + idItensRecompra).value = produtoSelecionado.codigo;
			document.getElementById("inpt_codigo_" + idItensRecompra).readOnly = true;
			document.getElementById("inpt_category_" + idItensRecompra).value = produtoSelecionado.categoria.idCategory;
		}
	});
};


