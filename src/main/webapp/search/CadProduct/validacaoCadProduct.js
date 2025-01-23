(function() {
	const form = document.getElementById("frmCadProduct");
	const category = document.getElementById("category");

	form.querySelectorAll('input, select').forEach(field => {
		field.addEventListener('invalid', (event) => {
			field.style.border = '2px solid red';

			if (category.value === "0") {
				category.style.border = '2px solid red';
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

	form.addEventListener('submit', (event) => {

		if (category.value === "0") {
			event.preventDefault();
			category.style.border = '2px solid red';
			category.setCustomValidity('Por favor, escolha uma categoria.'); //Cria uma mensagem personalizada
			category.reportValidity(); //Envia a mensagem personalizada
		} else {
			submitFormsAndToast(form.id, event);
		}
	});
	
	form.addEventListener('reset', function(){
		form.querySelectorAll('input, select').forEach(field => {
			field.style.border = '';
			field.setCustomValidity('');
		})
	})

})();

document.getElementById("vp").addEventListener('change', function() {
	formatarNumero(this.id);
});

