package spring.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOUsers;
import dao.transfer.UserTransfer;


/**
 * Class to identification user as guest or as registered 
 * user
 * @author Denis_Shipilov
 *
 */
public class UserIdentificationInterceptor extends HandlerInterceptorAdapter {

	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter
	 * #preHandle(javax.servlet.http.HttpServletRequest,
	 *  javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		DAOUsers daoUsers = daoFactory.getDAOUsers();
		UserTransfer userTransfer = daoUsers.findUserId(user.getUserId());
		if(userTransfer == null) {
			//set empty user 
			translateUser(new UserTransfer());
		}	
		return true;
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
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}
	
	
	

}
