const btn_expandir = document.getElementById("btn-expandir")
const menu_lateral = document.getElementById("menu_lateral")
const text_link = document.querySelectorAll(".text-link")

control = 0

btn_expandir.addEventListener("click", function(){
		
    btn_expandir.style.width = "100px"
    control++ 

    if (control == 2){ // Fecha o botão
        menu_lateral.style.width = "80px"
        control = 0
        
	    text_link.forEach(function(text_link) { // Abre e fecha o texto
	      if (text_link.style.display === 'none') {
	        text_link.style.display = 'block';
	      } else {
	        text_link.style.display = 'none';
	      }
	    });
	    
  	  } else {
			
        menu_lateral.style.width = "300px"
        
        setTimeout(function() { // Abre e fecha o texto com um Delay de 0.6s
		    text_link.forEach(function(text_link) {
		      if (text_link.style.display === 'none') {
		        text_link.style.display = 'block';
		      } else {
		        text_link.style.display = 'none';
		      }
	    	});
  		}, 600);
    }
})