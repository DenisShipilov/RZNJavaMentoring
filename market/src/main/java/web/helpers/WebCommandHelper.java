package web.helpers;

import helpers.interfaces.CommandHelper;

public class WebCommandHelper implements CommandHelper {
	
	private WebHelperFactory factory;
	

	/**
	 *  Constructor of web command helper
	 */
	public WebCommandHelper(WebHelperFactory factory) {
		this.factory = factory;
	}

	public String getCommandName() {
		String path = factory.getRequest().getServletPath();
		return path.substring(1, path.lastIndexOf("."));
	}

}
