package spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import dao.factories.DAOAbstractFactory;
import dao.transfer.UserTransfer;

/**
 * Controller to process registration
 * @author Denis_Shipilov
 *
 */
public class RegistrationController extends SimpleFormController {
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	
	private ModelAndView modelAndView;
	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UserTransfer commandUser = (UserTransfer)command;
		daoFactory.getDAOUsers().insertUser(commandUser);
		modelAndView = new ModelAndView(getSuccessView());
		//now create user 	
		translateUser(commandUser);
		modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
		return modelAndView;
	}
	
	
	/*
	 * Method to translate user from command object to bean object in session
	 */
	private void translateUser(UserTransfer userTransfer){
		user.setBillingAddress(userTransfer.getBillingAddress());
		user.setEmail(userTransfer.getEmail());
		user.setLogin(userTransfer.getLogin());
		user.setPassword(userTransfer.getPassword());
		user.setUserFullName(userTransfer.getUserFullName());
		user.setUserId(userTransfer.getUserId());
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}
	
	
	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	

}
