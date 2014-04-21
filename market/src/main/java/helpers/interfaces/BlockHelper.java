package helpers.interfaces;

import java.util.List;

import dao.transfer.BlockTransfer;

/**
 * Interface of block helper
 * @author Denis_Shipilov
 *
 */
public interface BlockHelper {
	
	/**
	 * Method to set block transfer to request
	 * @param blockTransferList
	 */
	void setBlackListToRequest(List<BlockTransfer> 
									blockTransferList);
}
