package spring.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dao.factories.DAOAbstractFactory;
import dao.transfer.UserTransfer;


/**
 * Class to check is guest enter or user
 * @author Denis_Shipilov
 *
 */
public class UserAutorizationInterceptor extends HandlerInterceptorAdapter {
	
	
	private static final String LOGIN_ERROR = "loginError";
	private static final String LOGIN_ERROR_MESSAGE = 
								"First You have to login";
	
	
	private UserTransfer user;
	private List<String> pathValue;
	private DAOAbstractFactory daoFactory;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean result = true;
		if (user.toString() != null) {
			if ((user.getLogin() == null) && (user.getPassword() == null)) {
				// check URI path
				for (String path : pathValue) {
					if (request.getRequestURI().contains(path)) {
						response.sendRedirect("Login.htm?" 
								+ LOGIN_ERROR + "=" + LOGIN_ERROR_MESSAGE);
						result = false;
					}
				}
			}
		}
		return result;
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


	/**
	 * @param pathValue the pathValue to set
	 */
	public void setPathValue(List<String> pathValue) {
		this.pathValue = pathValue;
	}
	
	

}
