package spring.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOItems;
import dao.transfer.BeanItem;
import dao.transfer.BlockTransfer;
import dao.transfer.UserTransfer;



/**
 * Controller for search item
 * 
 * @author Denis_Shipilov
 * 
 */
public class SearchItemController extends AbstractController {
	
	/*
	 * Constant of maximum item on page
	 */
	private static final int MAXIMUM_ITEM_ON_PAGE = 10;
	
	
	
	/*
	 * Constant of search error attribute
	 */
	private static final String SEARCH_ERROR = "searchError";
	
	/*
	 * Constant of search error message
	 */
	private static final String SEARCH_EMPTY_MESSAGE = 
							"The requested product does not exist";
	
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
	
	
	/////////////////////////////////////////////////
	// 			  STATUS CONSTANTS				   //
	////////////////////////////////////////////// //
	

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
	
	
	
	
	
	private AbstractHelperFactory helperFactory;
	private DAOAbstractFactory factory;
	private UserTransfer user;
	//item status map
	private Map<Integer, String> statusMap;	
	//private model and view field
	private ModelAndView modelAndView;



	private String successView;
	
	
	
	/**
	 * @param successView the successView to set
	 */
	public void setSuccess(String successView) {
		this.successView = successView;
	}



	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//creating helper factory
		createFactory(request);
		String keyword = helperFactory.getSearchHelper().getKeyWord();
		String condition = helperFactory.getSearchHelper().getCondition();
		DAOItems daoItems = factory.getDAOItems();
		// getting sorting column and sorting condition
		String columnName = helperFactory.getSortingHelper().getSortingColumn();
		String sortCrit = helperFactory.getSortingHelper()
				.getSortingCondition();

		// getting page number
		int page = helperFactory.getPageHelper().getPageNumber();
		List<BeanItem> beanItems = daoItems.selectBeanItemByCondition(
				condition, keyword, columnName, sortCrit, page,
				MAXIMUM_ITEM_ON_PAGE);
		//creating model and view
		modelAndView = new ModelAndView(successView);
		if (beanItems.size() == 0) {
			helperFactory.getErrorHelper().setErrorAttribute(SEARCH_ERROR,
					SEARCH_EMPTY_MESSAGE);
		} else {
			int itemCount = daoItems.selectItemsCountByCondition(condition,
					keyword);
			int pageCount = (int) Math.floor(1 + (itemCount - 1)
					/ MAXIMUM_ITEM_ON_PAGE);
			DAOBlackList daoBlock = factory.getDAOBlackList();
			List<BlockTransfer> blackList = daoBlock.selectBlackList();
			// try to add item status to request
			addItemStatus(beanItems, blackList);		
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

			// add searching parameters to request
			helperFactory.getSearchHelper().setCondition(condition);
			helperFactory.getSearchHelper().setKeyWord(keyword);
			helperFactory.getSearchHelper().setSearch(true);

		}	
		helperFactory.getSortingHelper().setSortingColumn(columnName);
		helperFactory.getSortingHelper().setSortingCondition(sortCrit);
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
