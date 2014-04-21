package web.helpers;

import helpers.interfaces.ErrorHelper;

public class WebErrorHelper implements ErrorHelper {
	
	private WebHelperFactory helperFactory;
	
	/**
	 * Constructor of web helper factory
	 */
	public WebErrorHelper(WebHelperFactory helperFactory) {
		this.helperFactory = helperFactory;
	}



	public void setErrorAttribute(String attributeName,
											String errorMessage) {
		helperFactory.getRequest().
						setAttribute(attributeName, errorMessage);

	}



	public String getErrorAttribute(String attributeName) {
		return (String) helperFactory.getRequest().
									getAttribute(attributeName);
	}

}
