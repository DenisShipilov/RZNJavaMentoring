package web.helpers;

import helpers.interfaces.SortingHelper;

public class WebSortingHelper implements SortingHelper {
	
	/*
	 * constant of sorting column attribute name
	 */
	private static final String SORTING_COLUMN_ATTRIBUTE = 
											"sortCol";
	
	/*
	 * constant of sorting condition attribute name
	 */
	private static final String SORTING_CONDITION = "sc";

	private WebHelperFactory helperFactory;
	
	
	/**
	 * constructor of web sorting helper
	 * @param helperFactory
	 */
	public WebSortingHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}

	public String getSortingColumn() {
		String column = helperFactory.getRequest().
				getParameter(SORTING_COLUMN_ATTRIBUTE);
		return column == null ? "itemId" : column;
	}

	public String getSortingCondition() {
		String sc = helperFactory.getRequest().
						getParameter(SORTING_CONDITION);
		return sc == null ? "ASC" : sc;
	}

	public void setSortingColumn(String column) {
		helperFactory.getRequest().
				setAttribute(SORTING_COLUMN_ATTRIBUTE, column);

	}

	public void setSortingCondition(String condition) {
		helperFactory.getRequest().setAttribute(SORTING_CONDITION,
															condition);

	}

}
