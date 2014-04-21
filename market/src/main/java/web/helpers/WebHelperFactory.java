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

public class WebHelperFactory extends AbstractHelperFactory {
	
	private HttpServletRequest request;
	

	/**
	 * Constructor of web helper factory
	 */
	public WebHelperFactory(HttpServletRequest request) {
		super();
		this.request = request;
	}


	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public CommandHelper getCommandHelper() {
		return new WebCommandHelper(this);
	}
	
	@Override
	public UserHelper getUserHelper() {
		return new WebUserHelper(this);
	}


	@Override
	public ItemsHelper getItemsHelper() {
		return new WebItemsHelper(this);
	}


	@Override
	public BidHelper getBidHelper() {
		return new WebBidHelper(this);
	}


	@Override
	public ErrorHelper getErrorHelper() {
		return new WebErrorHelper(this);
	}


	@Override
	public CategoryHelper getCategoryHelper() {
		return new WebCategoryHelper(this);
	}


	@Override
	public PageHelper getPageHelper() {
		return new WebPageHelper(this);
	}


	@Override
	public HistoryHelper getHistoryHelper() {
		return new WebHistoryHelper(this);
	}


	@Override
	public BlockHelper getBlockHelper() {
		return new WebBlockHelper(this);
	}


	@Override
	public ItemStatusHelper getItemStatusHelper() {
		return new WebItemStatusHelper(this);
	}


	@Override
	public SearchHelper getSearchHelper() {
		return new WebSearchItemHelper(this);
	}


	@Override
	public SortingHelper getSortingHelper() {
		return new WebSortingHelper(this);
	}

}
