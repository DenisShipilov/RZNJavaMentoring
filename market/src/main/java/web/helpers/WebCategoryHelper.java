package web.helpers;

import helpers.interfaces.CategoryHelper;

import java.util.Map;

/**
 * Class to process categories of items in application
 * @author Denis_Shipilov
 *
 */
public class WebCategoryHelper implements CategoryHelper {
	
	
	/*
	 * Constant of category attribute name
	 */
	private static final String CATEGORY_ATTRIBUTE_NAME = "categories";
	
	
	/*
	 * Category attribute name as request parameter
	 */
	private static final String CATEGORY_PARAMETER_NAME = "category";
	

	private WebHelperFactory helperFactory;

	
	/**
	 * Constructor of WebCategoryHelper
	 * @param helperFactory
	 */
	public WebCategoryHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCategoryMapFromSession() {
		return (Map<Integer, String>) helperFactory.getRequest().
				getSession().getAttribute(CATEGORY_ATTRIBUTE_NAME);
	}

	public void setCategoryMapToSession(Map<Integer, String> map) {
		helperFactory.getRequest().getSession().
						setAttribute(CATEGORY_ATTRIBUTE_NAME, map);

	}



	public String getCategoryStringParameterFromRequest() {
		return helperFactory.getRequest().
						getParameter(CATEGORY_PARAMETER_NAME);
	}
	

	public int getCategoryAsIntFromRequest() {
		String category = helperFactory.getRequest().getParameter(
											CATEGORY_PARAMETER_NAME);
		return Integer.valueOf(category);
	}

}
