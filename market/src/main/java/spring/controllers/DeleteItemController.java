package spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import web.helpers.AbstractHelperFactory;
import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOItems;


public class DeleteItemController extends SimpleFormController {
	
	
	private DAOAbstractFactory daoFactory;
	private AbstractHelperFactory helperFactory;
	
	
	
	/**
	 *  Method to create factory
	 */
	public void createFactory(HttpServletRequest request) {
		helperFactory = AbstractHelperFactory.
		getHelperFactory(AbstractHelperFactory.WEB_HELPER_FACTORY,
																request);
	}

	
	

	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse responce,Object command, BindException errors)
			throws Exception {
		createFactory(request);
		//now delete item
		int itemId = helperFactory.getItemsHelper().getItemId();
		DAOItems daoItems = daoFactory.getDAOItems();
		daoItems.deleteItem(itemId);
		return new ModelAndView(getSuccessView());
	}



	/**
	 * @param daoFactory the daoFactory to set
	 */
	public void setDaoFactory(DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

}
