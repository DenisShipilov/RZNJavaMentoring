/**
 * Function for user registration validation validation on logging page
 * @return alert message
 */
function checkRegistration(form) {
	var nameReg = new RegExp("^[^\\s][\\w\\s]*$", "i");
	var regPassword = new RegExp("^.{6,12}$","i");
	var adrPattern=/[0-9a-z_]+@[0-9a-z_]+\.[a-z]{2,5}/i;
	
	markErrorFields(false);
	var errmes = "";
	// check full name
	if (!form.elements["fullName"].value) {
		errmes += "You have to enter full name\n";
		markErrorFields(form.elements["fullName"]);
	} else if (!nameReg.test(form.elements["fullName"].value)) {
		errmes += "Your name contains invalid values\n";
		markErrorFields(form.elements["fullName"]);
	}
	if (!form.elements["address"].value) {
		errmes += "You have to enter bidding address\n";
		markErrorFields(form.elements["address"]);
	}
	if(!form.elements["email"].value){
		errmes += "You have to enter e-mail\n";
		markErrorFields(form.elements["email"]);
	} else if(!adrPattern.test(form.elements["email"].value)){
		errmes += "Email is incorrect\n";
		markErrorFields(form.elements["email"]);
	}
	if (!form.elements["login"].value) {
		errmes += "You have to enter login\n";
		markErrorFields(form.elements["login"]);
	}
	if (!form.elements["password"].value) {
		errmes += "You have to enter password\n";
		markErrorFields(form.elements["password"]);
	} else if (!regPassword.test(form.elements["password"].value)) {
		errmes += "Your password is more than twelve\n"
				+ "or little than six symbols";
		markErrorFields(form.elements["password"]);
	} else if (!form.elements["confirm"].value) {
		errmes += "You have to confirm your password\n";
		markErrorFields(form.elements["confirm"]);
	} else if (form.elements["password"].value != form.elements["confirm"].value) {
		errmes += "You password confirmation does not\n"
				+ "equal your password";
		markErrorFields(form.elements["confirm"]);
	}
	// if we have error
	if (errmes != "") {
		alert(errmes + "");
		return false;
	} else {
		return true;
	}
}

/**
 * Function to set color of labels
 */
function markErrorFields(element) {
	var allLabels = document.getElementsByTagName("LABEL");
	if (element) {
		for ( var i = 0; i < allLabels.length; i++) {
			if (allLabels[i].htmlFor == element.id)
				allLabels[i].style.color = "red";
		}
	} else {
		for ( var i = 0; i < allLabels.length; i++) {
			allLabels[i].style.color = "black";
		}
	}
}