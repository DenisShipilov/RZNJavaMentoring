package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOCategory;
import dao.transfer.CategoryTransfer;

public class OracleDAOCategory implements DAOCategory {

	/*
	 * Constant with name of generated columns of category
	 */
	private static final String[] GENERATED_COLUMNS = { "categoryId" };

	// SQL QUERY CONSTANTS

	/*
	 * Constant of sql insert category
	 */
	private static final String SQL_INSERT_CATEGORY = "INSERT INTO"
			+ " category VALUES(?,?)";

	/*
	 * Constant of SQL select all categories from database 
	 */
	private static final String SQL_SELECT_CATEGORY = 
											"SELECT * FROM category";
	
	/*
	 * Constant of find category by id
	 */
	private static final String SQL_FIND_CATEGORY_ID = 
					"SELECT * FROM category WHERE categoryId = ? ";
	
	
	

	// EXEPTION AND ERROR MESSAGES

	/*
	 * local exception constants
	 */
	private static final String CREATE_STATEMENT_EXPTN_MESSAGE = "Can't"
			+ "create statement";
	/*
	 * When can't create statement
	 */
	private static final String PREPARE_STATEMENT_EXPTN_MESSAGE = "Can't"
			+ " create prepared statement";
	/*
	 * When can't close statement
	 */
	private static final String CLOSE_STATEMENT_EXPTN = "Can't "
			+ "close statement";
	/*
	 * When can't execute sql command
	 */
	private static final String EXECUTE_COMMAND_EXCEPTION = "Can't"
			+ " execute sql command";
	/*
	 * When can't process the ResultSet object
	 */
	private static final String PROCESS_RS_EXCEPTION = "Unable to "
			+ "process the result set";
	/*
	 * When unable to close result set
	 */
	private static final String CLOSE_RS_EXPTN = "Unable to close"
			+ "result set";

	/*
	 * When unable to set prepared statement
	 */
	private static final String PREPARE_SET_EXCEPTION = "Can't "
			+ "set PreparedStatement";
	/*
	 * Close error message
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = ""
			+ "Can't close connection";

	/*
	 * Method to create connection
	 */
	private static Connection connect() {
		return DAOOracleFactory.createConnection();
	}

	/**
	 * Constructor of DAOItems class It immediately creates a database
	 * connection
	 */
	public OracleDAOCategory() {
		super();
	}

	/**
	 * {@inheritDoc DAOItems}
	 */
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(CLOSE_CONNECTION_ERROR_MESSAGE);
		}
	}

	public int insertCategory(CategoryTransfer category) {
		Connection conn = connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_INSERT_CATEGORY,
					GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setString(1, category.getCategory());
			prpstatmnt.setInt(2, category.getParentCategory());
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		// execute query and return
		int insertedRowCount = 0;
		try {
			insertedRowCount = prpstatmnt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
			;
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.getGeneratedKeys();
			rs.next();
			// set true key into item transfer
			category.setCategoryId(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;
	}

	public List<CategoryTransfer> selectCategories() {
		Connection conn = connect();
		List<CategoryTransfer> itTransList = new ArrayList<CategoryTransfer>();
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_SELECT_CATEGORY);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillCategoryTransferList(itTransList, rs);
		closeResultSetAndStatement(rs, statmnt);

		closeConnection(conn);
		return itTransList;
	}

	public CategoryTransfer selectCategoryById(int categoryId) {
		Connection conn = connect();
		// create list of transfer item
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_CATEGORY_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, categoryId);
		} catch (SQLException e1) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e1) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		CategoryTransfer category = null;
		try {
			if (rs.next()) {
				category = new CategoryTransfer(rs.getInt("categoryId"),
								rs.getString("category"), 
								rs.getInt("parentcategory"));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statment
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return category;
	}

	/*
	 * Method to close result set and statement
	 * 
	 * @param rs
	 * 
	 * @param st
	 */
	private void closeResultSetAndStatement(ResultSet rs, 
												Statement st) {
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
			PreparedStatement pst) {
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
	 * Method to fill List of CategoryTransfer
	 */
	private void fillCategoryTransferList(List<CategoryTransfer> 
						categoryList, ResultSet rs) {
		try {
			while (rs.next()) {
				categoryList.add(new CategoryTransfer(
						rs.getInt(1), rs.getString(2), 
						rs.getInt(3)));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
	}

}
