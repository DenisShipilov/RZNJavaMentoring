package dao.oracle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOItems;
import dao.transfer.BeanItem;
import dao.transfer.ItemTransfer;
import dao.transfer.SearchConditionTransfer;

/**
 * Class to get information from Items table
 * @author Denis_Shipilov
 *
 */
public class OracleDAOItems implements DAOItems {
	
	
	private static final String BUY_IT_NOW_FLAG = "1";

	private static final String QUERRY_UNION_AND = "AND ";

	/*Result set parameter constants*/

	private static final String CATEGORY = "category";

	private static final String BID_INCREMENT = "bidIncrement";

	private static final String BUY_IT_NOW = "buyItNow";

	private static final String START_BIDDING_DATE = "startBiddingDate";

	private static final String TIME_LEFT = "timeLeft";

	private static final String START_PRICE = "startPrice";

	private static final String DESCRIPTION = "description";

	private static final String TITLE_PARAMETER = "title";

	private static final String SELLER_ID = "sellerId";

	private static final String ITEM_ID = "itemId";
	

	
	
	/*
	 * Constant of SQL query to select all bean items
	 */
	private static final String SQL_SELECT_ALL_BEAN_ITEMS = 
							"SELECT * FROM beanItems";
	
	/*
	 * Constant of SQL query to select count of items
	 */
	private static final String SQL_ITEMS_COUNT_BY_CONDITION = 
		"SELECT COUNT(*) FROM beanItems WHERE ";
	
	/*
	 * Constant of SQL query to select bean items
	 * by condition
	 */
	private static final String SQL_ITEMS_CONDITION = 
		"SELECT * from ( " +
		"SELECT p.*, rownum rnum " +
		"FROM (SELECT * FROM beanItems WHERE  ) p " +
		"WHERE rownum  < ? ) WHERE rnum >= ?";
	
	
	/*
	 * Constant of SQL querry for Advanced search items
	 * for this querry we need to know bidderCount position and
	 * baseSearch position in this querry
	 */
	private static final String SQL_ADVANCED_SEARCH = "SELECT * FROM (" + 
    "SELECT p.*, rownum rnum FROM" +
    "(SELECT DISTINCT bi.* ,COUNT(b.bidderid) " +
    "OVER (PARTITION BY bi.\"itemId\") " +
    "AS \"bidderCount\" from beanitems bi " +
    "LEFT JOIN BIDS b ON bi.\"itemId\"=b.itemID WHERE 1 = 1   )p  " +
    "WHERE rownum  < ?  ) WHERE rnum >= ?";
	
	//position conditions "bidderCount"
	private static final int BIDDER_COUNT_POSITION_CONDITIONS = 228;
	//position conditions "baseCondition"
	private static final int BASE_CONDITIONS_POSITION = 204;
	
		
		
		
	
	
	/*
	 * Constant of position condition insert
	 */
	private static final Integer POSITION_OF_CONDITION_INSERT = 76;
	
	
	/*
	 * Constant of SQL query to select count of items
	 */
	private static final String SQL_ITEMS_COUNT = "SELECT COUNT(*) " +
			"FROM beanItems ";
	
	
	/* 
	 * Constant of sql query to select all items  
	 */
	private static final String SQL_SELECT_ITEMS = "SELECT * FROM Items";
	
	
	/*
	 * Constant of sql query to select bean items
	 */
	private static final String SQL_SELECT_BEAN_ITEMS = 
		"SELECT * FROM ( " +
        "SELECT p.*, rownum rnum " +
        "FROM (SELECT * FROM beanItems ORDER BY   ) p " +
        "WHERE rownum  < ? ) WHERE rnum >= ?";
	/*
	 * constant condition of sorting position in request
	 */
	private static final Integer 
						POSITION_OF_SORTING_CONDITION = 79;
	
	/*
	 * Constant of sql query to select bean items for a given user 
	 * as seller
	 */
	private static final String SQL_SELECT_MY_BEAN_ITEMS =
		"SELECT * from ( " +
		"SELECT p.*, rownum rnum " +
		"FROM (SELECT * FROM ( " +
		"SELECT * FROM beanItems bn  WHERE \"sellerId\" = ? " +
		"UNION " +
		"SELECT * FROM beanitems bk WHERE \"itemId\" IN ( "+
		"SELECT itemId FROM bids WHERE bidderId = ?)) " +
		"ORDER BY   ) p " + 
		"WHERE rownum  < ? ) WHERE rnum >= ?";
	
	/*
	 * constant condition of sorting position in request
	 */
	private static final Integer 
						POSITION_OF_MY_BEAN_SORTING_CONDITION = 218;
	
	/*
	 * Constant of sql querry to update item
	 */
	private static final String SQL_UPDATE_ITEM_BY_ID = "UPDATE items " +
		"SET sellerId = ?, title = ?, description = ?, category = ?,"+
			" startPrice = ?, timeLeft = ?, startBiddingDate = ?," +
			" buyItNow = ?, bidIncrement = ? WHERE itemId = ?";
	
	
	/*
	 * Constant of sql query to select bean Items by itemsId
	 */
	private static final String SQL_SELECT_BEANITEM_BY_ID = 
		"SELECT * FROM beanItems WHERE \"itemId\" = ?";
	
	
	
	
	/* 
	 * Constant of sql query to delete item 
	 */
	private static final String SQL_DELETE_ITEM = "DELETE FROM Items " +
												  "WHERE itemid = ?";
	/*
	 *  Constant of sql query to insert item  
	 *  with autogenerated id
	 */
	private static final String SQL_INSERT_ITEM = "INSERT INTO " +
						"Items VALUES(ITEM_SEQ.nextval,?,?,?,?,?,?,?,?,?)";
	
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
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	/*
	 * Constant with name of generated columns of item
	 */
	private static final String[] GENERATED_COLUMNS = {"itemId"};
	
	
	/*
	 * local exception constants
	 */
	private static final String CREATE_STATEMENT_EXPTN_MESSAGE = "Can't" +
											   		"create statement";
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
	 *  Constructor of DAOItems class
	 *  It immediately creates a database connection
	 */
	public OracleDAOItems() {
		super();
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
	 * Method to close result set and statement 
	 * @param rs
	 * @param st
	 */
	private void closeResultSetAndStatement(ResultSet rs, Statement st){
		try {
			rs.close();
		} catch (SQLException e) {
		System.out.println(CLOSE_RS_EXPTN);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(CLOSE_STATEMENT_EXPTN);
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

	/**
	 * {@inheritDoc DAOItems}
	 */
	public List<ItemTransfer> selectItems() {
		Connection conn = connect();
		List<ItemTransfer> itTransList = new ArrayList<ItemTransfer>();
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_SELECT_ITEMS);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillItemTransferList(itTransList, rs);
		closeResultSetAndStatement(rs, statmnt);

		closeConnection(conn);
		return itTransList;
	}

	/**
	 * * {@inheritDoc DAOItems}
	 */
	public int deleteItem(int itemId) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
								SQL_DELETE_ITEM, 
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
		int result = 0;
			try {
				result = prpstatmnt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(EXECUTE_COMMAND_EXCEPTION);
			}
			try {
				prpstatmnt.close();
			} catch (SQLException e) {
				System.out.println(CLOSE_STATEMENT_EXPTN);
			}
		closeConnection(conn);
		return result;
	}

	/**
	 * {@inheritDoc DAOItems}
	 */
	public List<ItemTransfer> findItemDescription(String itemDescription) {
		Connection conn = connect();
		// create list of transfer item
		List<ItemTransfer> itemTransferList = new ArrayList<ItemTransfer>();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_ITEM_DESCRIPTION,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setString(1, itemDescription);
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
		fillItemTransferList(itemTransferList, rs); 
		// close statement
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return itemTransferList;
	}


	/**
	 * {@inheritDoc DAOItems}
	 */
	public List<ItemTransfer> findItemTitle(String itemTitle) {
		Connection conn = connect();
		// create list of transfer item
		List<ItemTransfer> itemTransferList = new ArrayList<ItemTransfer>();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_ITEM_TITLE,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setString(1, itemTitle);
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
		fillItemTransferList(itemTransferList, rs); 
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return itemTransferList;
	}
	
	
	
	
	
	
	
	
	/**
	 * {@inheritDoc DAOItems}
	 */
	public ItemTransfer findItemId(int itemId) {
		Connection conn = connect();
		// create list of transfer item
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_ITEM_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, itemId);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		ItemTransfer itemTransfer = null;
		try {
			if (rs.next()) {
				itemTransfer = new ItemTransfer(
						rs.getInt(ITEM_ID),
						rs.getInt(SELLER_ID), 
						rs.getString(TITLE_PARAMETER),
						rs.getString(DESCRIPTION),
						rs.getInt(CATEGORY),
						rs.getFloat(START_PRICE),
						rs.getFloat(TIME_LEFT), 
						rs.getFloat(BID_INCREMENT));
				
				// now set start bidding date and 
				//buyItNow flag
				setStartDataAndBuyItNow(itemTransfer,
						rs.getByte(BUY_IT_NOW),
						rs.getDate(START_BIDDING_DATE).getTime());
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return itemTransfer;
	}
	
	

	
	
	/*
	 * Method to fill List of itemTransfer
	 */
	private void fillItemTransferList(List<ItemTransfer> itemTransferList,
			ResultSet rs) {
		try {
			while (rs.next()) {
				ItemTransfer item = new ItemTransfer(rs.getInt(ITEM_ID),
						rs.getInt(SELLER_ID), 
						rs.getString(TITLE_PARAMETER),
						rs.getString(DESCRIPTION),
						rs.getInt(CATEGORY),
						rs.getFloat(START_PRICE),
						rs.getFloat(TIME_LEFT), 
						rs.getFloat(BID_INCREMENT));
				//set right startBiddingDate and buyItNowFlag
				setStartDataAndBuyItNow(item,
						rs.getByte(BUY_IT_NOW),
						rs.getDate(START_BIDDING_DATE).getTime());
				itemTransferList.add(item);
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
	}


	/**
	 * {@inheritDoc DAOItems}
	 */
	public int insertItem(ItemTransfer itemTransfer) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
									SQL_INSERT_ITEM,GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setInt(1, itemTransfer.getSellerId());
			prpstatmnt.setString(2, itemTransfer.getTitle());
			prpstatmnt.setString(3, itemTransfer.getDescription());
			prpstatmnt.setInt(4, itemTransfer.getCategory());
			prpstatmnt.setFloat(5, itemTransfer.getStartPrice());
			prpstatmnt.setFloat(6, itemTransfer.getTimeLeft());
			//set date in sql format
			Date dat = new Date(itemTransfer.getStartBiddingDate().
														getTime());
			prpstatmnt.setDate(7,dat);
			//set buyItNow in oracle (number(7)) format
			byte buyItNow = 0;
			if(itemTransfer.getBuyItNow()) {
				buyItNow = 1;
			}
			prpstatmnt.setByte(8, buyItNow);
			prpstatmnt.setFloat(9, itemTransfer.getBidIncrement());
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
			itemTransfer.setItemId(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;
	}



	/**
	 * {@inheritDoc DAOItems}
	 */
	public List<BeanItem> selectBeanItems(
			String columnName,String sortCrit,
			int pageNumber, int maximumItemsOnPage) {
		StringBuilder sqlQueryString = 
			new StringBuilder(SQL_SELECT_BEAN_ITEMS);
		String condition = "\"" + columnName + "\" " +
									sortCrit;
			sqlQueryString.insert(POSITION_OF_SORTING_CONDITION,
														condition);
		Connection conn = connect();
		List<BeanItem> itTransList = new ArrayList<BeanItem>();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sqlQueryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		// getting max page and min page
		int minItem = (pageNumber - 1) * maximumItemsOnPage;
		int maxItem = pageNumber * maximumItemsOnPage;
		try {
			pst.setInt(1, maxItem);
			pst.setInt(2, minItem);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillBeanItemList(rs, itTransList);
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return itTransList;
	}
	
	
	
	/*
	 * Method to select my bean items
	 * (non-Javadoc)
	 * @see dao.interfaces.DAOItems#
	 * selectMyBeanItems(int, int, int, java.lang.String, java.lang.String)
	 */
	public List<BeanItem> selectMyBeanItems(
						int userId, int pageNumber, 
						int maximumItemsOnPage,
						String columnName,String sortCrit) {
		Connection conn = connect();
		List<BeanItem> items = new ArrayList<BeanItem>();
		//creating sql select of my bean items using 
		// sorting condition
		StringBuilder sqlQueryString = 
			new StringBuilder(SQL_SELECT_MY_BEAN_ITEMS);
		String condition = "\"" + columnName + "\" " +
									sortCrit;
			sqlQueryString.insert(POSITION_OF_MY_BEAN_SORTING_CONDITION,
														condition);
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sqlQueryString.toString());
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		// getting max page and min page
		int minItem = (pageNumber - 1) * maximumItemsOnPage;
		int maxItem = pageNumber * maximumItemsOnPage;
		try {
			pst.setInt(1, userId);
			pst.setInt(2, userId);
			pst.setInt(3, maxItem);
			pst.setInt(4, minItem);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		//fill the list of item
		fillBeanItemList(rs, items);
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return items;
		
	}
	
	/*
	 * Method to fill beanItem list
	 */
	private void fillBeanItemList(ResultSet rs, List<BeanItem> items){
		try {
			while (rs.next()) {
					items.add(new BeanItem(
							rs.getInt(1),
							rs.getString(2), 
							rs.getString(3),
							rs.getString(4),
							rs.getInt(5),
							rs.getString(6), 
							rs.getInt(7), 
							rs.getInt(8),rs.getInt(9),
							rs.getInt(10),
							rs.getString(11),
							rs.getDate(12), 
							rs.getInt(13),
							rs.getString(14)));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}	
	}




	public BeanItem selectBeanItemById(int itemId) {
		// create list of transfer item
		Connection conn = connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
					SQL_SELECT_BEANITEM_BY_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, itemId);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		BeanItem bean = null;
		try {
			if (rs.next()) {
				bean = new BeanItem(rs.getInt(1),
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6), 
						rs.getInt(7), 
						rs.getInt(8),rs.getInt(9),
						rs.getInt(10),
						rs.getString(11),
						rs.getDate(12), 
						rs.getInt(13),
						rs.getString(14));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return bean;
	}




	public int updateItem(ItemTransfer item) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
								SQL_UPDATE_ITEM_BY_ID, 
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, item.getSellerId());
			prpstatmnt.setString(2, item.getTitle());
			prpstatmnt.setString(3, item.getDescription());
			prpstatmnt.setInt(4, item.getCategory());
			prpstatmnt.setFloat(5, item.getStartPrice());
			prpstatmnt.setFloat(6, item.getTimeLeft());
			//set date in sql format
			Date dat = new Date(item.getStartBiddingDate().
														getTime());
			prpstatmnt.setDate(7,dat);
			//set buyItNow in oracle (number(7)) format
			byte buyItNow = 0;
			if(item.getBuyItNow()) {
				buyItNow = 1;
			}
			prpstatmnt.setByte(8, buyItNow);
			prpstatmnt.setFloat(9, item.getBidIncrement());
			prpstatmnt.setInt(10, item.getItemId());
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		int result = 0;
			try {
				result = prpstatmnt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(EXECUTE_COMMAND_EXCEPTION);
			}
			try {
				prpstatmnt.close();
			} catch (SQLException e) {
				System.out.println(CLOSE_STATEMENT_EXPTN);
			}
		closeConnection(conn);
		return result;
	}




	public int selectItemCount() {
		Connection conn = connect();
		int result = 0;
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_ITEMS_COUNT);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		try {
			if (rs.next()) {
					result = rs.getInt(1); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		closeResultSetAndStatement(rs, statmnt);

		closeConnection(conn);
		return result;
	}




	public int selectItemsCountByCondition(String columnName,
											String columnValue) {
		StringBuilder sqlQueryString = 
				new StringBuilder(SQL_ITEMS_COUNT_BY_CONDITION);
		sqlQueryString.append("\"" + columnName + "\"" +
				"= '" + columnValue + "' ");
		
		Connection conn = connect();
		int result = 0;
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(sqlQueryString.toString());
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		try {
			if (rs.next()) {
					result = rs.getInt(1); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		closeResultSetAndStatement(rs, statmnt);
		closeConnection(conn);
		return result;
	}




	public List<BeanItem> selectBeanItemByCondition(
			String columnName, String columnValue,
			String sortingColumnName, String sortCrit,			 
			int pageNumber, int maximumItemsOnPage) {
		StringBuilder sqlQueryString = 
			new StringBuilder(SQL_ITEMS_CONDITION);
		String condition = "\"" + columnName + "\"" +
							" = '" + columnValue + "'"
							+ " ORDER BY  \"" + 
							sortingColumnName + "\" " + 
							sortCrit;
			sqlQueryString.insert(POSITION_OF_CONDITION_INSERT,
														condition);
			Connection conn = connect();
			List<BeanItem> itTransList = new ArrayList<BeanItem>();
			PreparedStatement pst = null;
			try {
				pst = conn.prepareStatement(sqlQueryString.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			} catch (SQLException e) {
				System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
			}
			// getting max page and min page
			int minItem = (pageNumber - 1) * maximumItemsOnPage;
			int maxItem = pageNumber * maximumItemsOnPage;
			try {
				pst.setInt(1, maxItem);
				pst.setInt(2, minItem);
			} catch (SQLException e1) {
				System.out.println(PREPARE_SET_EXCEPTION);
			}
			ResultSet rs = null;
			try {
				rs = pst.executeQuery();
			} catch (SQLException e) {
				System.out.println(EXECUTE_COMMAND_EXCEPTION);
			}
			// write item from result set into List of itemTransfer object
			fillBeanItemList(rs, itTransList);
			closeResultSetAndPrepareStatement(rs, pst);
			closeConnection(conn);
			return itTransList;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<BeanItem> advancedSerchItem(
			String sortingColumnName, String sortCrit,			 
			int pageNumber, int maximumItemsOnPage,
			SearchConditionTransfer condition) {
		//variable to increment position when set prepared statement
		int posIncrement = 0;
		int strtDatPos = 0;
		int expDatPos = 0;
		
		String orderConditionString =  " ORDER BY  \"" + 
		sortingColumnName + "\" " + 
		sortCrit;
		//creating string builder
		StringBuilder sqlQuerry = 
							new StringBuilder(SQL_ADVANCED_SEARCH);
		StringBuilder sqlQuerryString = 
							new StringBuilder();
		
			
		
		//first check bidderCount condition
		if(condition.getBidderCount() != null){
			//append bidderCount condition in base query
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(condition("bidderCount", 
					condition.getBidderCount().toString()));
			sqlQuerry.insert(BIDDER_COUNT_POSITION_CONDITIONS,
						sqlQuerryString.toString());
			//now clear sqlQuerryString
			sqlQuerryString.setLength(0);
		}
		
		
		
		//if we have item id
		if(condition.getItemId() != null) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(condition(ITEM_ID, 
					String.valueOf(condition.getItemId())));
		} 
		//if we have title
		if((condition.getTitle() != null) &&
				 (!condition.getTitle().equals(""))){
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(condition("title", 
										condition.getTitle()));
		}
		//if we have description
		if((condition.getDescription() != null) && 
					(!condition.getDescription().equals(""))) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(condition(DESCRIPTION, 
								condition.getDescription()));
		}
		//if we have max price
		if((condition.getMaxPrice() != null) && 
				(condition.getMinPrice() == null)) {
			if(condition.getMaxPrice() != 0) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append("\"" + START_PRICE + "\"" +
					   " <= " + condition.getMaxPrice()  + " ");
		}
			
		}
		//if we have min price
		if((condition.getMaxPrice() == null) && 
				(condition.getMinPrice() != null)){
			if(condition.getMinPrice() != 0) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append("\"" + START_PRICE + "\"" +
			 			  " >= " +condition.getMinPrice() + " ");
			}
			
		}
		//if we have both price
		if((condition.getMaxPrice() != null) && 
				(condition.getMinPrice() != null)){
			if((condition.getMaxPrice() != 0) &&
					(condition.getMinPrice() != 0)){
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append("\"" + START_PRICE + "\"" +
									" BETWEEN " + 
									condition.getMinPrice() + 
						QUERRY_UNION_AND + condition.getMaxPrice());
			}
			
		}
		
		//if we have buy it now flag
		if(condition.isBuyItNow()) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(condition(BUY_IT_NOW, BUY_IT_NOW_FLAG));
		}
		
		//if we have start date
		if(condition.getStartDate() != null) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(" \"startBiddingDate\" = ?");
			strtDatPos = 1;
			posIncrement++;
		}
		
		//if we have expire date
		if(condition.getExpireDate() != null) {
			sqlQuerryString.append(QUERRY_UNION_AND);
			sqlQuerryString.append(" (\"startBiddingDate\" " +
								"+ (\"timeleft\"/24)) = ?");
			if(strtDatPos == 1){
				expDatPos = 2;
			}else {
				expDatPos = 1;
			}
			posIncrement++;
		}
		
				
		//now add base search in sql querry if it is
		if(sqlQuerryString.length() > 0) {
			//first append order condition in querryString
			sqlQuerryString.append(orderConditionString);
			sqlQuerry.insert(BASE_CONDITIONS_POSITION,
								sqlQuerryString.toString());
		}
		
		
	
		
		Connection conn = connect();
		List<BeanItem> itTransList = new ArrayList<BeanItem>();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sqlQuerry.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		// getting max page and min page
		int minItem = (pageNumber - 1) * maximumItemsOnPage;
		int maxItem = pageNumber * maximumItemsOnPage;
		try {
			if(condition.getStartDate() != null){
				pst.setDate(strtDatPos, 
						new Date(condition.getStartDate().getTime()));
			}
			if(condition.getExpireDate() != null){
				pst.setDate(expDatPos, 
						new Date(condition.getExpireDate().getTime()));
			}
			pst.setInt(1 + posIncrement, maxItem);
			pst.setInt(2 + posIncrement, minItem);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillBeanItemList(rs, itTransList);
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return itTransList;
	}
	
	
	private String condition(String columnName, String columnValue) {
		 return "\"" + columnName + "\"" +
		 		" = '" + columnValue + "'" + " ";
	}
	




	/*
	 * Method to select all Bean items 
	 * (non-Javadoc)
	 * @see dao.interfaces.DAOItems#selectAllBeanItems()
	 */
	public List<BeanItem> selectAllBeanItems() {
		Connection conn = connect();
		List<BeanItem> items = new ArrayList<BeanItem>();
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_SELECT_ALL_BEAN_ITEMS);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		fillBeanItemList(rs, items);
		closeResultSetAndStatement(rs, statmnt);
		closeConnection(conn);
		return items;
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
	
	
}