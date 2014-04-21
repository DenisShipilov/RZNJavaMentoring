package spring.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dao.transfer.UserTransfer;

public class SetBlockValidator implements Validator {


	public boolean supports(Class clazz) {
		return clazz.equals(UserTransfer.class);
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

}
