document.addEventListener('DOMContentLoaded', function() {
	parent.postMessage({
		action: 'formatarNumeroDouble',
		elementId: 'valorT',
	}, '*');
});

document.getElementById("frmCadPedido").querySelectorAll('input, select').forEach(field => {
	field.addEventListener('invalid', (event) => {
		
		const fornecedor = document.getElementById('fornecedor');
		
		field.style.border = '2px solid red';

		if (fornecedor.value === "0") {
			fornecedor.style.border = '2px solid red';
		}

		if (event.target.validity.patternMismatch) { //Evento acionado qnd for digitado letras
			event.target.setCustomValidity('Por favor, insira apenas números, vírgulas ou pontos.');
		}
	});

	field.addEventListener('input', () => {
		field.style.border = '';
		field.setCustomValidity('');
	});
});

document.getElementById("frmCadPedido").addEventListener("submit", (event) => {
	event.preventDefault();

	const codigo = document.getElementById("codigo").value;

	const valor_pedido = document.getElementById("valorT").value;

	let destinationUrl = '/CarvalhoStore/search/Home/Cadproduct';
	destinationUrl += "?codigo=" + codigo + "&valorT=" + valor_pedido;

	parent.postMessage({
		action: 'submitFormsAndToast',
		formId: 'frmCadPedido',
		dadosForm: null,
		destinationUrl: destinationUrl
	}, '*');
});

document.getElementById("frmCadPedido").addEventListener('reset', (event) => {

	event.preventDefault();

	event.target.querySelectorAll('input, select').forEach(field => {
		field.style.border = '';
		field.setCustomValidity('');
	});

	event.target.querySelectorAll('input[type="text"], select').forEach(field => {
		if (field instanceof HTMLSelectElement) {
			field.value = "0";
		} else {
			field.value = '';
		}
	});
});