package spring.controllers;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import dao.factories.DAOAbstractFactory;
import dao.transfer.UserTransfer;


/**
 * Controller to perform login action
 * @author Denis_Shipilov
 *
 */
public class LoginController extends SimpleFormController {
	
	
	
	private UserTransfer user;
	private ModelAndView modelAndView;
	private DAOAbstractFactory daoFactory;

	
		

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command,
			BindException errors)
			throws Exception {
		UserTransfer userTransfer = (UserTransfer) command;
		//translate user from transfer to bean user
		UserTransfer daoUser = daoFactory.getDAOUsers().findUser(
									userTransfer.getLogin(), 
									userTransfer.getPassword());
		translateUser(daoUser);
		modelAndView = new ModelAndView(getSuccessView());
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
