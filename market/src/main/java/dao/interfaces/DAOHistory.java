package dao.interfaces;

import java.util.List;

import dao.transfer.HistoryTransfer;

/**
 * Interface for class which must get daoHistory
 * @author Denis_Shipilov
 *
 */
public interface DAOHistory {
	
	/**
	 * Method to get history of bids for specified item
	 * @return {@link List} of {@link HistoryTransfer} objects
	 */
	List<HistoryTransfer> getHistoryOfBidForItem(int itemId);

}
