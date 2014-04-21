package web.helpers;
import helpers.interfaces.UserHelper;
import dao.transfer.UserTransfer;

/**
 * Class uses as helper to help users to login, or register, or enter as guest
 * 
 * @author Denis_Shipilov
 * 
 */
public class WebUserHelper implements UserHelper {

	private WebHelperFactory helperFactory;
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	
	/*
	 * userId attribute name
	 */
	private static final String USERID_ATTRIBUTE = "userId";
	
	/*
	 * Constant of login parameter
	 */
	public static final String LOGIN_PARAMETER = "login";

	/*
	 * Constant of password parameter
	 */
	public static final String PASSWORD_PARAMETER = "password";
	
	/*
	 * Constant of billing address parameter
	 */
	public static final String BILLINGADDRESS_PARAMETER = "address";
	
	/*
	 * Constant of email parameter
	 */
	public static final String EMAIL_PARAMETER = "email";
	
	
	/*
	 * Constant of user name parameter 
	 */
	public static final String FULL_NAME_PARAMETER = "fullName";
	

	/**
	 *  Constructor of web user helper
	 */
	public WebUserHelper(WebHelperFactory factory) {
		this.helperFactory = factory;
	}


	public UserTransfer getUserFromSession() {
	  return (UserTransfer) helperFactory.getRequest().
	  				getSession().getAttribute(USER_ATTRIBUTE_NAME);
		
	}


	public boolean removeUserFromSession() {
		boolean result = false;
		 helperFactory.getRequest().getSession().
							removeAttribute(USER_ATTRIBUTE_NAME);
		 if((helperFactory.getRequest().
				 getParameter(USER_ATTRIBUTE_NAME) == null) ||
			(helperFactory.getRequest().
					getParameter(USER_ATTRIBUTE_NAME).equals(""))){
			 result = true;
		 }
		return result;
	}


	public void setSessionForUser(UserTransfer user) {
		helperFactory.getRequest().getSession().
		setAttribute(USER_ATTRIBUTE_NAME,user);
	}


	public String getLogin() {
		return helperFactory.getRequest().getParameter(LOGIN_PARAMETER);
	}


	public String getPassword() {
		// TODO Auto-generated method stub
		return helperFactory.getRequest().
									getParameter(PASSWORD_PARAMETER);
	}


	public String getBillingAddress() {
		return helperFactory.getRequest().
								getParameter(BILLINGADDRESS_PARAMETER);
	}


	public String getEmail() {
		return helperFactory.getRequest().getParameter(EMAIL_PARAMETER);
	}


	public String getFullName() {
		return helperFactory.getRequest().
							getParameter(FULL_NAME_PARAMETER);
	}


	public int getUserIdFromRequest() {
		String userIdString = helperFactory.getRequest().
									getParameter(USERID_ATTRIBUTE);
		return Integer.valueOf(userIdString);
	}
}
