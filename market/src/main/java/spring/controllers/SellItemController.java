package spring.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOCategory;
import dao.interfaces.DAOItems;
import dao.transfer.CategoryTransfer;
import dao.transfer.ItemTransfer;
import dao.transfer.UserTransfer;

/**
 * Class to sell items
 * @author Denis_Shipilov
 *
 */
public class SellItemController extends SimpleFormController {
	

	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	/*
	 * category arribute name
	 */
	private static final String CATEGORES = "categores";

	
	private ModelAndView modelAndView;
	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	

	
	/**
	 * Method to set category to request
	 */
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request)
											throws Exception {
		//now try to set categories of items to session context
		DAOCategory daoCategory = daoFactory.getDAOCategory();
		List<CategoryTransfer> categores =
							daoCategory.selectCategories();
		Map<String,Object> categoryMap = 
					new HashMap<String,Object>();
		categoryMap.put(CATEGORES, categores);
		categoryMap.put(USER_ATTRIBUTE_NAME, user);
		return categoryMap;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ItemTransfer itemTtransfer = (ItemTransfer) command;
		modelAndView = new ModelAndView(getSuccessView());
		DAOItems daoItems = daoFactory.getDAOItems();
		Date startBiddingDate = new Date(Calendar.
						getInstance().getTimeInMillis());
		//set start bidding date to item
		itemTtransfer.setStartBiddingDate(startBiddingDate);
		//set seller id to item
		itemTtransfer.setSellerId(user.getUserId());
		daoItems.insertItem(itemTtransfer);
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
