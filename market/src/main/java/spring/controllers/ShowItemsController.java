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
import dao.interfaces.DAOBids;
import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOItems;
import dao.transfer.BeanItem;
import dao.transfer.BidTransfer;
import dao.transfer.BlockTransfer;
import dao.transfer.UserTransfer;

/**
 * controller to show items
 * 
 * @author Denis_Shipilov
 * 
 */
public class ShowItemsController extends MultiActionController {

	



	private static final String GUEST_EXCEPTION = "You Have To Login";

	/*
	 * Constant of maximum item on page
	 */
	private static final int MAXIMUM_ITEM_ON_PAGE = 10;
	
	//////////////////////////////////////////////////
	// 				ATTRIBUTE NAME CONSTANTS        //
	/////////////////////////////////////////////////

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
	private static final String PAGE_NUMBER_ATTR_NAME = "pageNumber";
	
	
	/*
	 * Constant of page count attribute name
	 */
	private static final String PAGE_COUNT_ATTR_NAME = "pageCount";

	// ///////////////////////////////////////////////
	// STATUS CONSTANTS //
	// //////////////////////////////////////////// //

	/*
	 * bid constant
	 */
	private static final String BID_STATUS = "bid";

	/*
	 * buy constant
	 */
	private static final String BUY_STATUS = "buy";

	/*
	 * sold constant
	 */
	private static final String SOLD_STATUS = "sold";

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
	private static final String BLOCK_STATUS = "You can't "
			+ "buy/bid this item ";
	
	
	
	// ///////////////////////////////////////////////
	//  BID  STATUS CONSTANTS 						//
	// //////////////////////////////////////////// //
	
	/*
	 * Constant if user set bid on his own item 
	 */
	private static final int SELF_INSERT = 2301;
	
	
	/*
	 * Constant of self insert error message
	 */
	private static final String SELF_INSERT_ERROR_MESSAGE = "You" +
								" can't set bid on self item";
	
	/*
	 * when bid is too small
	 */
	private static final int LITTLE_BID= 2302;
	
	/*
	 * Constant of little bid error
	 */
	private static final String BID_ERROR = "bidError";
	
	
	/*
	 * Constant of little bid error message
	 */
	private static final String LITLE_BID_ERROR_MESSAGE = "It's too little" +
			"bid";
	
	
	
	// ///////////////////////////////////////////////
	//  BUY  STATUS CONSTANTS 						//
	// //////////////////////////////////////////// //
	
	/*
	 * when bid is too small
	 */
	private static final int BUY_NOW= 2303;
	
	/*
	 * Constant of little bid error
	 */
	private static final String BUY_ERROR = "bidError";
	
	
	/*
	 * Constant of little bid error message
	 */
	private static final String BUY_ERROR_MESSAGE = 
		"It's already buyed";
	
	/*
	 * Constant if user set bid on his own item 
	 */
	private static final int SELF_BUY = 2301;
	
	
	/*
	 * Constant of self insert error message
	 */
	private static final String SELF_BUY_ERROR_MESSAGE = "You" +
								" can't buy self item";

	
	
	private AbstractHelperFactory helperFactory;
	private DAOAbstractFactory factory;
	private UserTransfer user;
	//item status map
	private Map<Integer, String> statusMap;	
	//private model and view field
	private ModelAndView modelAndView;
	
	
	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}
	
	
	/*
	 * Method to right init show items
	 */
	private ModelAndView initShowItems(HttpServletRequest request,
		      HttpServletResponse response){
		DAOItems daoItems = factory.getDAOItems();
		// getting page number
		int page = helperFactory.getPageHelper().getPageNumber();
		// getting sorting column and condition
		String columnName = helperFactory.getSortingHelper().getSortingColumn();
		String sortCrit = helperFactory.getSortingHelper()
				.getSortingCondition();
		// getting deanItems from data base
		// in accordance with the page number
		List<BeanItem> beanItems = daoItems.selectBeanItems(columnName,
				sortCrit, page, MAXIMUM_ITEM_ON_PAGE);
		// set how many pages we have as links
		int itemCount = daoItems.selectItemCount();
		int pageCount = (int) Math.floor(1 + (itemCount - 1)
				/ MAXIMUM_ITEM_ON_PAGE);
		DAOBlackList daoBlock = factory.getDAOBlackList();
		List<BlockTransfer> blackList = daoBlock.selectBlackList();
		addItemStatus(beanItems, blackList);
		// set sorting column and sorting condition to request
		// again
		helperFactory.getSortingHelper().setSortingColumn(columnName);
		helperFactory.getSortingHelper().setSortingCondition(sortCrit);
		//creating model and view
		modelAndView = new ModelAndView();
	
		// now add items to session
		modelAndView.addObject(ITEMS_ATTRIBUTE_NAME, beanItems);
		//put black list
		modelAndView.addObject(BLACK_LIST_ATTR_NAME, blackList);
		//put status map
		modelAndView.addObject(STATUS_ATTRIBUTE_NAME, statusMap);
		//we don't need to put categories here
		//modelAndView.addObject(CATEGORY_ATTR_NAME, categoryMap);
		
		//put page count
		modelAndView.addObject(PAGE_COUNT_ATTR_NAME, pageCount);
		//put page number 
		modelAndView.addObject(PAGE_NUMBER_ATTR_NAME, page);		
		//and now add user to modelAndView
		modelAndView.addObject(USER_ATTRIBUTE_NAME,user);
		
		return modelAndView;
	}
		
	
	/**
	 * {@inheritDoc}
	 */
	public ModelAndView showItems(HttpServletRequest request,
			      HttpServletResponse response) throws Exception {
		//create helper factory
		createFactory(request);
		return initShowItems(request, response); // states for form
	}
	
	
	/**
	 * Method to add bit to item
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView bid(HttpServletRequest request,
		      HttpServletResponse response) throws Exception {
		createFactory(request);
		//set bid
		float bid = helperFactory.getBidHelper().getBidCount();
		int userId = user.getUserId();
		int itemId = helperFactory.getItemsHelper().getItemId();
		//initialize model and view only for show items again
		modelAndView = initShowItems(request, response);
		if((user.getLogin() != null) && 
				(user.getPassword() != null)) {
		DAOBids daoBids = factory.getDAOBids();
		BidTransfer bidTransfer = new BidTransfer(userId, itemId, bid);
		int resultInsert = daoBids.insertBid(bidTransfer);
		//init modelAndView again after insert
		modelAndView = initShowItems(request, response);
		//add an error of bid in the modelAndView
		if (resultInsert == SELF_INSERT) {
			modelAndView.addObject(BID_ERROR,
					SELF_INSERT_ERROR_MESSAGE);
		}
		if (resultInsert == LITTLE_BID) {
			modelAndView.addObject(BID_ERROR,
					LITLE_BID_ERROR_MESSAGE);
		}
		} else {
			modelAndView.addObject(BID_ERROR,
					GUEST_EXCEPTION);
		}
		
 
	 return modelAndView;
		
	}
	
	
	
	/**
	 * Method to buy item
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView buy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		createFactory(request);
		int userId = user.getUserId();
		int itemId = helperFactory.getItemsHelper().getItemId();
		int price = helperFactory.getItemsHelper().getStartPriceFromRequest();
		DAOBids daoBids = factory.getDAOBids();
		modelAndView = initShowItems(request, response);
		if((user.getLogin() != null) && 
						(user.getPassword() != null)) {
		// create bid for buy item
		BidTransfer bid = new BidTransfer(userId, itemId, price);
		// try to set bid on item
		int resultInsert = daoBids.insertBid(bid);
		//init modelAndView again after insert
		modelAndView = initShowItems(request, response);
		if (resultInsert == SELF_BUY) {
			modelAndView.addObject(BUY_ERROR,
					SELF_BUY_ERROR_MESSAGE);
		}
		if (resultInsert == BUY_NOW) {
			modelAndView.addObject(BUY_ERROR,
					BUY_ERROR_MESSAGE);

		}
		if (resultInsert == SELF_INSERT) {
			modelAndView.addObject(BID_ERROR,
					SELF_INSERT_ERROR_MESSAGE);
		}
		if (resultInsert == LITTLE_BID) {
			modelAndView.addObject(BID_ERROR,
					LITLE_BID_ERROR_MESSAGE);

		}
		} else {
			modelAndView.addObject(BID_ERROR,
					GUEST_EXCEPTION);
		}
		
		return modelAndView;

	}
	
	
	/*
	 * Method to add map of item status to request
	 */
	private void addItemStatus(List<BeanItem> list,
								List<BlockTransfer> blackList){
	  Date nowDate = new Date(Calendar.
				getInstance().getTimeInMillis());
		 statusMap = new HashMap<Integer, String>();
		
		
		for (BeanItem beanItem : list) {
			boolean containsBlock = false;
			// check if item is blocked for current user
			for (BlockTransfer block : blackList) {
				if((beanItem.getSellerId() == block.getUserId()) &&
				   (block.getBlockedUserId() == user.getUserId())) {
					containsBlock = true;					
				}
			}
			
			//if can bid
			if(beanItem.getBuyItNow().equals("0") &&
				(beanItem.getMillisecondStopDate() > 
										nowDate.getTime()) &&
				(beanItem.getTimeLeft() != 0)){
				statusMap.put(beanItem.getItemId(),BID_STATUS);
				
			}
			//if can buy
			if(beanItem.getBuyItNow().equals("1") &&
			   ((beanItem.getMillisecondStopDate() > 
				nowDate.getTime()) ||
			    (beanItem.getTimeLeft() == 0)) &&
				beanItem.getBestOffer() == 0) {
				statusMap.put(beanItem.getItemId(),BUY_STATUS);
			}
			// if sold
			if(((beanItem.getMillisecondStopDate() < 
						nowDate.getTime()) &&
				(beanItem.getTimeLeft() != 0) &&
				(beanItem.getBuyItNow().equals("0")) &&
				(!beanItem.getBidder().equals(user.getUserFullName())) &&
				(beanItem.getBestOffer() != 0)	) || 
				(beanItem.getBuyItNow().equals("1") && 
				(beanItem.getBestOffer() != 0) 		&&
				(!beanItem.getBidder().equals(user.getUserFullName())))) {
				statusMap.put(beanItem.getItemId(), SOLD_STATUS);
			}
			//if purchased
			if(((beanItem.getMillisecondStopDate() < 
									  nowDate.getTime()) &&
					(beanItem.getBuyItNow().equals("0")) &&
					(beanItem.getBidder().equals(user.getUserFullName()))) || 
					(beanItem.getBuyItNow().equals("1") && 
					(beanItem.getBestOffer() != 0) 		&&
					(beanItem.getBidder().equals(user.getUserFullName())))) {
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
		//at this point we have full status map
	}

	
	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory factory) {
		this.factory = factory;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}
	
}
