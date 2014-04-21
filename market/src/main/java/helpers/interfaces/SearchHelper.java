package helpers.interfaces;

/**
 * Interface for search helper
 * @author Denis_Shipilov
 *
 */
public interface SearchHelper {
	
	
	/**
	 * Method to get key word
	 * @return {@link String}
	 */
	String getKeyWord();
	
	
	/**
	 * Method to get condition 
	 * @return {@link String}
	 */
	String getCondition();
	
	/**
	 * Method for set condition to request
	 * @param condition
	 */
	void setCondition(String condition);
	
	/**
	 * Method for set keyword to request
	 * @param keyword
	 */
	void setKeyWord(String keyword);
	
	
	/**
	 * Method to set search parameter to say 
	 * is that request is search something or just show
	 * @param search
	 */
	void setSearch(boolean search);

}
