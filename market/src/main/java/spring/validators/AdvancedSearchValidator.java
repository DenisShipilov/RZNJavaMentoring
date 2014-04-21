package spring.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import dao.transfer.SearchConditionTransfer;


/**
 * Class to validate advanced search of items
 * @author Denis_Shipilov
 *
 */
public class AdvancedSearchValidator implements Validator {
	
	
	private static final String EXPIRE_DATE_FIELD = "expireDateStr";
	private static final String START_DATE_FIELD = "startDateStr";
	
	private static final String START_DATE_ERROR = 
										"startDate.errorStartDate";
	private static final String EXPIRE_DATE_ERROR = 
										"expireDate.errorExpireDate";
	
	private static final String EXP_DATE_ERROR_MESSAGE = "Expire date " +
													"has wrong value ";
	private static final String START_DATE_ERROR_MESSAGE = "Start date " +
														"has wrong value ";
	
	/**
	 * I don't know what this method did
	 */
	public boolean supports(Class clazz) {
		return clazz.equals(SearchConditionTransfer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(Object command, Errors error) {
		SearchConditionTransfer condition = 
				(SearchConditionTransfer) command;
		if(condition.isExpireDateExp()) {
			error.rejectValue(EXPIRE_DATE_FIELD,EXPIRE_DATE_ERROR,
											EXP_DATE_ERROR_MESSAGE);			
		}
		if(condition.isStartDateExp()) {
			error.rejectValue(START_DATE_FIELD, START_DATE_ERROR,
										START_DATE_ERROR_MESSAGE);
		}

	}

}
