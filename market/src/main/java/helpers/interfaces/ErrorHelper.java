package helpers.interfaces;

/**
 * Method to set error attribute in request
 * @author Denis_Shipilov
 *
 */
public interface ErrorHelper {
	

	/**
	 * Method to set error attribute in request
	 * @param attributeName
	 * @param errorMessage
	 */
	void setErrorAttribute(String attributeName, String errorMessage);
	
	
	/**
	 * Method to get attribute from request
	 * @param attributeName
	 * @return attribute value
	 */
	String getErrorAttribute(String attributeName);
	
}
