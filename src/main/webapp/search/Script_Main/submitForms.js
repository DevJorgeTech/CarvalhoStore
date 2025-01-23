function submitFormsAndToast(formId, event, destinationUrl) {

	event.preventDefault();

	let form = document.getElementById(formId);

	fetch(form.action, {
		method: form.getAttribute('method'),
		body: new FormData(form)
	}).then(response => response.json())
		.then(data => {
			mostrarNotificação(data.type, data.status);

			if (destinationUrl !== '' && destinationUrl) {
				loadPageInConteinerMain(event, destinationUrl);
			}

			if (data.type === "Sucesso") {
				resetForm(formId);
			}
		})
		.catch(error => console.error('Erro ao enviar os dados:', error));
}

function resetForm(formID) {
	
	document.getElementById(formID).querySelectorAll('input[type="text"],select').forEach(field => {
		if (field instanceof HTMLInputElement) {
			field.value = "";
		} else if (field instanceof HTMLSelectElement) {
			field.value = "0";
		}
	});
}
