package dao.interfaces;

import dao.transfer.DealHistoryTransfer;

/**
 * Interface of DAO deal history
 * @author Denis_Shipilov
 *
 */
public interface DAODealHistory {
	
	/**
	 * Method to set deal as held
	 * @param histTransfer {@link DealHistoryTransfer}
	 * @return inserted row count
	 */
	int setDealAsHeld(DealHistoryTransfer deal);
	
	/**
	 * Method to check if it's time up deal
	 * @param deal
	 * @return
	 */
	boolean isTimeUpDeal(DealHistoryTransfer deal);
	
	
	/**
	 * Method to check information about deal. 
	 * @param deal
	 * If this information exists
	 * @return true 
	 * else 
	 * @return false
	 */
	boolean isDealHeld(DealHistoryTransfer deal);
	
	/**
	 * Method to set deal as "time up deal"
	 * @return inserted row count
	 */
	int setDealAsTimeUp(DealHistoryTransfer deal);

}
