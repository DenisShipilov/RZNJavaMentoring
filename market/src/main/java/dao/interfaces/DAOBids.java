package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dao.transfer.BidTransfer;

public interface DAOBids {
	
	
	
	/**
	 * Method to insert data into bids table. It's return the count 
	 * of processed row and sets true identifier 
	 * of bid in data base into bidTransfer object
	 * @param bidTransfer
	 * @return count of processed row
	 * @throws SQLException 
	 */
	int insertBid(BidTransfer bidTransfer);
	
	
	/**
	 * Method to find bid by user id
	 * @param userId
	 * @return
	 */
	List<BidTransfer> findBidByuserId(int userId);
}
