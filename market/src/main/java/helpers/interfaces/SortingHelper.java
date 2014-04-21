package helpers.interfaces;

/**
 * Interface of sorting helper
 * @author Denis_Shipilov
 *
 */
public interface SortingHelper {
	
	
	/**
	 * Method to get sorting condition from request
	 * @return
	 */
	String getSortingCondition();
	
	/**
	 * Method to set sorting condition to request
	 * @param condition
	 */
	void setSortingCondition(String condition);
	
	
	/**
	 * Method to get sorting column from request
	 * @return
	 */
	String getSortingColumn();
	
	
	/**
	 * Method to set sorting column to request
	 * @param column
	 */
	void setSortingColumn(String column);

}
