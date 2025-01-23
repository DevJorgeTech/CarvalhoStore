//Aplica a função para os Links internos do conteiner_main

document.getElementById('conteiner_main')
	.querySelectorAll("a").forEach(function(link) {

		link.addEventListener('click', function(event) {

			event.preventDefault();

			sessionStorage.clear();
			resetScripts();

			let url = this.href;

			fetch(url, {
				method: 'GET',
			})
				.then(response => {
					if (!response.ok) {
						throw new Error('Erro ao carregar a página');
					}
					return response.text();
				})
				.then(html => {
					// Insere o HTML na div "conteiner_main"
					document.getElementById('conteiner_main').innerHTML = html;

					let conteinerHtml = document.getElementById('conteiner_main').innerHTML;

					// Armazena o conteúdo da div no sessionStorage
					sessionStorage.setItem('lastConteinerHtml', conteinerHtml);

					loadScripts();

				})
				.catch(error => {
					console.error('Erro:', error);
					document.getElementById('conteiner_main').innerHTML = `<p>Erro ao carregar conteúdo: ${error.message}</p>`;
				});
		});
	});