document.querySelectorAll("a").forEach(function(link) {
	link.addEventListener('click', function(event) {
		
		let url = this.href;
						
		resetScripts();
				
		loadPageInConteinerMain(event, url);
	});
});

function loadPageInConteinerMain(event,url) {
		
	event.preventDefault();
	
	sessionStorage.clear();
	
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
}

function loadScripts() {
    let scripts = document.querySelectorAll('#conteiner_main script');

    scripts.forEach(script => {
        let newScript = document.createElement('script');

        if (script.src) {
            newScript.src = script.src;
            newScript.dataset.fromConteinerMain = true; // Marca o script como parte do conteiner
        } else {
            newScript.textContent = script.innerHTML;
            newScript.dataset.fromConteinerMain = true; // Marca o script inline
        }

        // Remove qualquer script existente duplicado
        let existingScript = document.querySelector(`script[src="${newScript.src}"]`);
        if (existingScript) existingScript.remove();

        // Adiciona o novo script ao DOM
        document.body.appendChild(newScript);
        console.log('Novo script adicionado:', newScript.src || 'inline script');
    });
}



function resetScripts() {
    // Remove todos os scripts na div conteiner_main
    document.querySelectorAll('#conteiner_main script').forEach(script => {
        console.log('Removendo script da div:', script.src || 'inline script');
        script.remove();
    });

    // Remove scripts externos já adicionados ao body
    document.querySelectorAll('script').forEach(script => {
        // Verifica se o script pertence ao conteúdo atual
        if (script.dataset.fromConteinerMain) {
            console.log('Removendo script do body:', script.src || 'inline script');
            script.remove();
        }
    });
}