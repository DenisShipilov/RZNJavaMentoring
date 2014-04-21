/**
 * Function for item validation
 * 
 * @return alert message
 */
function checkItem(form) {
	var titleReg = /^[a-zA-Z_]+[\w\s\.,!?]*$/;
	var priceReg = /^[0-9]+\.\d+$/i;
	var timeReg = /^\d+\:([0-5][0-9])$/;
	markErrorFields(false);
	var errmes = "";
	// check full name
	if (!form.elements["title"].value) {
		errmes += "You have to enter title\n";
		markErrorFields(form.elements["title"]);
	} else if (!titleReg.test(form.elements["title"].value)) {
		errmes += "Title contains invalid values\n";
		markErrorFields(form.elements["title"]);
	}
	if (!form.elements["price"].value) {
		errmes += "You have to enter start price\n";
		markErrorFields(form.elements["price"]);
	} else if (!priceReg.test(form.elements["price"].value)) {
		errmes += "Price contains invalid values\n";
		markErrorFields(form.elements["price"]);
	}
	// if not buy it now
	if (!form.elements["buyNow"].checked) {
		// then need time left
		if(!form.elements["time"].value) {
			errmes += "You need to enter timeleft\n";
			markErrorFields(form.elements["time"]);
		} else if(!timeReg.test(form.elements["time"].value)){
			errmes += "Time bar have incorrect form\n";
			markErrorFields(form.elements["time"]);
		}
	} else if (form.elements["time"].value){
		if(!timeReg.test(form.elements["time"].value)){
			errmes += "Time bar have incorrect form\n";
			markErrorFields(form.elements["time"]);
		}
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