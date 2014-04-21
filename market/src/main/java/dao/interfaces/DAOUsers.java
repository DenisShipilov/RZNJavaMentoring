package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dao.transfer.UserTransfer;

public interface DAOUsers {
	/**
	 * Method to select all users from table
	 * @return list of {@link UserTransfer} objects
	 * @throws SQLException
	 */
	List<UserTransfer> selectUsers() throws SQLException;
	/**
	 * method to insert users in data base
	 * @return count of processed string
	 */
	int insertUser(UserTransfer user);
	
	/**
	 * Method to delete user from data base
	 * @return count of processed string
	 */
	int deleteUser(int userId);
	
	/**
	 * Method to find user by full name
	 * @return list of {@link UserTransfer} objects
	 */
	List<UserTransfer> findUserName(String fullName);
	
	/**
	 * Method to find user by id
	 * @return {@link UserTransfer} object
	 */
	UserTransfer findUserId(int userId);
	
	/**
	 * Method to find user by login and password
	 * @param login
	 * @param password
	 * @return {@link UserTransfer} object
	 */
	UserTransfer findUser(String login, String password);
	
	
	/**
	 * Method for testing user login
	 * @param login
	 * @return true if user exists and false other way
	 */
	boolean testUserLogin(String login);

}
