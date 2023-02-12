function validateLogin() {
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let invalid = document.getElementById("invalid");

    if (username.value === null || username.value === "") {
        username.focus();
        invalid.innerHTML = "Username can't be blank";
        return false;
    } else if (username.value.length < 4) {
        username.focus();
        invalid.innerHTML = "Username can't be have less than 4 characters";
        return false;
    } else if (username.value.length > 25) {
        username.focus();
        invalid.innerHTML = "Username can't have more than 25 characters";
        return false;
    } else if (!alphanumeric(username.value)) {
        username.focus();
        invalid.innerHTML = "Username can only contain numbers and letters";
        return false;
    } else if (password.value.length < 6 || password.value === "" || password.value === null) {
        password.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (password.value.length > 50) {
        password.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(password.value)) {
        password.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    }
    return true;
}

function validateKeyRoom() {
    let key = document.getElementById("key");
    let invalid = document.getElementById("invalid");

    if (key.value === null || key.value === "") {
        key.focus();
        invalid.innerHTML = "Key can't be blank";
        return false;
    } else if (key.value.length < 4) {
        key.focus();
        invalid.innerHTML = "Key can't be have less than 4 characters";
        return false;
    } else if (key.value.length > 25) {
        key.focus();
        invalid.innerHTML = "Key can't have more than 25 characters";
        return false;
    } else if (!alphanumeric(key.value)) {
        key.focus();
        invalid.innerHTML = "Key can only contain numbers and letters";
        return false;
    }
    
    return true;
}

function validateStudentInfo() {
    let firstName = document.getElementById("firstname");
    let lastName = document.getElementById("lastname");
    let number = document.getElementById("number");
    let birthday = document.getElementById("birthday");
    let invalid = document.getElementById("invalid");

    if (firstName.value === null || firstName.value === "") {
    	firstName.focus();
        invalid.innerHTML = "First name can't be blank";
        return false;
    } else if (firstName.value.length < 2) {
    	firstName.focus();
        invalid.innerHTML = "First name must have at least 3 characters";
        return false;
    } else if (firstName.value.length > 50) {
    	firstName.focus();
        invalid.innerHTML = "First name can't have more than 50 characters";
        return false;
    } else if (!isLetter(firstName.value)) {
    	firstName.focus();
        invalid.innerHTML = "First name can only contain letters";
        return false;
    }
    
    
    
    else if (lastName.value === null || lastName.value === "") {
    	lastName.focus();
        invalid.innerHTML = "Last name can't be blank";
        return false;
    } else if (lastName.value.length < 2) {
    	lastName.focus();
        invalid.innerHTML = "Last name must have at least 2 characters";
        return false;
    } else if (lastName.value.length > 50) {
    	lastName.focus();
        invalid.innerHTML = "Last name can't have more than 50 characters";
        return false;
    } else if (!isLetter(lastName.value)) {
    	lastName.focus();
        invalid.innerHTML = "Last name can only contain letters";
        return false;
    } 
    
    
    
    else if (number.value === null || number.value === "") {
    	number.focus();
        invalid.innerHTML = "Number can't be blank";
        return false;
    } else if (!isNumber(number.value)) {
    	number.focus();
        invalid.innerHTML = "Student number must contain only numbers";
        return false;
    }else if (parseInt(number.value) < 1) {
    	number.focus();
        invalid.innerHTML = "Number must be higher than 0";
        return false;
    } else if (parseInt(number.value) > 80000) {
    	number.focus();
        invalid.innerHTML = "Number must be less than 80000";
        return false;
    } 
    
    else if (birthday.value === null || birthday.value === "") {
    	birthday.focus();
        invalid.innerHTML = "Birthday can't be blank";
        return false;
    } else if (!isDateFormat(birthday.value)) {
    	birthday.focus();
        invalid.innerHTML = "Birthday is not on correct form, example: 02/06/1997";
        return false;
    }
    
    return true;
}


//validação submissão das perguntas
function validateQuestions() {
	let childCount = document.getElementById("teste").childElementCount;
    let question = document.getElementById("cb1-input");
    let questionCount = document.getElementById("cb1-listbox").childElementCount;
    let timer = document.getElementById("question-time");
    let invalid = document.getElementById("invalid");
    
    let choosedTheme = false;
    for (let i = 1; i < childCount+1; i++) {
		let elemento = document.getElementById("d" + i);  
		if(elemento.classList.contains("same-as-selected")){
			document.getElementById("district-name2").value = elemento.textContent;
			choosedTheme = true;
			break;
		}
	}
    if(choosedTheme === false){
    	document.getElementById("district-name2").focus();
        invalid.innerHTML = "You must choose a theme";
        return false;
    }
    
    
	let choosedQuestion = false;
	let elemList = document.getElementsByName("q-name");
	for (let i = 0; i < elemList.length; i++) {
		if (question.value === elemList[0].textContent) {
			choosedQuestion = true;
			break;
		}

	}
    
    if(choosedQuestion === false){
    	question.focus();
    	invalid.innerHTML = "You must have to introduce the whole question and the question must match with one of the opcions";
    	return false;
    }
    
    
    
    if(!isNumber(timer.value) || timer.value==="" || timer.value == null){
    	timer.focus();
    	invalid.innerHTML = "The time must be only numbers and it will be on seconds";
    	return false;
    }else if(parseInt(timer.value) > 300){
    	timer.focus();
    	invalid.innerHTML = "The time must be less than 300 seconds";
    	return false;
    }else if(parseInt(timer.value) < 20){
    	timer.focus();
    	invalid.innerHTML = "The time must be more than 20 seconds";
    	return false;
    }
    
    
    let twoQuestsError = false;
    let wroteSeparated = false;
    let errorSeparated = false;
    let indexesWrite = [];
    for (let i = 1; i < 7; i++) {
    	let elemento = document.getElementById("op" + i);
    	if((i === 1 || i ===2) && (elemento.value === "" || elemento.value == null)){
    		twoQuestsError = true;
    		break;
    	}
    	else if(elemento.value === "" || elemento.value == null){
    		wroteSeparated = true;
    	}
    	else if(wroteSeparated && (elemento.value !== "" || elemento.value != null)){
    		errorSeparated = true;
    		break;
    	}else if(elemento.value !== "" && elemento.value != null){
    		indexesWrite.push(i);
    		elemento.setAttribute("value", elemento.value);
    	}
    }
    
    if(twoQuestsError === true){
    	document.getElementById("op1").focus();
    	invalid.innerHTML = "You must have to write at least two questions on A and B";
    	return false;
    }
    
    if(errorSeparated === true){
    	document.getElementById("op1").focus();
    	invalid.innerHTML = "You can't write questions with blank align, like write on A,B and D it must be C";
    	return false;
    }
    
    let oneChecked = false;
    for (let i = 0; i < indexesWrite.length; i++) {
    	switch(i+1){
    		case 1:
    			if(document.getElementById('op-a').checked){
    				oneChecked = true;
    			}
    			break;
    		case 2:
    			if(document.getElementById('op-b').checked){
    				oneChecked = true;
    				
    			}
    			break;
    		case 3:
    			if(document.getElementById('op-c').checked){
    				oneChecked = true;
    			}
    			break;
    		case 4:
    			if(document.getElementById('op-d').checked){
    				oneChecked = true;
    			}
    			break;
    		case 5:
    			if(document.getElementById('op-e').checked){
    				oneChecked = true;
    			}
    			break;
    		case 6:
    			if(document.getElementById('op-f').checked){
    				oneChecked = true;
    			}
    			break;
    	}
    }
    
    if(oneChecked === false){
    	document.getElementById('op-a').focus();
    	invalid.innerHTML = "You must have at least one question selected as correct";
    	return false;
    }
    
    for (let i = 1; i < 7; i++) {
    	document.getElementById("op" + i).disabled = false;
    }
    document.getElementById("op-a").disabled = false;
    document.getElementById("op-b").disabled = false;
    document.getElementById("op-c").disabled = false;
    document.getElementById("op-d").disabled = false;
    document.getElementById("op-e").disabled = false;
    document.getElementById("op-f").disabled = false;
    
    
    if(document.getElementById("all-students").style.backgroundColor == "rgb(125, 116, 108)"){
    	document.getElementById("all-students").value = "picked";
    	document.getElementById("all-students").type = "text";
    }else{
    	document.getElementById("random-student").value = "picked";
    	document.getElementById("random-student").type = "text";
    }
 
    return true;
}



function validateSubmitStudentQuest(){
	let theme = document.getElementById("theme1");
	let quest = document.getElementById("quest1");
	let invalid = document.getElementById("invalid");
	
	if(theme.value == "" || theme.value == null){
		invalid.focus();
		invalid.innerHTML = "You can't submit a question without a question";
		return false;
	}
	
	if(quest.value == "" || quest.value == null){
		invalid.focus();
		invalid.innerHTML = "You can't submit a question without a question";
		return false;
	}
	
	quest.disabled = false;
	document.getElementById("see-info").href = "handleCheckDataStudent";
	document.getElementById("see-correction").href = "handleShowCorrection";
	return true;
	
}

function updateTime(){
	let seconds =parseInt(document.getElementById("question-time").value);
	let time = document.getElementById("question-time");
	
	setInterval(function() {
		if (parseInt(time.value) == 0) {
			document.getElementById("quest1").disabled = false;
			document.getElementById("see-info").href = "handleCheckDataStudent";
			document.getElementById("see-correction").href = "handleShowCorrection";
			document.getElementById("myForm").submit();
		}else{
			time.value = parseInt(time.value) - 1;
		}
		

	}, 1000); // repeat function every 1000 milliseconds

}


function validateNewPassword(){
	let lastpass = document.getElementById("last-pass");
	let newpass = document.getElementById("new-pass");
	let repeatpass = document.getElementById("repeat-pass");
	let invalid = document.getElementById("invalid");
	
	
	if (lastpass.value.length < 6 || lastpass.value === "" || lastpass.value === null) {
		lastpass.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (lastpass.value.length > 50) {
    	lastpass.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(lastpass.value)) {
    	lastpass.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    }
	
	if (newpass.value.length < 6 || newpass.value === "" || newpass.value === null) {
		newpass.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (newpass.value.length > 50) {
    	newpass.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(newpass.value)) {
    	newpass.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    }
	
	if (repeatpass.value.length < 6 || repeatpass.value === "" || repeatpass.value === null) {
		repeatpass.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (repeatpass.value.length > 50) {
    	newpass.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(repeatpass.value)) {
    	repeatpass.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    }
	
	if(newpass.value != repeatpass.value){
		newpass.focus();
        invalid.innerHTML = "Password must match with each other";
        return false;
	}
	return true;
}



function validateNewProf(){
	let username = document.getElementById("username");
	let newpass = document.getElementById("password");
	let repeatpass = document.getElementById("verify_password");
	let credential = document.getElementById("credential");
	let invalid = document.getElementById("invalid");
	
	if (username.value === null || username.value === "") {
        username.focus();
        invalid.innerHTML = "Username can't be blank";
        return false;
    } else if (username.value.length < 4) {
        username.focus();
        invalid.innerHTML = "Username can't be have less than 4 characters";
        return false;
    } else if (username.value.length > 25) {
        username.focus();
        invalid.innerHTML = "Username can't have more than 25 characters";
        return false;
    } else if (!alphanumeric(username.value)) {
        username.focus();
        invalid.innerHTML = "Username can only contain numbers and letters";
        return false;
    } 
    
    else if (password.value.length < 6 || password.value === "" || password.value === null) {
        password.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (password.value.length > 50) {
        password.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(password.value)) {
        password.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    } 
    
    else if (repeatpass.value.length < 6 || repeatpass.value === "" || repeatpass.value === null) {
    	repeatpass.focus();
        invalid.innerHTML = "Password must be at least 6 characters long";
        return false;
    } else if (repeatpass.value.length > 50) {
    	repeatpass.focus();
        invalid.innerHTML = "Password can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(repeatpass.value)) {
    	repeatpass.focus();
        invalid.innerHTML = "Password can only contain numbers and letters";
        return false;
    }
	
    else if(newpass.value != repeatpass.value){
    	repeatpass.focus();
        invalid.innerHTML = "Password must match with each other";
        return false;
    }
	
    else if (credential.value.length < 4 || credential.value === "" || credential.value === null) {
    	credential.focus();
        invalid.innerHTML = "Credential must be at least 4 characters long";
        return false;
    } else if (credential.value.length > 50) {
    	credential.focus();
        invalid.innerHTML = "Credential can't have more than 50 characters";
        return false;
    } else if (!alphanumeric(credential.value)) {
    	credential.focus();
        invalid.innerHTML = "Credential can only contain numbers and letters";
        return false;
    }
	return true;
}

function validateNewQuestion() {
	let theme = document.getElementById("theme");
	let question = document.getElementById("question");
	let time = document.getElementById("time-quest");
	let invalid = document.getElementById("invalid");
	
	if (theme.value === null || theme.value === "") {
		theme.focus();
        invalid.innerHTML = "Theme can't be blank";
        return false;
    } else if (theme.value.length < 2) {
    	theme.focus();
        invalid.innerHTML = "Theme can't be have less than 2 characters";
        return false;
    } else if (theme.value.length > 25) {
    	theme.focus();
        invalid.innerHTML = "Theme can't have more than 25 characters";
        return false;
    } else if (!alphanumeric(theme.value)) {
    	theme.focus();
        invalid.innerHTML = "Theme can only contain numbers and letters";
        return false;
    }
	
	if (question.value === null || question.value === "") {
		question.focus();
        invalid.innerHTML = "Question can't be blank";
        return false;
    } else if (question.value.length < 4) {
    	question.focus();
        invalid.innerHTML = "Question can't be have less than 4 characters";
        return false;
    }
	
	if (time.value === null || time.value === "") {
		time.focus();
        invalid.innerHTML = "Time can't be blank";
        return false;
    } else if (time.value.length < 2) {
		time.focus();
        invalid.innerHTML = "Time must have two digits";
        return false;
        
    }else if (time.value.length > 5) {
       time.focus();
       invalid.innerHTML = "Time must have less than five digits";
       return false;
    } else if (!isNumber(time.value)) {
    	time.focus();
        invalid.innerHTML = "Time must be only numbers";
        return false;
    }
	
	let optionsGroupElement = document.getElementById("optionsGroup");
	let numberOfOption = optionsGroupElement.childElementCount;
	let flag = false;
	
	
	for(let i = 1; i< numberOfOption+1;i++){
		let opc = document.getElementById("opc"+i);
		if(opc.value == "" || opc.value == null){
			opc.focus();
			invalid.innerHTML = "Opcion can't be blanked";
	        return false;
		}
	}
	
	for(let i = 1; i< numberOfOption+1;i++){
		if(document.getElementById("chx"+i).checked){
			flag = true;
			break;
		}
	}
	
	
	if(!flag){
		document.getElementById("chx1").focus();
		invalid.innerHTML = "Must select at least one checkbox";
		return false;
	}
	
	
	return true;
}


function addMoreOptions() {
	let optionsGroupElement = document.getElementById("optionsGroup");

	let numberOfOption = optionsGroupElement.childElementCount + 1;

	if (numberOfOption < 7) {
		let nodeToAdd = document.createElement("div");
		nodeToAdd.setAttribute("style", "display: flex; align-items: center");

		let labelNode = document.createElement("label");
		labelNode.setAttribute("for", "opc" + numberOfOption);
		labelNode
				.setAttribute(
						"style",
						"font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 96px;")
		let textNode = document.createTextNode("Option " + numberOfOption)
		labelNode.appendChild(textNode);

		nodeToAdd.appendChild(labelNode);

		let inputDiv = document.createElement("div");
		inputDiv.setAttribute("class", "wrap-input100 validate-input");
		inputDiv.setAttribute("style", "width: 50%;")

		let inputNode = document.createElement("input");
		inputNode.setAttribute("id", "opc" + numberOfOption);
		inputNode.setAttribute("name", "opc" + numberOfOption);
		inputNode.setAttribute("class", "input100");
		inputNode.setAttribute("type", "text");
		inputNode.setAttribute("style",
				"padding: 0px; margin-top: 20px; font-size: 20px;")

		let checkBox = document.createElement("input");
		checkBox.setAttribute("id", "chx" + numberOfOption);
		checkBox.setAttribute("name", "chx" + numberOfOption);
		checkBox.setAttribute("type", "checkbox");
		checkBox.setAttribute("style", "float: right; margin: auto;");

		inputDiv.appendChild(inputNode);

		nodeToAdd.appendChild(inputDiv);
		nodeToAdd.appendChild(checkBox);

		optionsGroupElement.appendChild(nodeToAdd);
	}else{
		document.getElementById("invalid").innerHTML = "Cannot add more than 6 opcions";
	}
}

function removeOption() {
	let invalid = document.getElementById("invalid");

	let elem = document.getElementById("optionsGroup");
	if (elem.childElementCount > 2) {
		elem.removeChild(elem.lastChild);
	} else {
		invalid.innerHTML = "Cannot remove, must have at least 2 options";
	}
}


function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function alphanumeric(inputtxt) {
    let letterNumber = /^[0-9a-zA-Z]+$/;
    return inputtxt.match(letterNumber);
}

function letterSpace(inputtxt) {
    let letterNumber = /[^\u0000-\u00ff]/;/*/^[A-Za-z ]+$/;*/
    return !inputtxt.match(letterNumber);
}

function validatorISO(str) {
	  return !/[^\u0000-\u00ff]/g.test(str);
}

function isLetter(inputtxt) {
    let letterNumber = /^[a-zA-Z]+$/;
    return inputtxt.match(letterNumber);
}

function isNumber(inputtxt) {
    let letterNumber = /^\d+$/;
    return inputtxt.match(letterNumber);
}

function isDateFormat(inputtxt) {
    let letterNumber = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
    return inputtxt.match(letterNumber);
}

