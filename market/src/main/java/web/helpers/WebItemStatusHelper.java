package web.helpers;

import helpers.interfaces.ItemStatusHelper;

import java.util.Map;

public class WebItemStatusHelper implements ItemStatusHelper {
	

	//Attribute name constants
	
	/*
	 * Constant of items attribute name
	 */
	private static final String STATUS_ATTRIBUTE_NAME = "statusMap";
	
	//web helper factory to get request
	private WebHelperFactory helperFactory;
	
	
	
	/**
	 * Constructor of web item status helper
	 * @param helperFactory
	 */
	public WebItemStatusHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}


	public void setStatusMapToRequest(Map<Integer, String> statusMap) {
		helperFactory.getRequest().
				setAttribute(STATUS_ATTRIBUTE_NAME, statusMap);

	}

}
