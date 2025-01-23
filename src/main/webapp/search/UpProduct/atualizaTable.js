function atualizarTabela(produtos) {
    // Seleciona a tbody onde os dados serão atualizados
    const tbody = document.querySelector("table tbody");
    
    // Limpa a tabela atual
    tbody.innerHTML = "";

    // Itera sobre os produtos e cria as novas linhas
    produtos.forEach(produto => {
        const tr = document.createElement("tr");

        // Cria as células para cada produto
        tr.innerHTML = `
            <td>${produto.idProduto}</td>
            <td>${produto.nome}</td>
            <td>${produto.vp}</td>
            <td>${produto.categoria.descricao}</td>
            <td>${produto.codigo}</td>
            <td>${produto.dataCadastro}</td>
            <td><a href="UpProduct?idProduct=${produto.idProduto}" class="Botao1">Editar</a></td>
        `;

        // Adiciona a nova linha à tabela
        tbody.appendChild(tr);
    });
}
