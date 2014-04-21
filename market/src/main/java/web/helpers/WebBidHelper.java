package web.helpers;

import helpers.interfaces.BidHelper;

public class WebBidHelper implements BidHelper {
	
	/*
	 * Constant of bid count attribute name
	 */
	private static final String BID_COUNT_ATTRIBUTE_NAME = "bidCount";
	
	private WebHelperFactory helperFactory;

	/**
	 * Constructor of web bid Helper
	 */
	public WebBidHelper(WebHelperFactory helperFactory) {
		this.helperFactory = helperFactory;
	}

	public float getBidCount() {
		String attr = (String) helperFactory.getRequest().
								getParameter(BID_COUNT_ATTRIBUTE_NAME);
		return Float.valueOf(attr);
	}

}
