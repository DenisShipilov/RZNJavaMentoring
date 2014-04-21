package web.helpers;

import helpers.interfaces.BlockHelper;

import java.util.List;

import dao.transfer.BlockTransfer;

public class WebBlockHelper implements BlockHelper {
	
	/*
	 * Constant of black list attribute name
	 */
	private static final String BLACK_LIST_ATTR_NAME = "blackList";
	
	
	private WebHelperFactory helperfactory;
	
	
	
	/**
	 * Constructor of Web Block helper 
	 * @param helperfactory
	 */
	public WebBlockHelper(WebHelperFactory helperfactory) {
		super();
		this.helperfactory = helperfactory;
	}

	
	public void setBlackListToRequest(List<BlockTransfer>
											blockTransferList) {
		helperfactory.getRequest().setAttribute(BLACK_LIST_ATTR_NAME, 
													blockTransferList);

	}

}
