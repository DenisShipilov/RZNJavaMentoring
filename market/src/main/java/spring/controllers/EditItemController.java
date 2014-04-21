package spring.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOCategory;
import dao.interfaces.DAOItems;
import dao.transfer.CategoryTransfer;
import dao.transfer.ItemTransfer;
import dao.transfer.UserTransfer;


/**
 * Class controller for edit items
 * @author Denis_Shipilov
 *
 */
public class EditItemController extends SimpleFormController {
	
	
	
	
	/*
	 * user attribute name
	 */
	private static final String USER_ATTRIBUTE_NAME = "user";
	
	/*
	 * category arribute name
	 */
	private static final String CATEGORES = "categores";
	
	
	private AbstractHelperFactory helperFactory;
	private ModelAndView modelAndView;
	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	

	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}
	
	
	
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
	
	
	
	/**
	 * Metod to return formBakingObject of edit form from showMyItems page 
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		createFactory(request);
		int itemId = helperFactory.getItemsHelper().getItemId();
		DAOItems daoItem = daoFactory.getDAOItems();
		ItemTransfer item = daoItem.findItemId(itemId);
		return item; // with blank Vehicle
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command,
			BindException errors)
			throws Exception {
		DAOItems daoItems = daoFactory.getDAOItems();
		ItemTransfer item = (ItemTransfer)command;
		daoItems.updateItem(item);
		modelAndView = new ModelAndView(getSuccessView());
		return modelAndView;
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
