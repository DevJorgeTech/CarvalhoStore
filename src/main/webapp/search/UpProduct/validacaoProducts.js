const inputSearch = document.getElementById("search");
const formSearch = document.getElementById("formSearch");
const button_search_cancel = document.getElementById("button-search-cancel")

document.addEventListener("DOMContentLoaded", function() {    
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
    inputSearch.value = ''
	document.forms["formSearch"].submit();
  } else {
    typingTimer = setTimeout(function() {
      document.forms["formSearch"].submit();
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
	inputSearch.value = ''
	document.forms["formSearch"].submit();
}) // Faz apenas um envio vazio, logo retorna todos os produtos

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