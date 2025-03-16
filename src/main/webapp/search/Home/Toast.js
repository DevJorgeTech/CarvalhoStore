const wrapper_toast = document.getElementById("wrapper_toast");
const pType = document.getElementById("type");
const pStatus = document.getElementById("status");

let isFirstNotification = false;
let isSecundNotification = false;
let actualClass;

const time = 2000

function mostrarNotificação(type, status) {
	
	if(!isFirstNotification){
		wrapper_toast.classList.add(type);
		isFirstNotification = true;
		actualClass = type;
	} else if(!isSecundNotification){
		wrapper_toast.classList.replace(actualClass,type);
		actualClass = type;
	}

	pType.textContent = type;

	pStatus.textContent = status;

	wrapper_toast.style.display = "block";

	// Inicia a animação de entrada (backInRight)
	wrapper_toast.style.animation = "backInRight 1s ease-in-out forwards";

	wrapper_toast.addEventListener("animationend", () => {
		// Agora reaplicamos a animação de saída (backOutDown)
		wrapper_toast.style.animation = "backOutDown 1s ease-in-out forwards 1.5s";
	});
}










