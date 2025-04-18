function atualizarTabela(produtos) {
	// Seleciona a tbody onde os dados serão atualizados
	const tbody = document.querySelector("table tbody");

	// Limpa a tabela atual
	tbody.innerHTML = "";

	// Itera sobre os produtos e cria as novas linhas
	produtos.forEach(produto => {
		const tr = document.createElement("tr");
		
		console.log(produto.preco_Venda);

		// Cria as células para cada produto
		tr.innerHTML = `
            <td>${produto.idProduto}</td>
            <td>${produto.nome}</td>
            <td>${produto.preco_Venda}</td>
            <td>${produto.categoria.descricao}</td>
            <td>${produto.codigo}</td>
            <td>${trataDate(produto.dataCadastro)}</td>
            <td><a href="UpProduct?idProduct=${produto.idProduto}" class="Botao1">Editar</a></td>
        `;

		// Adiciona a nova linha à tabela
		tbody.appendChild(tr);
	});
}

function trataDate(data) {
	const meses = {
		'jan.': '01',
		'fev.': '02',
		'mar.': '03',
		'abr.': '04',
		'mai.': '05',
		'jun.': '06',
		'jul.': '07',
		'ago.': '08',
		'set.': '09',
		'out.': '10',
		'nov.': '11',
		'dez.': '12'
	};

	const partes = data.replace(',', '').split(' '); 

	const dia = partes[1].padStart(2, '0');
	const mes = meses[partes[0].toLowerCase()];
	const ano = partes[2];

	return `${dia}/${mes}/${ano}`;
}
