function demo(){
	alert("thymeleaf with css and js demo");
}

  function computeas(){
	for(i=1; i<=10; i++){
		var selectedAnswer = document.getElementsByName(i);
		for(let j of selectedAnswer){
	if(j.checked){
		alert(j.id);
	}

    }
	}

}