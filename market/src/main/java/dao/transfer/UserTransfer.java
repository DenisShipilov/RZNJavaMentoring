package dao.transfer;

import java.io.Serializable;

/**
 * Class of User transfer object
 * @author Denis_Shipilov
 *
 */
public class UserTransfer implements Serializable {
		

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String userFullName;
	private String billingAddress;
	private String email;
	private String login;
	private String password;
	
	
	/**
	 * Constructor of user transfer without fields
	 */
	public UserTransfer() {
		super();
	}
	
	
	
	/**
	 * Constructor with only login and password.
	 * Basically it used for Guest User
	 * @param login
	 * @param password
	 */
	public UserTransfer(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	
	/**
	 * Constructor of user transfer object
	 * @param userId
	 * @param userFullName
	 * @param billingAddress
	 * @param login
	 * @param password
	 */
	public UserTransfer(int userId, String userFullName,
			String billingAddress, String email,
			String login, String password) {
		super();
		this.userId = userId;
		this.userFullName = userFullName;
		this.billingAddress = billingAddress;
		this.email = email;
		this.login = login;
		this.password = password;
	}
	
	/**
	 *  Constructor of user transfer without 
	 *  userId 
	 * @param userFullName
	 * @param billingAddress
	 * @param email
	 * @param login
	 * @param password
	 */
	public UserTransfer(String userFullName, String billingAddress,
			String email, String login, String password) {
		super();
		this.userFullName = userFullName;
		this.billingAddress = billingAddress;
		this.email = email;
		this.login = login;
		this.password = password;
	}
	
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the userFullName
	 */
	public String getUserFullName() {
		return userFullName;
	}
	/**
	 * @param userFullName the userFullName to set
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	/**
	 * @return the billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((billingAddress == null) ? 0 : billingAddress.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userFullName == null) ? 0 : userFullName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTransfer other = (UserTransfer) obj;
		if (billingAddress == null) {
			if (other.billingAddress != null)
				return false;
		} else if (!billingAddress.equals(other.billingAddress))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userFullName == null) {
			if (other.userFullName != null)
				return false;
		} else if (!userFullName.equals(other.userFullName))
			return false;
		return true;
	}

	public String toString() {
		return  String.valueOf(getUserId());
		
	}
	
	
	public void clearUser(){
		userId = 0;
		userFullName = null;
		billingAddress = null;
		email = null;
		login = null;
		password = null;		
	}
	
}
