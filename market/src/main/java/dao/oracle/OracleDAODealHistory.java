package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.interfaces.DAODealHistory;
import dao.transfer.DealHistoryTransfer;



/**
 * Class to get dao deal history 
 * @author Denis_Shipilov
 *
 */
public class OracleDAODealHistory implements DAODealHistory {
	
	
	/*
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	/*
	 * Constant with name of generated columns of item
	 */
	private static final String[] GENERATED_COLUMNS = {"dealId"};
	
	

	/*
	 * When can't create statement
	 */
	private static final String PREPARE_STATEMENT_EXPTN_MESSAGE = "Can't" +
									"create prepared statement";
	/*
	 * When can't close statement
	 */
	private static final String CLOSE_STATEMENT_EXPTN = "Can't " +
													 "close statement";
	/*
	 * When can't execute sql command
	 */
	private static final String EXECUTE_COMMAND_EXCEPTION = "Can't" +
												" execute sql command";
	/*
	 * When can't process the ResultSet object
	 */
	private static final String PROCESS_RS_EXCEPTION = "Unable to " +
												"process the result set";
	/*
	 * When unable to close result set
	 */
	private static final String CLOSE_RS_EXPTN = "Unable to close" +
															"result set";
	
	/*
	 * When unable to set prepared statement
	 */
	private static final String PREPARE_SET_EXCEPTION = "Can't " +
											"set PreparedStatement";
	

	/*
	 * Constant of select deal
	 */
	private static final String SQL_SELECT_DEAL_COUNT_BY_CONDITION = 
				"SELECT COUNT(*) FROM deals WHERE sellerId = ? AND " +
				"bidderId = ? AND itemId = ?";
	/*
	 * Constant of select time up deal
	 */
	private static final String 
					SQL_SELECT_TIME_UP_DEAL_COUNT_BY_CONDITION = 
		"SELECT COUNT(*) FROM deals WHERE sellerId = ? AND itemId = ?";
	
	
	
	/*
	 * Constant of insert deal
	 */
	private static final String SQL_INSERT_DEAL = "INSERT INTO " +
	"Deals VALUES(DEAL_SEQ.nextval,?,?,?)";
	
	/*
	 * Constant of SQL insert deal as time up
	 */
	private static final String SQL_INSERT_DEAL_AS_TIME_UP = 
					"INSERT INTO Deals(dealId,sellerId,itemId) " +
								"VALUES(DEAL_SEQ.nextval,?,?)";
	
	
	
	
	/*
	 *Method to create connection 
	 */
	private static Connection connect() {
		return DAOOracleFactory.createConnection();
	}

	
	
	/**
	 * {@inheritDoc DAOItems}
	 */
	public void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(CLOSE_CONNECTION_ERROR_MESSAGE);
		}		
	}
	
	/*
	 * Method to close ResultSet and PreparedStatement
	 */
	private void closeResultSetAndPrepareStatement(ResultSet rs,
											PreparedStatement pst){
		try {
			rs.close();
		} catch (SQLException e) {
			System.out.println(CLOSE_RS_EXPTN);
		}
		try {
			pst.close();
		} catch (SQLException e) {
			System.out.println(CLOSE_STATEMENT_EXPTN);
		}
	}
	
	public boolean isDealHeld(DealHistoryTransfer deal) {
		Connection conn = connect();
		boolean result = false;
		// create list of transfer item
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
					SQL_SELECT_DEAL_COUNT_BY_CONDITION,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, deal.getSellerId());
			prpstatmnt.setInt(2, deal.getBidderId());
			prpstatmnt.setInt(3, deal.getItemId());
			
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		int count = 0;
		try {
			if (rs.next()) {
				count = rs.getInt(1); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		if(count != 0) {
			result = true;
		}
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return result;
	}

	public int setDealAsHeld(DealHistoryTransfer deal) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
									SQL_INSERT_DEAL,GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setInt(1, deal.getSellerId());
			prpstatmnt.setInt(2, deal.getBidderId());
			prpstatmnt.setInt(3, deal.getItemId());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		// execute query and return
		int insertedRowCount = 0;
		try {
			insertedRowCount = prpstatmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(EXECUTE_COMMAND_EXCEPTION);;
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.getGeneratedKeys();
			rs.next();
			// set true key into item transfer
			deal.setDealId(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;

	}


	public int setDealAsTimeUp(DealHistoryTransfer deal) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
						SQL_INSERT_DEAL_AS_TIME_UP,
								GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setInt(1, deal.getSellerId());
			prpstatmnt.setInt(2, deal.getItemId());
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		// execute query and return
		int insertedRowCount = 0;
		try {
			insertedRowCount = prpstatmnt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);;
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.getGeneratedKeys();
			rs.next();
			// set true key into item transfer
			deal.setDealId(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;
	}



	public boolean isTimeUpDeal(DealHistoryTransfer deal) {
		Connection conn = connect();
		boolean result = false;
		// create list of transfer item
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
					SQL_SELECT_TIME_UP_DEAL_COUNT_BY_CONDITION,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, deal.getSellerId());
			prpstatmnt.setInt(2, deal.getItemId());
			
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		int count = 0;
		try {
			if (rs.next()) {
				count = rs.getInt(1); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		if(count != 0) {
			result = true;
		}
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return result;
	}

}
