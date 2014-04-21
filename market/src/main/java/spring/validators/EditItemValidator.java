package spring.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dao.factories.DAOAbstractFactory;
import dao.transfer.ItemTransfer;
import dao.transfer.UserTransfer;


/**
 * Class to validate command of edit action
 * @author Denis_Shipilov
 *
 */
public class EditItemValidator implements Validator {
	
	/////////////////////////////
	//    Error Constants      //
	////////////////////////////
	
	private static final String ITEM_FIELD = "itemId"; 
	private static final String ITEM_ERROR = "itemId.errorItem";
	private static final String ITEM_ERROR_MESSAGE = 
										"You can't edit this item";
	
	

	private DAOAbstractFactory daoFactory;
	private UserTransfer user;
	
	
	
	/**
	 * Method to set support of itemTransfer class
	 */
	public boolean supports(Class clazz) {
		return clazz.equals(ItemTransfer.class);
	}

	/**
	 * Method to validate editing of item
	 */
	public void validate(Object command, Errors error) {
		ItemTransfer item = (ItemTransfer) command;
		int sellerId = item.getSellerId();
		if(sellerId != user.getUserId()) {
			error.rejectValue(ITEM_FIELD, ITEM_ERROR, ITEM_ERROR_MESSAGE);
		}		
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
