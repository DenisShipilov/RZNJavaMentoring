package web.helpers;

import helpers.interfaces.HistoryHelper;

import java.util.List;

import dao.transfer.HistoryTransfer;

/**
 * Class to set history to request
 * @author Denis_Shipilov
 *
 */
public class WebHistoryHelper implements HistoryHelper {
	
	/*
	 * Constant of history attribute name
	 */
	private static final String HISTORY_ATTRIBUTE_NAME = "history";


	private WebHelperFactory helperFactory;
	
	
	/**
	 * Constructor of web history helper
	 * @param helperFactory
	 */
	public WebHistoryHelper(WebHelperFactory helperFactory) {
		super();
		this.helperFactory = helperFactory;
	}


	
	public void setHistoryToRequest(List<HistoryTransfer> history) {
		helperFactory.getRequest().setAttribute(HISTORY_ATTRIBUTE_NAME,
															  history);

	}

}
