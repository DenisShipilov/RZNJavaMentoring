package web.helpers;

import helpers.interfaces.ItemsHelper;

import java.sql.Date;
import java.util.List;

import dao.transfer.BeanItem;
import dao.transfer.ItemTransfer;

/**
 * Method to get and set items data to the session or request 
 * it's no matter if we try to get attribute from request which
 * refers to item(s) or we try to get(set) itemTransferObject 
 * to request or session 
 * @author Denis_Shipilov
 *
 */
public class WebItemsHelper implements ItemsHelper {
	
	
	//Attribute name constants
	
	/*
	 * Constant of item id parameter name
	 */
	private static final String ITEM_ID_PARAMETER = "itemId";
	
	/*
	 * Constant of items attribute name
	 */
	private static final String ITEMS_ATTRIBUTE_NAME = "items";
	
	
	
	/*
	 * Constant of item attribute name
	 */
	private static final String ITEM_TRANSFER_ATTRIBUTE_NAME = "item";
	
	/*
	 * Constant to set attribute name for beanItems in session
	 */
	private static final String BEAN_ITEMS_ATTRIBUTE_NAME = "beanItems";
	
	
	/*
	 * Constant of item title attribute name
	 */
	private static final String ITEM_TITLE_NAME = "title";
	
	
	/*
	 * Constant of description attribute name
	 */
	private static final String ITEM_DESCRIPTION = 	"description";
	
	
	
	/*
	 * Constant of item start price attribute name
	 */
	private static final String ITEM_START_PRICE = "price";
	
	
	
	/*
	 * Constant of Item time left attribute name
	 */
	private static final String ITEM_TIME_LEFT = "time";
	
	
	
	/*
	 * Constant of start bidding date attribute name 
	 */
	private static final String ITEM_START_BIDDINGDATE = 
											"startBiddingDate";
	
	
	/*
	 * Constant of buy it now attribute name
	 */
	private static final String ITEM_BUY_IT_NOW_ATTRIBUTE_NAME =
													"buyNow";
	
	
	/*
	 * Constant of bid increment attribute name
	 */
	private static final String ITEM_BID_INCREMENT = "bidInc";
	
	//web helper factory to get request
	private WebHelperFactory helperFactory;
	
	/**
	 *   Constructor of web user helper
	 */
	public WebItemsHelper(WebHelperFactory factory) {
		this.helperFactory = factory;
	}
	
	

	public void setItemsToSession(List<ItemTransfer> items) {
		helperFactory.getRequest().getSession().
							setAttribute(ITEMS_ATTRIBUTE_NAME,items);
	}



	public void setBeanItemsToRequest(List<BeanItem> items) {
		helperFactory.getRequest().
		setAttribute(BEAN_ITEMS_ATTRIBUTE_NAME,items);		
	}



	public int getItemId() {
		String itemIdStrig = helperFactory.
							getRequest().getParameter(ITEM_ID_PARAMETER);
		//if we really get item id 
		if((itemIdStrig != null) && (!itemIdStrig.equals(""))) {
			return Integer.valueOf(itemIdStrig);
		} else {
			return 0;
		}
		
	}



	public void setItemToRequest(ItemTransfer item) {
		helperFactory.getRequest().
		setAttribute(ITEM_TRANSFER_ATTRIBUTE_NAME,item);	
	}



	public boolean getBuyItNowFromRequest() {
		String buy = helperFactory.getRequest().getParameter(
	    					ITEM_BUY_IT_NOW_ATTRIBUTE_NAME);	
		
		return  ((buy == null) || buy.equals("0")) ? false : true;
	}



	public String getDescriptionFromRequest() {
		 return helperFactory.getRequest().getParameter(
										ITEM_DESCRIPTION);
	}


                  
	public int getStartPriceFromRequest() {
		String price = helperFactory.getRequest().getParameter(
													ITEM_START_PRICE);
		return Integer.valueOf(price);
	}



	public int getTimeLeftFromRequest() {
		String timeString = helperFactory.getRequest().getParameter(
													ITEM_TIME_LEFT);
		int timeLeft = 0;
		if(!timeString.equals("")){
		String delims = "[:]";
		String[] times = timeString.split(delims);
		timeLeft = Integer.valueOf(times[0]) + 
						(Integer.valueOf(times[1])/60);
		}
		return timeLeft;
	}



	public String getTitleFromRequest() {
		return helperFactory.getRequest().getParameter(
													ITEM_TITLE_NAME);
	}



	public int getBidIncrement() {
		String bidInc = helperFactory.getRequest().getParameter(
				ITEM_BID_INCREMENT);
		int result = 0;
		if((!bidInc.equals(""))) {
			result =Integer.valueOf(bidInc);
		}
		return result;
	}



	public ItemTransfer getItemFromRequest() {
		return (ItemTransfer) helperFactory.getRequest().
					getAttribute(ITEM_TRANSFER_ATTRIBUTE_NAME);
	}



	public Date getStartBiddingDateFromRequest() {
		Date date = null;
		String stringDate =	helperFactory.getRequest().
		      				getParameter(ITEM_START_BIDDINGDATE);
		if(stringDate != null) {
			date = Date.valueOf(stringDate);
		}
		
		return date;
	}

}
