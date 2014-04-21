package spring.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOItems;
import dao.transfer.BeanItem;
import dao.transfer.BlockTransfer;
import dao.transfer.UserTransfer;


/**
 * Controller to show users item 
 * @author Denis_Shipilov
 *
 */
public class ShowMyItemsController extends MultiActionController {
	
	
//////////////////////////////////////////////////
	// 				ATTRIBUTE NAME CONSTANTS        //
	/////////////////////////////////////////////////
	
	/*
	 * Constant of page number attribute name
	 */
	private static final String PAGE_NUMBER_ATTR_NAME = "pageNumber";

	/*
	 * constant of items attribute name
	 */
	private static final String ITEMS_ATTRIBUTE_NAME = "beanItems";
	
	/*
	 * constant of blacklist attribute name
	 */
	private static final String BLACK_LIST_ATTR_NAME = "blaclist";	
	
	
	/*
	 * Constant of items attribute name
	 */
	private static final String STATUS_ATTRIBUTE_NAME = "statusMap";
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	/*
	 * Constant of page number attribute name
	 */
	//private static final String PAGE_NUMBER_ATTR_NAME = "pageNumber";
	
	
	/*
	 * Constant of page count attribute name
	 */
	private static final String PAGE_COUNT_ATTR_NAME = "pageCount";
	
	
	
	
	
	/////////////////////////////////////////////////
	// 			  STATUS CONSTANTS				   //
	////////////////////////////////////////////// //
	
	/*
	 * edit constant
	 */
	private static final String EDIT_STATUS = "edit";
	
	/*
	 * sold constant
	 */
	private static final String SOLD_STATUS = "sold";
	
	/*
	 * bid constant
	 */
	private static final String BID_STATUS = "bid";
	
	/*
	 * purchased constant
	 */
	private static final String PURCHASED_STATUS = "purchased";
	
	
	/*
	 * time up
	 */
	private static final String TIME_UP_STATUS = "Time is Up";
	
	/*
	 * block status
	 */
	private static final String  BLOCK_STATUS = "You can't " +
			"									buy/bid this item ";
	
	
	
	
	/*
	 * Constant of maximum item on page
	 */
	private static final int MAXIMUM_ITEM_ON_PAGE = 10;
	
	
	
	/*
	 * Seller column query parameter
	 */
	private static final String SELLER_COLUMN = "seller";
	
	
	/*
	 * Bidder column query parameter
	 */
	private static final String BIDDER_COLUMN = "bidder";
	
	
	
	private UserTransfer user;
	private DAOAbstractFactory daoFactory;
	private AbstractHelperFactory helperFactory;
	private Map<Integer, String> statusMap;
	private ModelAndView modelAndView;
	
	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}
	
	
	private ModelAndView initShowMyItems(HttpServletRequest request,
		      HttpServletResponse response) {
		DAOItems daoItems = daoFactory.getDAOItems();
		//getting page number
		int page = helperFactory.getPageHelper().getPageNumber();
		//getting sorting column and sorting condition
		String columnName = helperFactory.getSortingHelper().
		  								getSortingColumn();
		String sortCrit = helperFactory.getSortingHelper().
										getSortingCondition();
		//select items where user is seller
		List<BeanItem> beanItems = daoItems.
		selectMyBeanItems(user.getUserId(),
								page,MAXIMUM_ITEM_ON_PAGE,
								columnName,sortCrit);
		//set how many pages we have as links
		int itemCount = daoItems.
				selectItemsCountByCondition(SELLER_COLUMN, 
												user.getUserFullName());
		itemCount += daoItems.selectItemsCountByCondition(BIDDER_COLUMN, 
												user.getUserFullName());
		int pageCount = (int) Math.floor(1+(itemCount-1)/
												MAXIMUM_ITEM_ON_PAGE);
		//get black
		DAOBlackList daoBlock = daoFactory.getDAOBlackList();
		List<BlockTransfer> blackList = daoBlock.selectBlackList();
		//try to add item status to request
		addItemStatus(beanItems, blackList);
		//set sorting column and sorting condition to request
		//again
		helperFactory.getSortingHelper().setSortingColumn(columnName);
		helperFactory.getSortingHelper().setSortingCondition(sortCrit);
		//creating modelAndView object
		modelAndView = new ModelAndView();
		// now add items to session
		modelAndView.addObject(ITEMS_ATTRIBUTE_NAME, beanItems);
		//put black list
		modelAndView.addObject(BLACK_LIST_ATTR_NAME, blackList);
		//put page count
		modelAndView.addObject(PAGE_COUNT_ATTR_NAME, pageCount);
		//add page number
		modelAndView.addObject(PAGE_NUMBER_ATTR_NAME,page);
		//and now add user to modelAndView
		modelAndView.addObject(USER_ATTRIBUTE_NAME,user);
		//add status map 
		modelAndView.addObject(STATUS_ATTRIBUTE_NAME, statusMap);
		return modelAndView;
		
	}
	
	
	
	/*
	 * Method to add map of item status to request
	 */
	private void addItemStatus(List<BeanItem> list,
								List<BlockTransfer> blackList){
	  Date nowDate = new Date(Calendar.
				getInstance().getTimeInMillis());
		    statusMap =	new HashMap<Integer, String>();
		int userId = user.getUserId();
		
		String userName = user.getUserFullName();
		
		
		for (BeanItem beanItem : list) {
			boolean containsBlock = false;
			// check if item is blocked for current user
			for (BlockTransfer block : blackList) {
				if((beanItem.getSellerId() == block.getUserId()) &&
				   (block.getBlockedUserId() == userId)) {
					containsBlock = true;					
				}
			}
			
			//if can edit
			if(((beanItem.getBuyItNow().equals("0") &&
				(beanItem.getMillisecondStopDate() > 
										nowDate.getTime()) &&
				(beanItem.getTimeLeft() != 0))     || 
				(beanItem.getBuyItNow().equals("1") &&
						   ((beanItem.getMillisecondStopDate() > 
							nowDate.getTime()) ||
						    (beanItem.getTimeLeft() == 0)) &&
							beanItem.getBestOffer() == 0))  &&
				(beanItem.getSellerId() == userId)){
				statusMap.put(beanItem.getItemId(),EDIT_STATUS);
				
			} 
			
			//if bid
			if((beanItem.getSellerId() != userId) && 
						(!beanItem.getBidder().equals(userName) ||
						  beanItem.getBidder().equals(userName)) &&
						 (beanItem.getMillisecondStopDate() > 
							nowDate.getTime())){
				statusMap.put(beanItem.getItemId(), BID_STATUS);	
			}

			// if sold
			if(((beanItem.getMillisecondStopDate() < 
						nowDate.getTime()) &&
				(beanItem.getTimeLeft() != 0) &&
				(beanItem.getBuyItNow().equals("0")) &&
				(!beanItem.getBidder().equals(userName)) &&
				(beanItem.getBestOffer() != 0) ) || 
				(beanItem.getBuyItNow().equals("1") && 
				(beanItem.getBestOffer() != 0) 		&&
				(!beanItem.getBidder().equals(userName)))) {
				statusMap.put(beanItem.getItemId(), SOLD_STATUS);
			}
			
			
			//if purchased
			if(((beanItem.getMillisecondStopDate() < 
									  nowDate.getTime()) &&
					(beanItem.getBuyItNow().equals("0")) &&
					(beanItem.getBidder().equals(userName))) || 
					(beanItem.getBuyItNow().equals("1") && 
					(beanItem.getBestOffer() != 0) 		&&
					(beanItem.getBidder().equals(userName)))) {
				statusMap.put(beanItem.getItemId(), PURCHASED_STATUS);
			}
			// if time is up
			if((beanItem.getMillisecondStopDate() <
							nowDate.getTime()) &&
			   (beanItem.getBestOffer() == 0) && 
			   (beanItem.getTimeLeft() != 0)){
				statusMap.put(beanItem.getItemId(), TIME_UP_STATUS);
			}
			
			//if blocked
			if(containsBlock == true) {
				statusMap.put(beanItem.getItemId(),BLOCK_STATUS);
			}
		  }
			
		}
	
	
	
	
	/**
	 * {@inheritDoc}
	 */
	public ModelAndView showMyItems(HttpServletRequest request,
			      HttpServletResponse response) throws Exception {
		// here we creating helper factory
		createFactory(request);
		//now return model and view using initShowMyItems method
		return initShowMyItems(request, response);
		
	}

	
	
	
	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}
	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	
	

}
