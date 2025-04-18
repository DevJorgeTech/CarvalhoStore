document.querySelectorAll("a").forEach(function(link) {
	link.addEventListener('click', function(event) {

		event.preventDefault();

		console.log('Chama: loadPageInConteinerMain');

		var href = this.getAttribute('href');

		loadPageInConteinerMain(href);

	});
});

function loadPageInConteinerMain(href) {

	console.log(href)
	//Adicionamos o href para o iframe para ser gerado uma request http 
	//(Sempre que o att SRC do iframe é alterado o navegador gera uma nova request)
	document.getElementById('app-frame').src = href;
}

// Adiciona um listener para escutar mensagens do iframe
window.addEventListener('message', (event) => {
	console.log("Evento message 1");

	const app_frame = document.getElementById('app-frame');

	const iframeOrigin = new URL(app_frame.src).origin;

	if (event.origin === iframeOrigin) {
		if (event.data && event.data.action === 'submitFormsAndToast') {
			trataSubmitFormsAndToast(app_frame, event);
		} else if (event.data && event.data.action === 'enviaSearchProduct') {
			trataEnviaSearchProduct(event);
		} else if (event.data && event.data.action === 'loadPageInConteinerMain') {
			loadPageInConteinerMain(event.data.link);
		}
	}
});

function trataSubmitFormsAndToast(iframe, event) {

	const iframeDocument = iframe.contentWindow.document;

	const { formId, dadosForm, destinationUrl } = event.data;

	const form = iframeDocument.getElementById(formId);

	submitFormsAndToast(form, dadosForm, event, destinationUrl);
}

function trataEnviaSearchProduct(event) {

	const { search, idDataList, idItensRecompra } = event.data;

	enviaSearchProduct(search, idDataList, idItensRecompra);
}