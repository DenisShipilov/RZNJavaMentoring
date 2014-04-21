package spring.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOHistory;
import dao.transfer.BlockTransfer;
import dao.transfer.HistoryTransfer;
import dao.transfer.UserTransfer;


/**
 * Class to show and perform history of bid for item
 * @author Denis_Shipilov
 *
 */
public class ShowHistoryController extends MultiActionController {
	
	

	/*
	 * Constant of history attribute name
	 */
	private static final String HISTORY_ATTRIBUTE_NAME = "history";
	
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	
	/*
	 * Constant of black list attribute name
	 */
	private static final String BLACK_LIST_ATTR_NAME = "blackList";
	
	
	private ModelAndView modelAndView;
	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	private AbstractHelperFactory helperFactory;
	
	
	
	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}
	
	
	
	/**
	 * Base Method to show history
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showHistory(HttpServletRequest request,
		      HttpServletResponse response) throws Exception {
		// creating factory
		createFactory(request);
		//init show items
		initShowItems();
		//return model
		return modelAndView;		
	}



	/*
	 * Method to init show items
	 */
	private void initShowItems() {
		DAOHistory daoHistory = daoFactory.getDAOHistrory();
		//getting item id from request attributes
		int itemId = helperFactory.getItemsHelper().getItemId();
		List<HistoryTransfer> history = daoHistory.
										getHistoryOfBidForItem(itemId);		
		DAOBlackList daoBlack = daoFactory.getDAOBlackList();
		List<BlockTransfer> blackList = daoBlack.selectBlackList();
		
		//creating model and view
		modelAndView = new ModelAndView();
		modelAndView.addObject(USER_ATTRIBUTE_NAME,user);
		modelAndView.addObject(BLACK_LIST_ATTR_NAME,blackList);
		modelAndView.addObject(HISTORY_ATTRIBUTE_NAME,history);
	}
	
	
	/**
	 * Method to set block for user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView setBlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		createFactory(request);
		int userId = user.getUserId();
		int blockedUserId = helperFactory.getUserHelper()
				.getUserIdFromRequest();
		if (blockedUserId != userId) {
			BlockTransfer block = new BlockTransfer(userId, blockedUserId);
			// now set block to data base
			DAOBlackList daoBlack = daoFactory.getDAOBlackList();
			daoBlack.setBlock(block);
		}
		initShowItems();
		return modelAndView;
	}
	
	
	/**
	 * Method to remove block from user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeBlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// creating factory
		createFactory(request);
		int blockedUserId = helperFactory.getUserHelper().
										getUserIdFromRequest();
		int userId = user.getUserId();
		DAOBlackList daoBlock = daoFactory.getDAOBlackList();
		//remove block
		daoBlock.removeBlock(userId, blockedUserId);
		//init show items
		initShowItems();		
		return modelAndView;
		
	}
		
	
	/**
	 * @param modelAndView the modelAndView to set
	 */
	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}
	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}
	

	
	

}
