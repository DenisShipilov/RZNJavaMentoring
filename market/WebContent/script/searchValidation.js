/**
 * Function for item validation
 * 
 * @return alert message
 */
function checkItem(form) {
	var titleReg = /^[a-zA-Z_]+[\w\s\.,!?]*$/;
	var priceReg = /^[0-9]+\.\d+$/i;
	var numberReg = /^[0-9]+$/i;
	var timeReg = /^\d+\:([0-5][0-9])$/;
	var dateReg = /^[0-1][0-9]\/[0-3][0-9]\/\d{4}\s[0-5][0-9]\:[0-5][0-9]$/i;
	markErrorFields(false);
	var errmes = "";
	// check full name
	if (form.elements["itemId"].value) {
		if (!numberReg.test(form.elements["itemId"].value)) {
			errmes += "ItemId contains invalid values\n";
			markErrorFields(form.elements["itemId"]);
		}
	}
	// check full name
	if (form.elements["title"].value) {
		if (!titleReg.test(form.elements["title"].value)) {
			errmes += "Title contains invalid values\n";
			markErrorFields(form.elements["title"]);
		}
	}
	// check min price
	if (form.elements["minPrice"].value) {
		if (!priceReg.test(form.elements["minPrice"].value)) {
			errmes += "Min Price contains invalid values\n";
			markErrorFields(form.elements["minPrice"]);
		}
	}

	// check max price
	if (form.elements["maxPrice"].value) {
		if (!priceReg.test(form.elements["maxPrice"].value)) {
			errmes += "Max Price contains invalid values\n";
			markErrorFields(form.elements["maxPrice"]);
		}
	}
	
	//now check the dates
	if(form.elements["startDateStr"].value){
		if (!dateReg.test(form.elements["startDateStr"].value)) {
			errmes += "Start Date contains invalid values\n";
			markErrorFields(form.elements["startDateStr"]);
		}
	}
	if(form.elements["expireDateStr"].value){
		if (!dateReg.test(form.elements["expireDateStr"].value)) {
			errmes += "Expire Date contains invalid values\n";
			markErrorFields(form.elements["expireDateStr"]);
		}
	}
	
	//check bidder count
	if (form.elements["bidderCount"].value) {
		if (!numberReg.test(form.elements["bidderCount"].value)) {
			errmes += "Bidder Count contains invalid values\n";
			markErrorFields(form.elements["bidderCount"]);
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