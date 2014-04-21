/*
 * function to return error message if it exist
 */
function errorCheckFunc(errmes) {
	if (errmes != "") {
		alert(errmes + "");
		return false;
	}
}

/**
 * Function to check value of bid
 * @param form
 * @return if error
 */
function sendBid(form) {
	var bidReg = /^\d+$/i;
	var errmes = "";
	if(!form.elements["bidCount"].value) {
		errmes += "You have to insert bid";
	} else if(!bidReg.test(form.elements["bidCount"].value)){
		errmes +="Bid is incorrect";
	}	
	
	// if we have an error
    return errorCheckFunc(errmes);
}

/**
 * Function to check value of keyword of search 
 * @param form
 * @return false if error
 */
function checkSearch(form) {
	var keyreg = /^[\s\w]+$/i;
	var uidReg = /^\d+$/i;
	var errmes = "";
	if(!form.elements["keyword"].value) {
		errmes += "You have to insert key word";
	} else if(!keyreg.test(form.elements["keyword"].value)){
		errmes +="Key word is incorrect";
	} 
	if(form.elements["condition"].value == "itemId") {
		if(!uidReg.test(form.elements["keyword"].value)){
			errmes +="Key word is incorrect";
		}
		
	}
	return errorCheckFunc(errmes);
}

