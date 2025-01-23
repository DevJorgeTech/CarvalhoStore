function formatarNumero(inputId) {
	let value = document.getElementById(inputId).value.replace(/,/g, ".");
	let isFirstPoint = false;
	let isSecondPoint = false;
	let novaStr = "";

	for (let i = 0; i < value.length; i++) {
		if (value[i] === ".") {
			if (!isFirstPoint) {
				isFirstPoint = true;
				novaStr += value[i];
			} else if (!isSecondPoint) {
				isSecondPoint = true;
			}
		} else {
			novaStr += value[i];
		}
	}

	document.getElementById(inputId).value = novaStr;
}