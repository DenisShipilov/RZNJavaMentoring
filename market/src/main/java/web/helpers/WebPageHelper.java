package web.helpers;

import helpers.interfaces.PageHelper;

public class WebPageHelper implements PageHelper {
	
	/*
	 * Constant of page number attribute name
	 */
	private static final String PAGE_NUMBER_ATTR_NAME = "pageNumber";
	
	
	/*
	 * Constant of page count attribute name
	 */
	private static final String PAGE_COUNT_ATTR_NAME = "pageCount";
	
	

	private WebHelperFactory helperFactory;
	
	/**
	 * @param helperFactory
	 */
	public WebPageHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}
	
	
	

	public int getPageCount() {
		String pageNumberString = helperFactory.getRequest().
								getParameter(PAGE_COUNT_ATTR_NAME);
		return Integer.valueOf(pageNumberString);
	}

	public int getPageNumber() {
		int pageNumber = 0;
		String pageNumberString = helperFactory.getRequest().
								getParameter(PAGE_NUMBER_ATTR_NAME);
		
		if(pageNumberString != null) {
			if(!pageNumberString.equals("")){
			pageNumber = Integer.valueOf(pageNumberString);
			} else {
				pageNumber = 1;
			}
		} else {
			pageNumber = 1;
		}
		return pageNumber;
	}

	public void setPageCount(int pageCount) {
		helperFactory.getRequest().
					setAttribute(PAGE_COUNT_ATTR_NAME,pageCount);

	}

	public void setPageNumber(int pageNumber) {
		helperFactory.getRequest().
					setAttribute(PAGE_NUMBER_ATTR_NAME, pageNumber);

	}

}
