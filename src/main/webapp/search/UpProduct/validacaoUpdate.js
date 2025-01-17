const form = document.getElementById("frmUpdateProduct");

document.addEventListener('DOMContentLoaded', () => {
	configureFormValidation(form.id);
});

function configureFormValidation(formId) {

	const form = document.getElementById(formId);

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
			document.forms["frmUpdateProduct"].submit();
		}
	});
}

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

function mostrarNotificação() {
	const toast = document.getElementById("wrapper_toast");
	toast.style.display = "block";

	setTimeout(() => {
		toast.style.animation = "backOutDown 1s ease-in-out forwards";

		toast.addEventListener("animationend", () => {
			toast.style.display = "none";
		});
	}, 3000);
}

function voltarMenu(){
	window.location.href = "/CarvalhoStore/search/Home/ListProduct"
}