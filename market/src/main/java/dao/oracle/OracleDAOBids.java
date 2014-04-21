package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOBids;
import dao.transfer.BidTransfer;



/**
 * Class to create DAOBid object for get information about 
 * @author Denis_Shipilov
 *
 */
public class OracleDAOBids implements DAOBids{

	/* 
	 * Constant of sql query to add bid on the lot
	 */
	private static final String SQL_INSERT_BID = 
									"INSERT INTO Bids VALUES" +
									"(BID_SEQ.nextval,?,?,?)";
	
	/*
	 * local exception constants
	 */
	private static final String CREATE_STATEMENT_EXPTN_MESSAGE = "Can't" +
											   		"create statement";
	/*
	 * When can't close statement
	 */
	private static final String CLOSE_STATEMENT_EXPTN = "Can't " +
													 "close statement";
	/*
	 * When can't execute sql command
	 */
	private static final String EXECUTE_COMMAND_EXCEPTION = "Can't" +
												 "execute sql command";
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
	 * Constant of find bid
	 */
	private static final String SQL_FIND_BID_BY_USERID = 
									"SELECT * FROM bids WHERE " +
									"bidderId = ?";
	
	/*
	 * Constant with name of generated columns of item
	 */
	private static final String[] GENERATED_COLUMNS = {"bidId"};
	
	/*
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	/*
	 *Close error message constant 
	 */
	private static final String CLOSE_STATEMENT_ERROR_MESSAGE = 
										"Can't close prepared statment";
	
	
	
	/**
	 * Constructor of DAO bid object
	 */
	public OracleDAOBids() {
		super();
	}

	/*
	 * Method to close connection with data base
	 * @throws SQLException
	 */
	private void closeConnection(Connection conn) {
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
	
	/*
	 *Method to create connection 
	 */
	private static Connection connect() {
		return DAOOracleFactory.createConnection();
	}
	

	/**
	 * {@inheritDoc DAOBids}
	 * @throws SQLException 
	 */
	public int insertBid(BidTransfer bidObject) {
		PreparedStatement prpstatmnt = null;
		int resultOfUpdate = 0;
		Connection conn = connect();
		try {
		prpstatmnt = conn.prepareStatement(
						SQL_INSERT_BID,GENERATED_COLUMNS);
			prpstatmnt.setInt(1, bidObject.getBidderId());
			prpstatmnt.setInt(2, bidObject.getItemId());
			prpstatmnt.setFloat(3, bidObject.getBid());	
			resultOfUpdate = prpstatmnt.executeUpdate();
			ResultSet rs = prpstatmnt.getGeneratedKeys();
			if(rs.next()) {
				bidObject.setBidId(rs.getInt(1));
			}
		} catch (SQLException e) {
			resultOfUpdate =  e.getErrorCode();
		} finally {
			try {
				prpstatmnt.close();
				closeConnection(conn);
			} catch (SQLException e) {
				System.out.println(CLOSE_STATEMENT_ERROR_MESSAGE);
			}
		}
		return resultOfUpdate;
	}

	public List<BidTransfer> findBidByuserId(int userId) {
		Connection conn = connect();
		List<BidTransfer> bids = new ArrayList<BidTransfer>();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(SQL_FIND_BID_BY_USERID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			pst.setInt(1, userId);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		//now get information from result set
		// close statement
		fillBidsList(bids, rs);
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return bids;
	}
	
	
	/*
	 * Method to fill List of itemTransfer
	 */
	private void fillBidsList(List<BidTransfer> bids,
			ResultSet rs) {
		try {
			while (rs.next()) {
						bids.add(new BidTransfer(rs.getInt("bidderId"),
						rs.getInt("itemId"), rs.getFloat("bid")));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
	}
	
	

}
