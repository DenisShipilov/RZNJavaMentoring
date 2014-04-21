package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOBids;
import dao.transfer.BidTransfer;
import junit.framework.TestCase;

public class OracleDAOBidsTest extends TestCase {
	
	//Constants of mistakes of insert
	private static int SELF_INSERT = 2301;
	private static int LITTLE_BID= 2302;
	private static int BUY_IT_NOW_EXCEPTION= 2303;
	private static int TIME_LEFT_EXCEPTION= 2304;
	
	
	
	/*Constant to select all bids from */
	private static final String SQL_SELECT_COUNT = 
									"SELECT * FROM Bids";
		
	/*
	 * Constant of sql query to delete bid
	 */
	private static final String SQL_DELETE_BID = "DELETE FROM Bids " +
												    "WHERE bidId = ?";
	
    //Message constant
	private static final String INSERT_MESSAGE = "Count of row before " +
							        "insert and after must be different";
	private static final String SELF_INSERT_MESSAGE = "counts of row" +
							" after selfInsertException must be equas ";
	private static final String LITTLE_BID_MESSAGE = "counts of row " +
										"after little bid must be equals";	
	private static final String BUY_IT_NOW_MESSAGE = "When flag BuyItNow" +
				 " exist tren row count before and after must be equals";
	private static final String TIME_LEFT_MESSAGE = "When trades " +
			"completed row count before and after must be equals";
	
	
	
	// in this tests it's OracleDAOItem object
    private DAOBids daoBids;
    //local test connection
    private Connection conn;
    //local result set
    private  ResultSet rs; 
    //local statement
    private Statement stmt;
    //local prepare statement
    private PreparedStatement pstmt;
    
    private BidTransfer bidTransfer;
    int rowsBefore;

	/**
	 * Constructor of OracleDAOBidsTest
	 * @param name
	 */
    public OracleDAOBidsTest(String name) {
		super(name);
	}

	/*
	 * Override method setUp to create connection to Oracle 
	 * and OracleDAOItem using OracleDAoFactory
	 */
	@Override
	protected void setUp() throws Exception {
		// creating local connection
		conn = DAOOracleFactory.createConnection();
		DAOAbstractFactory factory = DAOAbstractFactory.getDAOFactory(1);
		daoBids = factory.getDAOBids();
		//create statement to check row count before insert 
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		// check count of row before insert
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		rowsBefore = rs.getRow(); // get last row
		rs.close();
		super.setUp();
	}
	
	/*
	 * Override method tearDown to close connection
	 */
	@Override
	protected void tearDown() throws Exception {
		rs.close(); // close used result set
		conn.close(); // close connection
		super.tearDown();
	}
	
	/**
	 * The test to check inserted data into table bids
	 * @throws SQLException 
	 */
	public void testInsertBid() throws SQLException {
		// create new bids object
		bidTransfer = new BidTransfer(1, 7, 2050);
		// try to insert bid
		int resultOfUpdate = daoBids.insertBid(bidTransfer);
		// check rowcount after insert
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		int rowsAfter = rs.getRow();
		// check bid inserting
		assertFalse(INSERT_MESSAGE, ((rowsAfter == rowsBefore)
										 || (resultOfUpdate != 1)));
		// now delete inserted bid
		pstmt = conn.prepareStatement(SQL_DELETE_BID);
		pstmt.setInt(1, bidTransfer.getBidId());
		pstmt.executeUpdate();
		// close statements
		pstmt.close();
		stmt.close(); //close statement
	}
	
	/**
	 * The test to check self insert
	 * @throws SQLException 
	 */
	public void testSelfInsert() throws SQLException {
		// create new bids object
		bidTransfer = new BidTransfer(1, 2, 600);
		// try to insert bid
		int resultOfUpdate = daoBids.insertBid(bidTransfer);
		// check rowcount after insert
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		int rowsAfter = rs.getRow();
		//when self insert
		assertFalse(SELF_INSERT_MESSAGE, (rowsAfter > rowsBefore) || 
									(resultOfUpdate != SELF_INSERT));
	}
	
	/**
	 * The test to check when bid is too small
	 * 
	 * @throws SQLException
	 */
	public void testLittleBid() throws SQLException {
		// create new bids object
		bidTransfer = new BidTransfer(2, 8, 10);
		// try to insert bid
		int resultOfUpdate = daoBids.insertBid(bidTransfer);
		// check rowcount after insert
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		int rowsAfter = rs.getRow();
		// check result of update
		// when to little bid
		assertFalse(LITTLE_BID_MESSAGE, (rowsAfter > rowsBefore)||
									(resultOfUpdate != LITTLE_BID));
	}
	
	/**
	 * The test to check when try to insert bid when trades completed
	 * 
	 * @throws SQLException
	 */
	public void testTradesCompleted() throws SQLException {
		// create new bids object
		bidTransfer = new BidTransfer(2, 9, 50);
		// try to insert bid
		int resultOfUpdate = daoBids.insertBid(bidTransfer);
		// check rowcount after insert
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		int rowsAfter = rs.getRow();
		// check result of update
		// when to little bid
		assertFalse(TIME_LEFT_MESSAGE, (rowsAfter > rowsBefore)
				|| (resultOfUpdate != TIME_LEFT_EXCEPTION));
	}
	
	/**
	 * The test to check when bids is non available
	 * @throws SQLException 
	 */
	public void testBuyItNow() throws SQLException {
		// create new bids object
		bidTransfer = new BidTransfer(1, 4, 50);
		// try to insert bid
		int resultOfUpdate = daoBids.insertBid(bidTransfer);
		// check rowcount after insert
		rs = stmt.executeQuery(SQL_SELECT_COUNT);
		rs.last();
		int rowsAfter = rs.getRow();
		// check result of update
		// when to little bid
		assertFalse(BUY_IT_NOW_MESSAGE, (rowsAfter > rowsBefore)
				|| (resultOfUpdate != BUY_IT_NOW_EXCEPTION));		
	}
	
}
