package dao.factories;

import dao.interfaces.DAOBids;

import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOCategory;
import dao.interfaces.DAODealHistory;
import dao.interfaces.DAOHistory;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOUsers;
import dao.oracle.DAOOracleFactory;


public abstract class DAOAbstractFactory {
	
	/**
	 * Oracle db type flag
	 */
	public static final int ORACLE = 1;

	/**
	 * Abstract method to return Bids as DAO object
	 * @return DAOBids
	 */
	public abstract DAOBids getDAOBids();

	/**
	 * Abstract method to return Users as DAO object
	 * @return instance of DAOUsers
	 */
	public abstract DAOUsers getDAOUsers();

	/**
	 * Abstract method to return Items as DAO object
	 * @return instance of DAOItems
	 */
	public abstract DAOItems getDAOItems();
	
	
	/**
	 * Abstract method to get dao category
	 * @return instance of {@link DAOCategory}
	 */
	public abstract DAOCategory getDAOCategory();
	
	
	/**
	 * Method to get DAOBlackList
	 * @return instance of {@link DAOBlackList}
	 */
	public abstract DAOBlackList getDAOBlackList();
	
	
	/**
	 * Method to get dao history object
	 * @return instance of {@link DAOHistory}
	 */
	public abstract DAOHistory	getDAOHistrory();
	
	
	/**
	 * Method to get dao deal history
	 * @return instance of {@link DAODealHistory}
	 */
	public abstract DAODealHistory getDAODealHistory();
	
	
	

	/**
	 * Method for returning some of DAO factory
	 */
	public static DAOAbstractFactory getDAOFactory(int dbType) {
		switch (dbType) {
		case ORACLE:
			return new DAOOracleFactory();
		default:
			return null;
		}

	}

}
