package helpers.interfaces;

import java.util.List;

import dao.transfer.HistoryTransfer;

/**
 * Interface of history helper 
 * @author Denis_Shipilov
 *
 */
public interface HistoryHelper {
	
	
	/**
	 * Method to set history to the request
	 * @param history
	 */
	void setHistoryToRequest(List<HistoryTransfer> history);

}
