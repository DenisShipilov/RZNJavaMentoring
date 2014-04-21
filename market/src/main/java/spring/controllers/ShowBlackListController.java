package spring.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBlackList;
import dao.transfer.BlockTransfer;
import dao.transfer.UserTransfer;

public class ShowBlackListController extends MultiActionController {
	
	
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
	
	
	
	/*
	 * Method to init model and view 
	 */
	private void initShowBlackList(){
		modelAndView = new ModelAndView();
		DAOBlackList daoBlack = daoFactory.getDAOBlackList();
		int userId = user.getUserId();
		List<BlockTransfer> blackList = daoBlack.
					findBlockedUsersForSpecifiedUser(userId);
		//set blackList to request
		modelAndView.addObject(BLACK_LIST_ATTR_NAME,blackList);
		modelAndView.addObject(USER_ATTRIBUTE_NAME,user);
	}
	
	
	
	/*
	 * base method to show black list
	 */
	public ModelAndView showBlackList(
			HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		initShowBlackList();
		return modelAndView;
	}
	
	
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
		initShowBlackList();
		return modelAndView;
		
		
	}
	



	/**
	 * @param user the user to set
	 */
	public void setUser(UserTransfer user) {
		this.user = user;
	}



	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

}
