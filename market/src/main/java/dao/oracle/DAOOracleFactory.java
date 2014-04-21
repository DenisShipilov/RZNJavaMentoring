package dao.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBids;
import dao.interfaces.DAOBlackList;
import dao.interfaces.DAOCategory;
import dao.interfaces.DAODealHistory;
import dao.interfaces.DAOHistory;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOUsers;

public class DAOOracleFactory extends DAOAbstractFactory {
	
	/*
	 * initialize context constant
	 */
	private static final String INIT_CONTEXT_PROPERTY = 
											"jdbc/marketPlace";
	
	/*
	 * Initialize context error
	 */
	private static final String INIT_CONTEXT_ERROR = 
					"Can't initialize context";

    private static final String CAN_T_CREATE_CONNECTION =
            "Can't create connection";
    private static final String CAN_T_CREATE_CONNECTION_POOL =
            "Can't create connection pool";
    // initialization data for connection
    private static String urlOracle =
            "dbc:oracle:thin:@127.0.0.1:1521:xe";
    private static String usrOracle = "marketplace";
    private static String passOracle = "marketplace";

	// data source for connection
	private static DataSource ds;
    //datasource to create connection
    private static OracleDataSource connDs;


    /*
	 * Method to initialization pool of connection
	 */
	private static void initializePoole() {
		// create pool

		Context initContext;
		try {
		initContext = new InitialContext();
		ds = (DataSource)initContext.lookup(INIT_CONTEXT_PROPERTY);
		} catch (NamingException e) {
			System.out.println(INIT_CONTEXT_ERROR);
            try {
                connDs = new OracleDataSource();
                connDs.setURL(urlOracle);
                connDs.setUser(usrOracle);
                connDs.setPassword(passOracle);
                connDs.setConnectionCachingEnabled(true);
                ds = connDs;
            } catch (SQLException e1) {
                e.printStackTrace();
                System.out.println(CAN_T_CREATE_CONNECTION_POOL);
            }
		}

	}

	/**
	 * Method for creating connection to oracle data base
	 * 
	 * @return Connection
	 */
	public static synchronized Connection createConnection() {
		Connection conn = null;
		try {
			if (ds == null) {
				Locale.setDefault(Locale.ENGLISH); // for connecting
				initializePoole();
			}
			// try to get new connection from pool
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	/**
	 * Method to return oracleDAOBids object
	 */
	public DAOBids getDAOBids() {
		return new OracleDAOBids();
	}

	@Override
	/**
	 * Method to return oracleDAOitems object
	 */
	public DAOItems getDAOItems() {
		return new OracleDAOItems();
	}

	@Override
	/**
	 * Method to return oracleDAOUsers object
	 */
	public DAOUsers getDAOUsers() {
		return new OracleDAOUsers();
	}

	@Override
	/**
	 * Method to return oracleDAOCategory
	 */
	public DAOCategory getDAOCategory() {
		return new OracleDAOCategory();
	}

	@Override
	public DAOBlackList getDAOBlackList() {
		return new OracleDAOBlackList();
	}

	@Override
	public DAOHistory getDAOHistrory() {
		return new OracleDAOHistory();
	}

	@Override
	public DAODealHistory getDAODealHistory() {
		return new OracleDAODealHistory();
	}

}
