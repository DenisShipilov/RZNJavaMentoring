package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOBlackList;
import dao.transfer.BlockTransfer;


/**
 * Class to process blocks of user in data base
 * @author Denis_Shipilov
 *
 */
public class OracleDAOBlackList implements DAOBlackList {
	
	//
	//SQL constants
	//
	
	
	/*
	 * Constant with name of generated columns of item
	 */
	private static final String[] GENERATED_COLUMNS = {"blockId"};
	
	
	//
	// Message Constants
	//
	
	/*
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	
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
	 * Constant of SQL select data from blacklist table 
	 */
	private static final String SQL_FIND_BLOCK_ID = 
		"SELECT  bl.*, us.fullname userName  " +
		"FROM blackList" +
		" bl INNER JOIN users us ON us.userId = " +
		"bl.blockeduserid WHERE blockId = ?";
	
	
	/*
	 * Constant of SQL select all data from blacklist table
	 */
	private static final String SQL_SELECT_BLACKLIST = 
						"SELECT  bl.*, us.fullname userName  " +
						"FROM blackList" +
						" bl INNER JOIN users us ON us.userId = " +
						"bl.blockeduserid";
	
	/*
	 * Constant of SQL query to find blocks 
	 * by specified userId who set this block
	 */
	private static final String SQL_FIND_BLOCK_BY_USER = 
		"SELECT bl.*, us.fullname userName  from blackList" +
		" bl INNER JOIN users us ON us.userId = " +
		"bl.blockeduserid WHERE bl.userId = ?";


	/*
	 * Constant to insert block 
	 */
	private static final String SQL_INSERT_BLOCK = "INSERT INTO " +
					  "blackList VALUES (BLOCK_SEQ.nextval,?,?) ";
	
	
	/*
	 * Constant to delete block
	 */
	private static final String SQL_DELETE_BLOCK = "DELETE FROM " +
			"blackList WHERE userId = ? AND blockedUserId = ?";
	
	
	
	/*
	 * Method to create connection 
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
	

	public BlockTransfer findBlock(int blockId) {
		Connection conn = connect();
		// create list of transfer item
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_BLOCK_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, blockId);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		BlockTransfer blockTransfer = null;
		try {
			if (rs.next()) {
				blockTransfer = new BlockTransfer(
						rs.getInt("blockId"),
						rs.getInt("userIdId"),
						rs.getInt("blockedUserId"),
						rs.getString("username"));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return blockTransfer;
	}

	public List<BlockTransfer> findBlockedUsersForSpecifiedUser(
													int userId) {
		Connection conn = connect();
		// create list of transfer item
		List<BlockTransfer> blockTransferList = 
							new ArrayList<BlockTransfer>();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_BLOCK_BY_USER,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, userId);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		fillBlockTransferList(blockTransferList, rs);
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return blockTransferList;
	}

	public int setBlock(BlockTransfer block) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
									SQL_INSERT_BLOCK,GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setInt(1, block.getUserId());
			prpstatmnt.setInt(2, block.getBlockedUserId());
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
			block.setBlockId(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;
	}

	public int removeBlock(int userId, int blockedUserId) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
								SQL_DELETE_BLOCK, 
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, userId);
			prpstatmnt.setInt(2, blockedUserId);
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
	
	
	public List<BlockTransfer> selectBlackList() {
		Connection conn = connect();
		List<BlockTransfer> blockTransferList = 
			new ArrayList<BlockTransfer>();
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_SELECT_BLACKLIST);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillBlockTransferList(blockTransferList, rs);
		closeResultSetAndStatement(rs, statmnt);

		closeConnection(conn);
		return blockTransferList;
	}
	
	
	/*
	 * Method to fill list of bid transfer
	 */
	private void fillBlockTransferList(
			List<BlockTransfer> blockTransferList,	ResultSet rs){ 
		try {
			while (rs.next()) {
				blockTransferList.add(new BlockTransfer(
						rs.getInt("blockId"),
						rs.getInt("userId"),
						rs.getInt("blockedUserId"),
						rs.getString("userName")));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		
	}
}
