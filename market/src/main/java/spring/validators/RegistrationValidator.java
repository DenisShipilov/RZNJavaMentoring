package spring.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOUsers;
import dao.transfer.UserTransfer;

public class RegistrationValidator implements Validator {
	
	private static final String LOGIN_FIELD = "login"; 
	private static final String LOGIN_ERROR = "login.errorLogin";
	private static final String LOGIN_ERROR_MESSAGE = "user with this " +
													"login allready exist";
	
	
	private DAOAbstractFactory factory;

	
	
	public boolean supports(Class clazz) {
		return clazz.equals(UserTransfer.class);
	}

	
	
	public void validate(Object command, Errors error) {
		//try to get user from command
		UserTransfer userCommand = (UserTransfer) command;
		// now try to get data from data base about user	
		DAOUsers daoUsers = factory.getDAOUsers();
		//now return UserTransfer Object
		UserTransfer user = daoUsers.findUser(userCommand.getLogin(),
											  userCommand.getPassword());
		//then if statement
		if(user != null) {
			error.rejectValue(LOGIN_FIELD,LOGIN_ERROR, LOGIN_ERROR_MESSAGE);
		} else {
			command = user;
		}

	}
	
	
	/**
	 * @param factory the factory to set
	 */
	public void setDaoFactory(DAOAbstractFactory factory) {
		this.factory = factory;
	}

}
