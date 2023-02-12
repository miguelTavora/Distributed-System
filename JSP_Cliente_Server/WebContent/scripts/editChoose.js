$('#all-students').click(function () {
    $('#all-students').css('background-color', '#7D746C');
    $('#all-students').css('color', '#fff');
    $('#random-student').css('background-color', '#f7fbfe');
    $('#random-student').css('color', '#111');
});

$('#random-student').click(function () {
	$('#random-student').css('background-color', '#7D746C');
    $('#random-student').css('color', '#fff');
    $('#all-students').css('background-color', '#f7fbfe');
    $('#all-students').css('color', '#111');
});

$('#ckb1').click(function() {
    if(document.getElementById('ckb1').checked) {
    	$("#question-time").prop("disabled", false);
    	$("#op1").prop("disabled", false);
    	$("#op-a").prop("disabled", false);
    	$("#op2").prop("disabled", false);
    	$("#op-b").prop("disabled", false);
    	$("#op3").prop("disabled", false);
    	$("#op-c").prop("disabled", false);
    	$("#op4").prop("disabled", false);
    	$("#op-d").prop("disabled", false);
    	$("#op5").prop("disabled", false);
    	$("#op-e").prop("disabled", false);
    	$("#op6").prop("disabled", false);
    	$("#op-f").prop("disabled", false);
    }
    else{
    	$("#question-time").prop('disabled', true);
    	$("#op1").prop('disabled', true);
    	$("#op-a").prop("disabled", true);
    	$("#op2").prop("disabled", true);
    	$("#op-b").prop("disabled", true);
    	$("#op3").prop("disabled", true);
    	$("#op-c").prop("disabled", true);
    	$("#op4").prop("disabled", true);
    	$("#op-d").prop("disabled", true);
    	$("#op5").prop("disabled", true);
    	$("#op-e").prop("disabled", true);
    	$("#op6").prop("disabled", true);
    	$("#op-f").prop("disabled", true);
    }
      
});

function selectorBasedOnQuestion(info) {
	let childCount = document.getElementById("cb1-listbox").childElementCount;
	let separatedQuestion = info.split(";");
	let separatedInfo = [];
	for(let i = 0;i < separatedQuestion.length; i++){
		let params = separatedQuestion[i].split(",");
		separatedInfo.push(params);
	}
	for (let i = 1; i < childCount+1; i++) {
		let elemento = document.getElementById("q" + i);
		elemento.addEventListener("click", function(e) {
			resetAllCheckedBox();
			document.getElementById("question-time").value = separatedInfo[i-1][0];
			switch(separatedInfo[i-1].length-1){
				case 4:
					document.getElementById("op1").value = separatedInfo[i-1][1];
					document.getElementById("op2").value = separatedInfo[i-1][2];
					if(separatedInfo[i-1][3] === "certo"){
						document.getElementById("op-a").checked = true;
					}
					if(separatedInfo[i-1][4] === "certo"){
						document.getElementById("op-b").checked = true;
					}
					break;
				case 6:
					document.getElementById("op1").value = separatedInfo[i-1][1];
					document.getElementById("op2").value = separatedInfo[i-1][2];
					document.getElementById("op3").value = separatedInfo[i-1][3];
					if(separatedInfo[i-1][4] === "certo"){
						document.getElementById("op-a").checked = true;
					}
					if(separatedInfo[i-1][5] === "certo"){
						document.getElementById("op-b").checked = true;
					}
					if(separatedInfo[i-1][6] === "certo"){
						document.getElementById("op-c").checked = true;
					}
					break;
					
				case 8:
					document.getElementById("op1").value = separatedInfo[i-1][1];
					document.getElementById("op2").value = separatedInfo[i-1][2];
					document.getElementById("op3").value = separatedInfo[i-1][3];
					document.getElementById("op4").value = separatedInfo[i-1][4];
					if(separatedInfo[i-1][5] === "certo"){
						document.getElementById("op-a").checked = true;
					}
					if(separatedInfo[i-1][6] === "certo"){
						document.getElementById("op-b").checked = true;
					}
					if(separatedInfo[i-1][7] === "certo"){
						document.getElementById("op-c").checked = true;
					}
					if(separatedInfo[i-1][8] === "certo"){
						document.getElementById("op-d").checked = true;
					}
					break;
					
				case 10:
					document.getElementById("op1").value = separatedInfo[i-1][1];
					document.getElementById("op2").value = separatedInfo[i-1][2];
					document.getElementById("op3").value = separatedInfo[i-1][3];
					document.getElementById("op4").value = separatedInfo[i-1][4];
					document.getElementById("op5").value = separatedInfo[i-1][5];
					if(separatedInfo[i-1][6] === "certo"){
						document.getElementById("op-a").checked = true;
					}
					if(separatedInfo[i-1][7] === "certo"){
						document.getElementById("op-b").checked = true;
					}
					if(separatedInfo[i-1][8] === "certo"){
						document.getElementById("op-c").checked = true;
					}
					if(separatedInfo[i-1][9] === "certo"){
						document.getElementById("op-d").checked = true;
					}
					if(separatedInfo[i-1][10] === "certo"){
						document.getElementById("op-e").checked = true;
					}
					break;
					
				case 12:
					document.getElementById("op1").value = separatedInfo[i-1][1];
					document.getElementById("op2").value = separatedInfo[i-1][2];
					document.getElementById("op3").value = separatedInfo[i-1][3];
					document.getElementById("op4").value = separatedInfo[i-1][4];
					document.getElementById("op5").value = separatedInfo[i-1][5];
					document.getElementById("op5").value = separatedInfo[i-1][6];
					if(separatedInfo[i-1][7] === "certo"){
						document.getElementById("op-a").checked = true;
					}
					if(separatedInfo[i-1][8] === "certo"){
						document.getElementById("op-b").checked = true;
					}
					if(separatedInfo[i-1][9] === "certo"){
						document.getElementById("op-c").checked = true;
					}
					if(separatedInfo[i-1][10] === "certo"){
						document.getElementById("op-d").checked = true;
					}
					if(separatedInfo[i-1][11] === "certo"){
						document.getElementById("op-e").checked = true;
					}
					if(separatedInfo[i-1][12] === "certo"){
						document.getElementById("op-f").checked = true;
					}
					break;
			}
		});
	}
}

//função da set das perguntas para os alunos
function setQuestionToStudent(info){
	let listInfo = info.split(",");
	
	document.getElementById("theme1").value = listInfo[0];
	if(listInfo[1].length > 300){
		document.getElementById("quest1").value = listInfo[1].substring(0,300);
		document.getElementById("quest2").value = listInfo[1].substring(301,listInfo[1].length);
	}else{
		document.getElementById("quest1").value = listInfo[1];
	}
	document.getElementById("question-time").value = listInfo[2];
	for(let i = 1; i < listInfo.length-2;i++){
		document.getElementById("op"+i).value = listInfo[2+i];
	}
	
	switch(listInfo.length-3){
		case 2:
			document.getElementById("op-a").disabled = false;
			document.getElementById("op-b").disabled = false;
			break;
		case 3:
			document.getElementById("op-a").disabled = false;
			document.getElementById("op-b").disabled = false;
			document.getElementById("op-c").disabled = false;
			break;
		case 4:
			document.getElementById("op-a").disabled = false;
			document.getElementById("op-b").disabled = false;
			document.getElementById("op-c").disabled = false;
			document.getElementById("op-d").disabled = false;
			break;
		case 5:
			document.getElementById("op-a").disabled = false;
			document.getElementById("op-b").disabled = false;
			document.getElementById("op-c").disabled = false;
			document.getElementById("op-d").disabled = false;
			document.getElementById("op-e").disabled = false;
			break;
			
		case 6:
			document.getElementById("op-a").disabled = false;
			document.getElementById("op-b").disabled = false;
			document.getElementById("op-c").disabled = false;
			document.getElementById("op-d").disabled = false;
			document.getElementById("op-e").disabled = false;
			document.getElementById("op-f").disabled = false;
			break;
	}
	
	document.getElementById("see-info").removeAttribute("href");
	document.getElementById("see-correction").removeAttribute("href");
}

//função da set das perguntas para os alunos
function setInfoToStudent(info){
	let listInfo = info.split(",");
	
	document.getElementById("first-name").value = listInfo[0];
	document.getElementById("last-name").value = listInfo[1];
	document.getElementById("student-number").value = listInfo[2];
	document.getElementById("birthday").value = listInfo[3];
}

function setUsernameToEditProf(username){
	document.getElementById("prof-user").value = username;
}


function resetAllCheckedBox(){
	document.getElementById("op-a").checked = false;
	document.getElementById("op-b").checked = false;
	document.getElementById("op-c").checked = false;
	document.getElementById("op-d").checked = false;
	document.getElementById("op-e").checked = false;
	document.getElementById("op-f").checked = false;
}



function resetAll(){
	let btn = document.getElementById("reset-all");
	let childCount = document.getElementById("teste").childElementCount;
	let childCountQuest = document.getElementById("cb1-listbox").childElementCount;
	btn.addEventListener("click", function(e) {
		document.getElementById("district-name2").value = "Select theme:";
		for (let i = 1; i < childCount+1; i++) {
			let elemento = document.getElementById("d" + i);  
			if(elemento.classList.contains("same-as-selected")){
				elemento.classList.remove("same-as-selected");
			}
		}
		document.getElementById("question-time").value = "";
		document.getElementById("op1").value = "";
		document.getElementById("op2").value = "";
		document.getElementById("op3").value = "";
		document.getElementById("op4").value = "";
		document.getElementById("op5").value = "";
		document.getElementById("op6").value = "";
		resetAllCheckedBox();
	});
}