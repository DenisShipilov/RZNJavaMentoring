package dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAOUsers;
import dao.transfer.UserTransfer;

public class OracleDAOUsers implements DAOUsers{
	/*
	 * Constant of sql query to select all users from data base
	 */
	private static final String SQL_SELECT_USERS = 
							                   "SELECT * FROM Users";
	/*
	 * Constant of sql query to delete user from data base 
	 */
	private static final String SQL_DELETE_USER = "DELETE FROM Users " +
	  												"WHERE userId = ?";
	/*
	 * Constant of sql query to insert user in data base
	 */
	private static final String SQL_INSERT_USER ="INSERT INTO Users" +
							" VALUES(USERS_SEQ.nextval,?,?,?,?,?)";
	
	/*
	 * Constant of sql query to find user by fullname
	 */
	private static final String SQL_FIND_USER_BY_NAME = 
							"Select * FROM Users WHERE fullname = ?";
	
	private static final String SQL_FIND_USER_BY_ID = "Select * FROM Users WHERE userId = ?";
	
	/*
	 * Constant to find user by login and password
	 */
	private static final String SQL_FIND_USER = "Select * FROM Users " +
						"WHERE login = ? AND password = ?";
	
	
	/*
	 * Constant to find user by login and password
	 */
	private static final String SQL_CHECK_LOGIN = 
							"Select * FROM Users  WHERE " +
							"login = ?";
	
	
	/*
	 * local exception constants
	 */
	private static final String CREATE_STATEMENT_EXPTN_MESSAGE =
											"Can't create statement";
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
	 *Close error message 
	 */
	private static final String CLOSE_CONNECTION_ERROR_MESSAGE = "" +
											"Can't close connection";
	
	/*
	 * Constant with name of generated columns of item
	 */
	private static final String[] GENERATED_COLUMNS = {"userId"};
	


	
	/**
	 * Constructor of OracleDAOUsesr
	 */
	public OracleDAOUsers() {
		//initialize connection
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
	
	
	/**
	 * {@inheritDoc DAOUsers} 
	 */
	public int deleteUser(int userId) {
		Connection conn=connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
								SQL_DELETE_USER, 
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(PREPARE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, userId);
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

	public UserTransfer findUserId(int userId) {
		Connection conn = connect();
		// create list of transfer item
		
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_USER_BY_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setInt(1, userId);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = prpstatmnt.executeQuery();
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		UserTransfer user = null;
		try {
			if(rs.next()) {
				user = new UserTransfer(rs.getInt("userid"),
						rs.getString("fullName"),
						rs.getString("billingAdress"),
						rs.getString("email"),
						rs.getString("login"), 
						rs.getString("password")); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		// close statement
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return user;
	}

	
	public List<UserTransfer> findUserName(String fullName) {
		Connection conn = connect();
		// create list of transfer item
		List<UserTransfer> users = new ArrayList<UserTransfer>();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(SQL_FIND_USER_BY_NAME,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			prpstatmnt.setString(1, fullName);
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
		fillUsersTransferlist(users, rs) ;
		// close statement
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return users;
	}
	
	/**
	 * {@inheritDoc DAOUsers}
	 * @return
	 */
	public UserTransfer findUser(String login, String password) {
		Connection conn = connect();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(SQL_FIND_USER,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			pst.setString(1, login);
			pst.setString(2, password);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		UserTransfer user = null;
		try {
			if(rs.next()) {
				user = new UserTransfer(rs.getInt("userid"),
						rs.getString("fullName"),
						rs.getString("billingAdress"),
						rs.getString("email"),
						rs.getString("login"), 
						rs.getString("password")); 
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return user;
	}
	
	

	public int insertUser(UserTransfer user) {
		Connection conn = connect();
		PreparedStatement prpstatmnt = null;
		try {
			prpstatmnt = conn.prepareStatement(
									SQL_INSERT_USER,GENERATED_COLUMNS);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		// set statement
		try {
			prpstatmnt.setString(1, user.getUserFullName());
			prpstatmnt.setString(2, user.getBillingAddress());
			prpstatmnt.setString(3, user.getEmail());
			prpstatmnt.setString(4, user.getLogin());
			prpstatmnt.setString(5, user.getPassword());
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
			user.setUserId(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		} // get generated keys
		closeResultSetAndPrepareStatement(rs, prpstatmnt);
		closeConnection(conn);
		return insertedRowCount;
	}

	
	public List<UserTransfer> selectUsers() throws SQLException {
		Connection conn = connect();
		List<UserTransfer> users = new ArrayList<UserTransfer>();
		Statement statmnt = null;
		try {
			statmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		ResultSet rs = null;
		try {
			rs = statmnt.executeQuery(SQL_SELECT_USERS);
		} catch (SQLException e) {
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		// write item from result set into List of itemTransfer object
		fillUsersTransferlist(users,rs);
		closeResultSetAndStatement(rs, statmnt);

		closeConnection(conn);
		return users;
	}
	
	
	
	private void fillUsersTransferlist(List<UserTransfer> users,
														ResultSet rs) {
		try {
			while (rs.next()) {
				users.add(new UserTransfer(rs.getInt("userid"),
				rs.getString("userFullName"),
				rs.getString("billingAddress"),
				rs.getString("email"),
				rs.getString("login"), rs.getString("password")));
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		
	}

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

	public boolean testUserLogin(String login) {
		boolean result = false;
		Connection conn = connect();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(SQL_CHECK_LOGIN,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println(CREATE_STATEMENT_EXPTN_MESSAGE);
		}
		try {
			pst.setString(1, login);
		} catch (SQLException e) {
			System.out.println(PREPARE_SET_EXCEPTION);
		}
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(EXECUTE_COMMAND_EXCEPTION);
		}
		try {
			if(rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println(PROCESS_RS_EXCEPTION);
		}
		closeResultSetAndPrepareStatement(rs, pst);
		closeConnection(conn);
		return result;
	}

	

}
