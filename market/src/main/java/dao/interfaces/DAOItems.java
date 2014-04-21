package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dao.transfer.BeanItem;
import dao.transfer.ItemTransfer;
import dao.transfer.SearchConditionTransfer;

/**
 * Interface of DAO object to get information from 
 * marketplace data base table 'Items'
 * @author Denis_Shipilov
 *
 */
public interface DAOItems {
	
	/**
	 * Method to select items from data base
	 * @return
	 * @throws SQLException
	 */	
	List<ItemTransfer> selectItems();

	/**
	 * Method to delete item into data base
	 * @param itemId
	 * @return
	 * @throws SQLException
	 */	
	int deleteItem(int itemId);
	
	/**
	 * Method to find item use part of title string
	 * this method returns {@link List} of {@link ItemTransfer}
	 * @param itemTitle
	 * @return
	 * @throws SQLException
	 */
	List<ItemTransfer> findItemTitle(String itemTitle);
	
	/**
	 * Method to find item with Description part
	 * this method returns {@link List} of {@link ItemTransfer}
	 * @param itemDescription
	 * @return
	 * @throws SQLException
	 */
	List<ItemTransfer> findItemDescription(String itemDescription);
	

	
	/**
	 * Method to find item by id
	 * @param itemId
	 * @return
	 * @throws SQLException
	 */
	ItemTransfer findItemId(int itemId);
	

	
	/**
	 *  Method to insert item in data base. It's return 
	 *  the count of processed row and sets true identifier 
	 *  of item in data base into itemTransfer object
	 * @param itemTransfer
	 * @return
	 * @throws SQLException
	 */
	int insertItem(ItemTransfer itemTransfer);
	
	/**
	 * Method to select bean items to show it on the web site
	 * in accordance with page number
	 * @return
	 */
	List<BeanItem> selectBeanItems(
			String columnName,String sortCrit,
			int pageNumber, int maximumItemsOnPage);
	
	/**
	 * Method to get from data base items for a given user
	 * @param userName
	 * @return List of {@link BeanItem}
	 */
	List<BeanItem> selectMyBeanItems(int userId,
						int pageNumber,	int maximumItemsOnPage,
						String sortingColumnName, String sortCrit);
	
	
	/**
	 * Method to select bean item by id
	 * @param itemId
	 * @return {@link BeanItem}
	 */
	BeanItem selectBeanItemById(int itemId);
	
	
	
	/**
	 * Method to update item 
	 * for update we have to know the ID of item
	 * @param item
	 * @return count of performing rows
	 */
	int updateItem(ItemTransfer item);
	
	
	
	/**
	 * Method to select count of items 
	 * returned by view
	 * @return count of items
	 */
	int selectItemCount();
	
	/**
	 * Method to select columns by condition 
	 * from one of columns
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	int selectItemsCountByCondition(String columnName,
										String columnValue);
	
	/**
	 * Method to select bean items by condition
	 * @param columnName
	 * @param columnValue
	 * @param pageNumber
	 * @param maximumItemsOnPage
	 * @return
	 */
	List<BeanItem> selectBeanItemByCondition(
							String columnName,String columnValue,
							String sortingColumnName, String sortCrit,
							int pageNumber,	int maximumItemsOnPage);
	
	
	
	/**
	 * Method to advanced search bean items by
	 * @param condition
	 * @return
	 */
	List<BeanItem> advancedSerchItem(
			String sortingColumnName, String sortCrit,			 
			int pageNumber, int maximumItemsOnPage,
			SearchConditionTransfer condition);
	
	
	/**
	 * Method to select all bean items
	 * @return
	 */
	List<BeanItem> selectAllBeanItems();
	
}
