package spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import dao.transfer.UserTransfer;

/**
 * Controller to correct logout
 * @author Denis_Shipilov
 *
 */
public class LogoutController extends AbstractController {
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	private UserTransfer user;
	private String success;
	private ModelAndView modelAndView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		modelAndView = new ModelAndView(success);
		user.clearUser();
		modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
		return modelAndView;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

}
