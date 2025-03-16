const inputSearch = document.getElementById("search");
const formSearch = document.getElementById("formSearch");
const button_search_cancel = document.getElementById("button-search-cancel");

let scriptTag = document.currentScript;

scriptTag.addEventListener("load", function() {
	inputSearch.focus();

	// Coloca o cursor no final do texto
	const textLength = inputSearch.value.length;
	inputSearch.setSelectionRange(textLength, textLength);
});

let likeValue = '';
let typingTimer;
const doneTypingInterval = 500;

inputSearch.addEventListener("input", function(event) {
	clearTimeout(typingTimer);

	likeValue = inputSearch.value;

	if (likeValue === "") {
		formSearch.style.border = "3px solid red";
		inputSearch.value = '';
		enviaSearch(event);
	} else {
		typingTimer = setTimeout(function() {
			enviaSearch(event);
		}, doneTypingInterval);
	}
});

inputSearch.addEventListener("focus", function() {
	formSearch.style.border = "3px solid green";
});

inputSearch.addEventListener("blur", function() {
	formSearch.style.border = "1px solid black";
	inputSearch.style.borderColor = "transparent";
});

button_search_cancel.addEventListener("click", function(event) {
	inputSearch.value = '';
	enviaSearch(event);
}); // Faz apenas um envio vazio, logo retorna todos os produtos

function enviaSearch(event) {
	event.preventDefault();

	// Pega os dados do formulário
	const formData = new FormData(formSearch);
	const params = new URLSearchParams(formData);

	// Aqui você constrói a URL com os parâmetros de pesquisa
	const url = `${formSearch.action}?${params.toString()}`;
	
	fetch(url, {
		method: formSearch.getAttribute('method'),
	}).then(response => response.json())
		.then(data => {
			console.log(data.produtos)
			atualizarTabela(data.produtos);
		})
		.catch(error => console.error('Erro ao enviar os dados!', error));
}
