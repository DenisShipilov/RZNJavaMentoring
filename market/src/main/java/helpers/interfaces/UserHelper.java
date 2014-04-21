package helpers.interfaces;
import dao.transfer.UserTransfer;


/**
 * Interface for login action helper
 * @author Denis_Shipilov
 *
 */
public interface UserHelper {
	

	/**
	 * Method to set session for current user who are inter
	 * @param user
	 */
	void setSessionForUser(UserTransfer user);
	
	/**
	 * Method to get user
	 */
	UserTransfer getUserFromSession();
	
	/**
	 * Method to remove user
	 */
	boolean removeUserFromSession();
	
	/**
	 * Method to get login of user 
	 * when user try to login to the system 
	 * @return login
	 */
	String getLogin();
	
	
	/**
	 * Method to get password of user
	 * when user try to login to the system
	 * @return
	 */
	String getPassword();
	
	/**
	 * Method to get full name
	 * @return full name of user
	 */
	String getFullName();
	
	/**
	 * Method to get billing address
	 * @return
	 */
	String getBillingAddress();
	
	/**
	 * Method to get Email
	 * @return string email
	 */
	String getEmail();	
	
	
	/**
	 * Method to get user id not from session 
	 * but from request
	 * @return userId
	 */
	int getUserIdFromRequest();
}
