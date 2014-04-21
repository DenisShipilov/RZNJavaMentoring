package dao.oracle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;


import dao.factories.DAOAbstractFactory;
import dao.interfaces.DAOItems;
import dao.transfer.ItemTransfer;
import junit.framework.TestCase;

public class OracleDAOItemsTest extends TestCase {
	
	private static final String INSERT_ITEM_DESCRIPTION = 
											"DH Nukeproof d900";
	private static final String INSERT_ITEM_TITLE = "Bicycle";
	private static final String INSERT_ITEM_DATA = "16-09-2012";
	private static final float START_ITEM_PRICE = 2000;
	private static final float BID_INCREMENT = 40;
	private static final int TIME_LEFT = 300;
 	
	
	private static final String BID_INCREMENT_PARAMETER = 
														"bidIncrement";
	private static final String BUY_IT_NOW = "buyItNow";
	private static final String START_BIDDING_DATE = "startBiddingDate";
	private static final String TIME_LEFT_PARAMETER = "timeLeft";
	private static final String START_PRICE = "startPrice";
	private static final String DESCRIPTION_PARAMETER = "description";
	private static final String TITLE_PARAMETER = "title";
	private static final String SELLER_ID = "sellerId";
	private static final String ITEM_ID = "itemId";
	/*Message constants*/
	private static final String SELECT_MESSAGE = "Rowcount in local " +
						"result set and list of items must be equals";	
	private static final String DELETE_MESSAGE = "The number of rows " +
							 "should be less after the removal of line";
	private static final String DESCRIPTION = "Nukeproof";
	private static final String TITLE = INSERT_ITEM_TITLE;
	private static final String FIND_DESCRIPTION_MESSAGE = 
						"Count of Row in local result set must" +
						" be equals size of returned list ";
	private static final String FIND_ID_MESSAGE = 
			"Id in result set and in returned object must be equals";
	
	
	
	/*
	 * Constant of sql query to select all items  
	 */
	private static final String SQL_SELECT_ITEMS = "SELECT * FROM Items";
	
	/*
	 *  Constant of sql query to insert item  
	 */
	private static final String SQL_INSERT_ITEM = "INSERT INTO " +
					"Items VALUES(ITEM_SEQ.nextval,?,?,?,?,?,?,?,?)";
	
	
	/*
	 * Constant of sql query to find item(s) by title 
	 */
	private static final String SQL_FIND_ITEM_TITLE =
								"SELECT * FROM Items WHERE title " +
								"LIKE '%' || ? || '%'";
	/*
	 * Constant of sql query to find item(s) by description 
	 */
	private static final String SQL_FIND_ITEM_DESCRIPTION = 
						"SELECT * FROM Items WHERE description " +
									 		"LIKE '%' || ? || '%'";


	/* 
	 * Constant of sql query to find item(s) by id
	 */
	private static final String SQL_FIND_ITEM_ID = 
						"SELECT * FROM Items WHERE itemId = ?";
	
	/* 
	 * Constant of sql query to delete item 
	 */
	private static final String SQL_DELETE_ITEM = "DELETE FROM Items " +
												  "WHERE itemid = ?";
	/*
	 * String to set generated columns
	 */
	private static final String[] GENERATED_COLUMNS = {ITEM_ID};
	
	// in this tests it's OracleDAOItem object
    private DAOItems daoItems;
    //local test connection
    private Connection conn;
    //local result set
    private  ResultSet rs; 
    //local statement
    private Statement statmnt;
    //local prepare statement
    private PreparedStatement prpst;
    
	/**
	 * Constructor of test case for oracle items
	 * @param name
	 */
	public OracleDAOItemsTest(String name) {
		super(name);
	}
	
	/*
	 * Override method setUp to create connection to Oracle 
	 * and OracleDAOItem using OracleDAoFactory
	 */
	@Override
	protected void setUp() throws Exception {
		//creating local connection
		DAOAbstractFactory factory = 
					DAOAbstractFactory.getDAOFactory(1);
	    daoItems = factory.getDAOItems();
	    conn =  DAOOracleFactory.createConnection();	    
		super.setUp();
	}
	
	/*
	 * Override method tearDown to close connection
	 */
	@Override
	protected void tearDown() throws Exception {
		rs.close(); // close used result set
		conn.close(); // close local connection
		super.tearDown();
	}
	
	
	/**
	 * Test for selecting of items
	 * @throws SQLException 
	 */
	public void testSelectItems() throws SQLException {
		statmnt = conn.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
        rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
        rs.last();
		int rows = rs.getRow(); // get last row
		rs.beforeFirst(); // go at original position
		//create list of items
		List<ItemTransfer> itemsList = daoItems.selectItems();
		assertEquals(SELECT_MESSAGE,rows, itemsList.size());
		statmnt.close();
	}
	
	/**
	 * Test for check deleting item from items table
	 * @throws SQLException 
	 * @throws ParseException 
	 */
 	public void testDeleteItem() throws SQLException {
		// insert item
		prpst = conn.prepareStatement(SQL_INSERT_ITEM,
											GENERATED_COLUMNS);
		// set statment
		prpst.setInt(1, 1);
		prpst.setString(2, INSERT_ITEM_TITLE);
		prpst.setString(3, INSERT_ITEM_DESCRIPTION);
		prpst.setInt(4, 4900);
		prpst.setInt(5, 0);
		prpst.setDate(6,Date.valueOf("16-06-2015"));
		prpst.setString(7, "1");
		prpst.setInt(8, 40);
		// execute query and return
		prpst.executeUpdate();
		rs = prpst.getGeneratedKeys();
		rs.next();
		int generatedId = rs.getInt(1); // now i know generated id
		rs.close();
		prpst.close(); // close prepared statement 
		//create statement for select row count(only one time!)
		statmnt = conn.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
		rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
		// calculate row count before deleting
		rs.last();
		int rowsBefore = rs.getRow(); // get row count before
		rs.close();
		// try to delete item
		daoItems.deleteItem(generatedId);
		// calculate row count after deleting
		rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
		rs.last();
		int rowsAfter = rs.getRow();// get row count after
			assertFalse(DELETE_MESSAGE, (rowsBefore == rowsAfter));
		rs.close();
		statmnt.close();
	}
	
	/**
	 * The test to check found items in table by description
	 * @throws SQLException
	 */
	public void testFindItemDescription() throws SQLException {
		prpst = conn.prepareStatement(SQL_FIND_ITEM_DESCRIPTION,
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY);
		prpst.setString(1, DESCRIPTION);
		rs = prpst.executeQuery();
		rs.last();
		int rows = rs.getRow(); // get row count
	
		List<ItemTransfer> itemsList = 
							daoItems.findItemDescription(DESCRIPTION);
		assertTrue(FIND_DESCRIPTION_MESSAGE, (rows == itemsList.size()));
		rs.close();
		prpst.close();
	}
	
	/**
	 * The test to check found items in table by title
	 */
	public void testFindItemTitle() throws SQLException {
		prpst = conn.prepareStatement(SQL_FIND_ITEM_TITLE,
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY);
		prpst.setString(1, TITLE);
		rs = prpst.executeQuery();
		rs.last();
		int rows = rs.getRow(); // get row count
		List<ItemTransfer> itemsList = daoItems.findItemTitle(TITLE);
		assertTrue(FIND_DESCRIPTION_MESSAGE, (rows == itemsList.size()));
		rs.close();
		prpst.close();
	}
	
	
	/**
	 * The test to check found items in table by id
	 * @throws SQLException
	 * @throws SQLException
	 */
	public void testFindItemId() throws SQLException {
		int compareId = 2;
		ItemTransfer itemsList = daoItems.findItemId(compareId);
		
		prpst = conn.prepareStatement(SQL_FIND_ITEM_ID,
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY);
		prpst.setInt(1, compareId);
		rs = prpst.executeQuery();
		// if data exists
		if ((rs.next()) && (itemsList != null)) {
			// get data from result set
			ItemTransfer itemTransfer = 
					new ItemTransfer(
					rs.getInt(ITEM_ID),
					rs.getInt(SELLER_ID), 
					rs.getString(TITLE_PARAMETER), rs
					.getString(DESCRIPTION_PARAMETER),
					rs.getFloat(START_PRICE),
					rs.getFloat(TIME_LEFT_PARAMETER),
					rs.getFloat(BID_INCREMENT_PARAMETER));
			//now we need to insert right startBinding 
			//date and buyItNow flag
			setStartDataAndBuyItNow(itemTransfer, 
					rs.getByte(BUY_IT_NOW),
					rs.getDate(START_BIDDING_DATE).getTime());
			assertTrue(FIND_ID_MESSAGE, 
						(itemsList.equals(itemTransfer)));
		}
		rs.close();
		prpst.close();

	}
	
	
	/*
	 * Method to separately
	 *  set the startBindingDate and buyItNow flag
	 */
	private void setStartDataAndBuyItNow(ItemTransfer item, 
			 						byte buyNow, long time) {
		java.util.Date transDat = new java.util.Date(time);
		boolean buyItNow = false;
		if (buyNow == 1) {
			buyItNow = true;
		}
		item.setBuyItNow(buyItNow);
		item.setStartBiddingDate(transDat);
	}
	
	
	
	/**
	 * The test to check inserted item in data base 
	 * @throws SQLException
	 */
	public void testInsertItem() throws SQLException {
		statmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
									   ResultSet.CONCUR_READ_ONLY);
		rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
		rs.last();
		int rowsBefore = rs.getRow(); // get last row
		rs.close();//
		// create item transfer object for insert
		ItemTransfer itemTransferInsert = 
			new ItemTransfer(1,INSERT_ITEM_TITLE,
						INSERT_ITEM_DESCRIPTION,
                        0,
						START_ITEM_PRICE,TIME_LEFT, 
						Date.valueOf(INSERT_ITEM_DATA),true,
						BID_INCREMENT);
		// try to insert data into items
		daoItems.insertItem(itemTransferInsert);
		// select new rowcount
		rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
		rs.last();
		int rowsAfter = rs.getRow(); // get last row
		// compare inserted item with last selected item
		assertTrue(FIND_ID_MESSAGE, (rowsBefore < rowsAfter));
		// now delete inserted item
		prpst = conn.prepareStatement(SQL_DELETE_ITEM,
							ResultSet.TYPE_SCROLL_INSENSITIVE, 
							ResultSet.CONCUR_UPDATABLE);
		prpst.setInt(1, itemTransferInsert.getItemId());
		prpst.executeUpdate();
		prpst.close();
	}
}
