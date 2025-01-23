function configureFormValidation() {

	const form = document.getElementById('frmUpdateProduct');

	const category = document.getElementById("category")

	form.querySelectorAll('input, select').forEach(field => {
		field.addEventListener('invalid', (event) => {
			field.style.border = '2px solid red';
			field.setCustomValidity('Este campo é obrigatório!');

			if (event.target.validity.patternMismatch) {
				event.target.setCustomValidity('Por favor, insira apenas números, vírgulas ou pontos.');
			}

		});

		field.addEventListener('input', () => {
			field.style.border = '';
			field.setCustomValidity('');  // Limpa a validade personalizada para permitir nova validação
		});
	});

	form.addEventListener('submit', (event) => {
		if (category.value === "0") {
			category.style.border = '2px solid red';
			// Impede o envio do formulário 
			event.preventDefault();
		} else {
			submitFormsAndToast(form.id,event,'ListProduct');
		}
	});
}

configureFormValidation();

function formatarNumero() { // Event onchange
	let input = document.getElementById("vp");
	let value = input.value.replace(/,/g, ".");
	let isFirstPoint = false;
	let isSegundPoint = false;
	let novaStr = "";

	for (let i = 0; i < value.length; i++) {
		if (value[i] === ".") {
			if (!isFirstPoint) { // Primeira interação === true, demais interações sempre false
				isFirstPoint = true;
				novaStr += value[i];
			} else if (!isSegundPoint) {
				// Como o IF sempre será false após a primeira interação todos os demais pontos cairam aqui
				isSegundPoint = true;
			}
		} else {
			novaStr += value[i];
		}
	}

	input.value = novaStr;
}

function voltarMenu(event) {
	loadPageInConteinerMain(event, 'ListProduct');
}