document.getElementById('frmCadProduct').addEventListener('submit', event => {
	event.preventDefault();

	parent.postMessage({
		action: 'submitFormsAndToast',
		formId: 'frmCadProduct',
		dadosForm: retornaDadosForm(),
		destinationUrl: '/CarvalhoStore/search/Home/CadPedido'
	}, '*');
});

function retornaDadosForm() {
	var tbody = document.querySelector('#tb_produtos tbody');
	var produtos = {};

	Array.from(tbody.rows).forEach(function(row, index) {
		var produto = {};

		produto.nome = row.querySelector('[name="produto' + (index + 1) + '_nome"]').value;
		produto.preco_venda = row.querySelector('[name="produto' + (index + 1) + '_preco_venda"]').value;
		produto.categoria = row.querySelector('[name="produto' + (index + 1) + '_category"]').value;
		produto.codigo = row.querySelector('[name="produto' + (index + 1) + '_codigo"]').value;
		produto.qnt_cx = row.querySelector('[name="produto' + (index + 1) + '_qnt_cx"]').value;
		produto.valor_cx = row.querySelector('[name="produto' + (index + 1) + '_valor_cx"]').value;
		produto.recompra = row.querySelector('[name="produto' + (index + 1) + '_recompra"]').checked;

		produtos['Produto' + (index + 1)] = produto;
	});

	var jsonData = JSON.stringify({ Produtos: produtos });

	return jsonData;
}

document.getElementById("frmCadProduct").addEventListener('reset', (event) => {

	event.preventDefault();

	event.target.querySelectorAll('input, select').forEach(field => {
		field.style.border = '';
		field.setCustomValidity('');
	});

	event.target.querySelectorAll('input[type="text"]:not(#codigo_pedido):not(#recompra):not(#valor_pedido), select').forEach(field => {
		if (field instanceof HTMLSelectElement) {
			field.value = "0";
		} else {
			field.value = '';
		}
	});

	let dataList = document.querySelectorAll("datalist");

	while (dataList.firstChild) {
		dataList.removeChild(dataList.firstChild);
	}

});

let typingTimer;
const doneTypingInterval = 1000;

function enviaDadosSearch(inputNome, dataList, idItensRecompra) {

	clearTimeout(typingTimer); // evita múltiplas requisições

	typingTimer = setTimeout(function() {
		parent.postMessage({
			action: 'enviaSearchProduct',
			search: inputNome.value,
			idDataList: dataList.id,
			idItensRecompra: idItensRecompra
		}, '*');
	}, doneTypingInterval);

	inputNome.addEventListener("change", function() {
		clearTimeout(typingTimer);
	});
}

let inputHandler;

function recompraChecked(idItensRecompra) {

	var elementos = document.querySelectorAll(
		'[id^="inpt_"][id$="' + idItensRecompra + '"]' +
		':not([id*="inpt_nome_' + idItensRecompra + '"])' +
		':not([id*="inpt_codigo_' + idItensRecompra + '"])' +
		':not([id*="inpt_qnt_cx_' + idItensRecompra + '"])' +
		':not([id*="inpt_valor_cx_' + idItensRecompra + '"])'
	);

	if (elementos.length === 0) {
		console.error("Erro ao buscar elementos de recompra!");
	}

	elementos.forEach(function(elemento) {
		if (elemento.id === "inpt_category_" + idItensRecompra) {
			elemento.value = "0";
			elemento.disabled = true;
		} else {
			elemento.readOnly = true;
			elemento.value = '';
		}
	});

	let nome = document.getElementById("inpt_nome_" + idItensRecompra);

	let dataListName = document.createElement("datalist");
	dataListName.id = 'dataListName_' + idItensRecompra;

	nome.setAttribute('list', dataListName.id);
	nome.setAttribute('autocomplete', 'off');
	nome.appendChild(dataListName);

	inputHandler = function() {
		enviaDadosSearch(nome, dataListName, idItensRecompra);
	};

	nome.addEventListener("input", inputHandler);
};

function recompraNotChecked(idItensRecompra) {

	let nome = document.getElementById("inpt_nome_" + idItensRecompra);

	nome.removeEventListener("input", inputHandler);

	let elementos = document.querySelectorAll('[id*="_' + idItensRecompra + '"]');
	let dataList = document.getElementById("dataListName_" + idItensRecompra);

	dataList.remove();

	elementos.forEach(field => {
		field.readOnly = false;
		field.disabled = false;
	});

	parent.postMessage({
		action: 'removerEvent',
		evento: 'change',
		function: 'preencheCampos',
		idItensRecompra: idItensRecompra
	}, '*');
}

function adicionarLinha() {
	var section_produto = document.querySelector('#section-produto');
	var tbody = document.querySelector('#tb_produtos tbody');
	var produtoNum = tbody.children.length + 1;

	var tr = document.createElement('tr');
	tr.setAttribute('id', 'tr_' + produtoNum);

	var headers = document.querySelectorAll('#tb_produtos thead th');
	var tds = [];

	for (var i = 0; i < 8; i++) {
		var td = document.createElement('td');
		var input, btn;

		switch (headers[i].textContent.trim()) {
			case "Nome":
				input = document.createElement('input');
				input.setAttribute('type', 'text');
				input.id = 'inpt_nome_' + produtoNum;
				input.name = 'produto' + produtoNum + '_nome';
				input.required = true;
				input.minlength = 1;

				td.appendChild(input);
				break;

			case "Preço_Venda":
				input = document.createElement('input');
				input.setAttribute('type', 'text');
				input.id = 'inpt_Preco_Venda_' + produtoNum;
				input.name = 'produto' + produtoNum + '_preco_venda';
				input.required = true;
				input.minlength = 1;
				parent.postMessage({
					action: 'formatarNumeroDouble',
					elementId: input.id,
				}, '*');
				input.setAttribute('pattern', '^[0-9]+(\.[0-9]+)?$');
				td.appendChild(input);
				break;

			case "Categoria":
				input = document.createElement('select');
				input.id = 'inpt_category_' + produtoNum;
				input.name = 'produto' + produtoNum + '_category';
				input.required = true;

				var defaultOption = document.createElement('option');
				defaultOption.value = '0';
				defaultOption.textContent = 'Selecione uma categoria';
				defaultOption.disabled = true;
				defaultOption.selected = true;
				input.appendChild(defaultOption);

				categorias.forEach(function(categoria) {
					var option = document.createElement('option');
					option.value = categoria.idCategory;
					option.textContent = categoria.descricao;
					input.appendChild(option);
				});
				td.appendChild(input);
				break;

			case "Código":
				input = document.createElement('input');
				input.setAttribute('type', 'text');
				input.id = 'inpt_codigo_' + produtoNum;
				input.name = 'produto' + produtoNum + '_codigo';
				input.required = true;
				input.minlength = 1;
				td.appendChild(input);
				break;

			case "Produtos na Caixa":
				input = document.createElement('input');
				input.setAttribute('type', 'text');
				input.id = 'inpt_qnt_cx_' + produtoNum;
				input.name = 'produto' + produtoNum + '_qnt_cx';
				input.setAttribute('pattern', '^[0-9]+$');
				input.required = true;
				td.appendChild(input);
				break;

			case "Valor da Caixa":
				input = document.createElement('input');
				input.setAttribute('type', 'text');
				input.id = 'inpt_valor_cx_' + produtoNum;
				input.name = 'produto' + produtoNum + '_valor_cx';
				input.required = true;
				input.minlength = 1;
				parent.postMessage({
					action: 'formatarNumeroDouble',
					elementId: input.id,
				}, '*');
				input.setAttribute('pattern', '^[0-9]+(\.[0-9]+)?$');
				td.appendChild(input);
				break;

			case "Recompra?":
				input = document.createElement('input');
				input.setAttribute('type', 'checkbox');
				input.id = 'inpt_recompra_' + produtoNum;
				input.name = 'produto' + produtoNum + '_recompra';
				input.addEventListener('change', function() {
					if (input.checked) {
						recompraChecked(produtoNum);
					} else {
						recompraNotChecked(produtoNum);
					}
				});
				td.appendChild(input);
				break;

			case "Excluir":
				btn = document.createElement('button');
				btn.textContent = 'Excluir';
				btn.classList.add('Botao2');
				btn.setAttribute('type', 'button');
				btn.id = 'excluir_' + produtoNum;
				btn.addEventListener('click', function() {
					let resposta = confirm("Você deseja excluír essa linha?");
					if (resposta) {
						excluiLinha(this);
					}
				});
				td.appendChild(btn);
				break;

			default:
				input = document.createElement('input');
		}

		tds.push(td);
	}

	tds.forEach(function(td) {
		tr.appendChild(td);
	});

	if (tbody.querySelectorAll('td').length === 24) {
		section_produto.style.maxHeight = '262px';
		section_produto.style.overflowY = 'auto';
	}

	tbody.appendChild(tr);
	liberaBtnsAcoes();
}

function excluiLinha(button) {

	var trMaisProximo = button.closest('tr');

	var trExcluido = trMaisProximo.id.match(/^tr_(\d+)$/);

	if (!trExcluido) {
		console.error("ID do elemento não corresponde ao padrão esperado.");
		return;
	}

	var trExcluidoId = parseInt(trExcluido[1]);

	trMaisProximo.remove();

	var arrElementos = document.querySelectorAll('[id^="tr_"]');

	var ids = [];

	arrElementos.forEach(function(elemento) {
		var match = elemento.id.match(/^tr_(\d+)$/);
		if (match) {
			ids.push(match[1]);
		} else {
			console.error(`Erro ao localizar match do tr_${elemento.id}`);
		}
	});

	var idsNv = [];

	ids.forEach(function(e) {
		if (e < trExcluidoId) {
			idsNv.push(e);
		} else {
			idsNv.push(e - 1);
		}
	});

	arrElementos.forEach((e, index) => {
		e.id = 'tr_' + idsNv[index];
		corrigeInputs(idsNv[index], e.children);
	});

	liberaBtnsAcoes();
}

function corrigeInputs(idPai, filhos) {
	const mapeamentoIdsInput = {
		0: 'inpt_nome_',
		1: 'inpt_Preco_Venda_',
		2: 'inpt_category_',
		3: 'inpt_codigo_',
		4: 'inpt_qnt_cx_',
		5: 'inpt_valor_cx_',
		6: 'inpt_recompra_',
		7: 'excluir_'
	};

	const mapeamentoNameInput = {
		0: '_nome',
		1: '_preco_venda',
		2: '_category',
		3: '_codigo',
		4: '_qnt_cx',
		5: '_valor_cx',
		6: '_recompra',
		7: ''
	};

	const mapeamentoCombinado = {};

	Object.keys(mapeamentoIdsInput).forEach(key => {
		mapeamentoCombinado[key] = {
			id: mapeamentoIdsInput[key],
			name: mapeamentoNameInput[key]
		};
	});

	Array.from(filhos).forEach((td, index) => {
		const inputs = td.children;

		Array.from(inputs).forEach((input, indexInput) => {

			let idInput = mapeamentoCombinado[index].id;
			let nameInput = mapeamentoCombinado[index].name;

			if (index === 7) {
				input.id = `${idInput}${idPai}`;
				return;
			}

			if (idInput && nameInput) {
				input.id = `${idInput}${idPai}`;
				input.name = `produto${idPai}${nameInput}`
			} else {
				console.error(`subIndex inválido: ${index}. Deve ser de 0 a 7.`);
			}
		});
	});
}

function liberaBtnsAcoes() {
	const tb_produtos = document.getElementById('tb_produtos');
	const tds = tb_produtos.querySelectorAll('td');

	let btnSalvar = document.getElementById('btnSalvar');
	let btnCancelar = document.getElementById('btnCancelar');

	if (tds.length > 0) {
		btnSalvar.disabled = false;
		btnCancelar.disabled = false;
	} else {
		btnSalvar.disabled = true;
		btnCancelar.disabled = true;
	}
}