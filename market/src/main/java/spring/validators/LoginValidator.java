package spring.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOUsers;
import dao.transfer.UserTransfer;

public class LoginValidator implements Validator {
	
	private static final String LOGIN_FIELD = "login"; 
	private static final String LOGIN_ERROR = "login.errorLogin";
	private static final String LOGIN_ERROR_MESSAGE = "login or password " +
																"is invalid";
	
	
	private DAOAbstractFactory factory;

	/**
	 * I don't know what this method did
	 */
	public boolean supports(Class clazz) {
		return clazz.equals(UserTransfer.class);
	}

	/**
	 * Method to validate user 
	 */
	public void validate(Object command, Errors errors) {
		//try to get user from command
		UserTransfer userCommand = (UserTransfer) command;
		// now try to get data from data base about user	
		DAOUsers daoUsers = factory.getDAOUsers();
		//now return UserTransfer Object
		UserTransfer user = daoUsers.findUser(userCommand.getLogin(),
											  userCommand.getPassword());
		//then if statement
		if(user == null) {
			errors.rejectValue(LOGIN_FIELD,LOGIN_ERROR, LOGIN_ERROR_MESSAGE);
		}
	}

	/**
	 * @param factory the factory to set
	 */
	public void setDaoFactory(DAOAbstractFactory factory) {
		this.factory = factory;
	}

}
