package helpers.interfaces;

import java.sql.Date;
import java.util.List;

import dao.transfer.BeanItem;
import dao.transfer.ItemTransfer;

/**
 * Interface for get and set items data to the context
 * @author Denis_Shipilov
 *
 */
public interface ItemsHelper {
	
	
	/**
	 * Method to insert items to session
	 */
	void setItemsToSession(List<ItemTransfer> items);
	
	
	/**
	 * Method to insert beanItem list to session
	 */
	void setBeanItemsToRequest(List<BeanItem> items);

	
	
	/**
	 * Method to get item id from request
	 * @return
	 */
	int getItemId();
	
	
	/**
	 * Method to set item to session as attribute
	 * @param item
	 */
	void setItemToRequest(ItemTransfer item);
	
	
	/**
	 * Method to get item from request
	 * @return {@link ItemTransfer}
	 */
	ItemTransfer getItemFromRequest();
	
	
	
	/**
	 * Method to get title from request
	 * @return title
	 */
	String getTitleFromRequest();
	
	
	/**
	 * Method to get description from request
	 * @return description
	 */
	String getDescriptionFromRequest();
	
	
	
	/**
	 * Method to get start price from request
	 * @return
	 */
	int getStartPriceFromRequest();
	
	
	/**
	 * Method to get time left from request
	 * @return
	 */
	int getTimeLeftFromRequest();
	
	/**
	 * Method to get buy it now flag from request
	 * @return
	 */
	boolean getBuyItNowFromRequest();
	
	
	/**
	 * Method to get bid increment from request
	 * @return bid_increment
	 */
	int getBidIncrement();
	
	
	/**
	 * Method to get start bidding date of item
	 * @return {@link Date}
	 */
	Date getStartBiddingDateFromRequest();
}
