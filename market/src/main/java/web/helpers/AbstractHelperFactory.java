package web.helpers;

import helpers.interfaces.BidHelper;
import helpers.interfaces.BlockHelper;
import helpers.interfaces.CategoryHelper;
import helpers.interfaces.CommandHelper;
import helpers.interfaces.ErrorHelper;
import helpers.interfaces.HistoryHelper;
import helpers.interfaces.ItemStatusHelper;
import helpers.interfaces.ItemsHelper;
import helpers.interfaces.PageHelper;
import helpers.interfaces.SearchHelper;
import helpers.interfaces.SortingHelper;
import helpers.interfaces.UserHelper;

import javax.servlet.http.HttpServletRequest;



/**
 * Class to create helpers to get information from web components
 * @author Denis_Shipilov
 *
 */
public abstract class AbstractHelperFactory {
	
	/*
	 * Constant of web helper factory type
	 */
	public static final int WEB_HELPER_FACTORY = 1;
	
	
	/**
	 * Method to return factory of web helpers
	 * @param helperFactoryType
	 * @return helper factory or null
	 */
	public static AbstractHelperFactory 
					getHelperFactory(int helperFactoryType, 
										HttpServletRequest request){
		switch (helperFactoryType) {
		case WEB_HELPER_FACTORY:
			return new WebHelperFactory(request);
		default:
			return null;
		}
	
	}
	
	
	/**
	 * Method to create login action helper
	 * @return LoginActionHelper
	 */
	public abstract UserHelper getUserHelper();
	
	/**
	 * Method to get command helper 
	 * @return instance of {@link CommandHelper}
	 */
	public abstract CommandHelper getCommandHelper();
	
	/**
	 * Method to get items helper
	 * @return instance of {@link ItemsHelper}
	 */
	public  abstract ItemsHelper getItemsHelper();
	
	/**
	 * Method to get bid helper
	 * @return instance of {@link BidHelper}
	 */
	public abstract BidHelper getBidHelper();
	
	/**
	 * Method to get error helper
	 * @return instance of {@link ErrorHelper}
	 */
	public abstract ErrorHelper getErrorHelper();
	
	/**
	 * Method to get category helper
	 * @return instance of {@link CategoryHelper}
	 */
	public abstract CategoryHelper getCategoryHelper();
	
	/**
	 * Method to get page helper
	 * @return instance of {@link PageHelper}
	 */
	public abstract PageHelper getPageHelper();
	
	
	/**
	 * Method to get history helper
	 * @return instance of {@link HistoryHelper}
	 */
	public abstract HistoryHelper getHistoryHelper();
	
	
	/**
	 * Method to get block helper
	 * @return instance of {@link BlockHelper}
	 */
	public abstract BlockHelper getBlockHelper();
	
	/**
	 * Method to get item status helper
	 * @return
	 */
	public abstract ItemStatusHelper getItemStatusHelper();
	
	/**
	 * Method to get search helper 
	 * @return
	 */
	public abstract SearchHelper getSearchHelper();
	
	/**
	 * Method to get sorting helper
	 * @return instance of {@link SortingHelper}
	 */
	public abstract SortingHelper getSortingHelper();
}
