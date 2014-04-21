/**
 * Function for login and password
 * validation on logging page
 * @return
 */
function checkLogin() {
	var login = document.forms["logging"]["login"].value;
	var password = document.forms["logging"]["password"].value;
	var ptrn = new RegExp("^.{6,12}$","i");
	if (login == "" || login == null) {
		alert("You have to fill login");
		return false;
	} else {
		if(password == "" || password == null) {
			alert("You have to fill password");
			return false;
		} else {
			if (!ptrn.test(password)) {
				alert("Your password is little then six symbols" +
						"\n\ror more than twelve");
				return false;
			}
		}
	} 
}