package web.helpers;

import helpers.interfaces.SearchHelper;

/**
 * Class of search item helper
 * @author Denis_Shipilov
 *
 */
public class WebSearchItemHelper implements SearchHelper {
	
	/*
	 * Constant of condition
	 */
	private static final String CONDITION_PARAMETER = "condition";
	
	/*
	 * Constant of key word parameter
	 */
	private static final String KEY_WORD_PARAMETER = "keyword";
	
	/*
	 * Constant of search parameter
	 */
	private static final String SEARCH_PARAMETER = "search";

	private WebHelperFactory helperFactory;
	
	
	/**
	 * Constructor of web  search helper
	 * @param helperFactory
	 */
	public WebSearchItemHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}

	public String getCondition() {
		//try get condition
		return helperFactory.getRequest().
							getParameter(CONDITION_PARAMETER);
		}

	public String getKeyWord() {
		return helperFactory.getRequest().
							getParameter(KEY_WORD_PARAMETER);
	}

	public void setCondition(String condition) {
		helperFactory.getRequest().
				setAttribute(CONDITION_PARAMETER,condition);
		
	}

	public void setKeyWord(String keyword) {
		helperFactory.getRequest().
				 setAttribute(KEY_WORD_PARAMETER, keyword);
		
	}

	public void setSearch(boolean search) {
		helperFactory.getRequest().
				setAttribute(SEARCH_PARAMETER, search);
		
	}

}
