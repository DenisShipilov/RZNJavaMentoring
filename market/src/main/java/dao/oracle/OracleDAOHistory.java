package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOHistory;
import dao.transfer.HistoryTransfer;

/**
 * Class to get history of item bids from data base
 * @author Denis_Shipilov
 *
 */
public class OracleDAOHistory implements DAOHistory {
	
	
	//
	//SQL constants
	//
	
	/*
	 * Constant of SQL query to select history of bid from item
	 */
	private static final String SQL_FIND_HISTORY_OF_ITEM =
		"SELECT bd.itemid itemId, it.title title, it.sellerId sellerId," +
		"bd.bidderid bidderid," +
		" us.fullName bidder, " +
		"bd.bid bidCount FROM bids bd " +
		"INNER JOIN users us ON us.userId = bd.bidderId " +
		"INNER JOIN items it ON it.itemid = bd.itemid " +
		"WHERE itemId = ?";
	
	
	
	//
	// Message Constants
	//
	
	/*
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	
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


	public List<HistoryTransfer> getHistoryOfBidForItem(int itemId) {
		Connection conn = connect();
		// create list of transfer item
		List<HistoryTransfer> historyTransferList = 
									new ArrayList<HistoryTransfer>();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_HISTORY_OF_ITEM,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, itemId);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// get data from result set
		fillHistoryTransferList(historyTransferList, rs);
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return historyTransferList;
	}
	
	
	
	private void fillHistoryTransferList(
			List<HistoryTransfer> historyTransferLits,	ResultSet rs){ 
		try {
			while (rs.next()) {
				historyTransferLits.add(new HistoryTransfer(
						rs.getInt("itemId"),
						rs.getString("title"),
						rs.getInt("sellerId"),
						rs.getInt("bidderId"),
						rs.getString("bidder"),
						rs.getInt("bidCount")));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		
		
	}

}
