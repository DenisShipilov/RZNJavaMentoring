package helpers.interfaces;

import java.util.Map;


/**
 * Interface for category helper
 * @author Denis_Shipilov
 *
 */
public interface CategoryHelper {
	
	
	/**
	 * Method to get map of category from session
	 * @return {@link Map} of category
	 */
	Map<Integer, String> getCategoryMapFromSession();
	
	
	/**
	 * Method to set {@link Map} of category to session
	 * @param map
	 */
	void setCategoryMapToSession(Map<Integer, String> map);
	
	/**
	 * Method to get category as string parameter
	 * from request
	 * @return  name of category
	 */
	String getCategoryStringParameterFromRequest();
	
	
	/**
	 * Method to get item category from request
	 * as int like in Items table
	 * @return category as int
	 */
	int getCategoryAsIntFromRequest();

}
