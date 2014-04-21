package dao.interfaces;

import java.util.List;

import dao.transfer.CategoryTransfer;

/**
 * Interface of method which process category table in database
 * @author Denis_Shipilov
 *
 */
public interface DAOCategory {
	
	/**
	 * Method to select all categories from database
	 * @return
	 */
	List<CategoryTransfer> selectCategories();
	
	
	/**
	 * Method to select category by id
	 * @return
	 */
	CategoryTransfer selectCategoryById(int categoryId);
	
	
	/**
	 * Method to insert category into database
	 * @return
	 */
	int insertCategory(CategoryTransfer category);

	
	

}
