const app_frame = document.getElementById('app-frame');

function formatarNumeroDouble(inputId) {

	const iframeDocument = app_frame.contentWindow.document;

	let value = iframeDocument.getElementById(inputId).value.replace(/,/g, ".");

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

	iframeDocument.getElementById(inputId).value = novaStr;
};

function enviaSearchProduct(inputSearch, idDataList, idItensRecompra) {

	const url = `/CarvalhoStore/search/Home/searchProduct?search=${encodeURIComponent(inputSearch)}`;

	fetch(url, {
		method: "GET",
	}).then(response => response.json())
		.then(data => {
			populaDataList(idDataList, data.produtos, idItensRecompra);
		})
		.catch(error => console.error('Erro ao enviar os dados!', error));
};

let preencheCampos;

function populaDataList(idDataList, produtos, idItensRecompra) {

	console.log("populaDataList");

	const iframeDocument = app_frame.contentWindow.document;

	const dataList = iframeDocument.getElementById(idDataList);

	while (dataList.firstChild) {
		dataList.removeChild(dataList.firstChild);
	}

	produtos.forEach(produto => {
		let option = document.createElement("option");
		option.value = produto.nome;
		dataList.appendChild(option);
	});

	let inputNome = iframeDocument.getElementById("inpt_nome_" + idItensRecompra);

	preencheCampos = function() {
		handleChangeProduto(produtos, inputNome.value, idItensRecompra, iframeDocument);
	};

	inputNome.addEventListener("change", preencheCampos);
};

function handleChangeProduto(produtos, inputNomeValue, idItensRecompra, iframeDocument) {
	console.log("handleChangeProduto");

	let produtoSelecionado = produtos.find(produto => produto.nome === inputNomeValue);

	if (produtoSelecionado) {
		iframeDocument.getElementById("inpt_Preco_Venda_" + idItensRecompra).value = produtoSelecionado.preco_Venda;
		iframeDocument.getElementById("inpt_codigo_" + idItensRecompra).value = produtoSelecionado.codigo;
		iframeDocument.getElementById("inpt_codigo_" + idItensRecompra).readOnly = true;
		iframeDocument.getElementById("inpt_category_" + idItensRecompra).value = produtoSelecionado.categoria.idCategory;
	}
}

window.addEventListener('message', (event) => {
	console.log("Event message 2")

	const iframeOrigin = new URL(app_frame.src).origin;

	const iframeDocument = app_frame.contentWindow.document;

	if (event.origin === iframeOrigin) {
		if (event.data && event.data.action === 'removerEvent') {
			if (event.data.function === 'preencheCampos') {
				let inputNome = iframeDocument.getElementById("inpt_nome_" + event.data.idItensRecompra);

				inputNome.removeEventListener(event.data.evento, preencheCampos);
			}
		} else if (event.data && event.data.action === 'formatarNumeroDouble') {
			let inputToFormatNumero = iframeDocument.getElementById(event.data.elementId);

			inputToFormatNumero.addEventListener('change', function() {
				formatarNumeroDouble(this.id);
			});
		}
	}
});