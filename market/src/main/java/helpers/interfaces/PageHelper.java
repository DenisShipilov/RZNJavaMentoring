package helpers.interfaces;

/**
 * interface to set methods to get information 
 * about page from context
 * @author Denis_Shipilov
 *
 */
public interface PageHelper {
	
	
	
	/**
	 * Method to get page number
	 * @return pageNumber
  	 */
	int getPageNumber();
	
	
	/**
	 * Method to set page number
	 * @param pageNumber
	 */
	void setPageNumber(int pageNumber);
	
	
	/**
	 * Method to get page count
	 * @return count of page
	 */
	int getPageCount();
	
	
	/**
	 * Method to set page count
	 * @param pageCount
	 */
	void setPageCount(int pageCount);
	
	

}
