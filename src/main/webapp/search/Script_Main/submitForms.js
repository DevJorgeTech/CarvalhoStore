function submitFormsAndToast(formId, dadosForm, event, destinationUrl) {

	event.preventDefault();

	let form = document.getElementById(formId);
	
	const formData = new FormData(form);
	
	formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

	fetch(form.action, {
		method: form.getAttribute('method'),
		body: formData
	}).then(response => response.json())
		.then(data => {
			mostrarNotificação(data.type, data.status);

			if (destinationUrl !== '' && destinationUrl && data.type === "Sucesso") {
				loadPageInConteinerMain(event, destinationUrl);
			}
		})
		.catch(error => console.error('Erro ao enviar os dados:', error));
}

function resetForm(formID, notElementReset) {

	document.getElementById(formID).querySelectorAll('input[type="text"],select').forEach(field => {

		if (notElementReset && field.id === notElementReset) {
			return;
		}
		if (field instanceof HTMLInputElement) {
			field.value = "";
		} else if (field instanceof HTMLSelectElement) {
			field.value = "0";
		}
	});
}
