package dao.interfaces;

import java.util.List;

import dao.transfer.BlockTransfer;

/**
 * Interface of DAO blackList
 * @author Denis_Shipilov
 *
 */
public interface DAOBlackList {
	
	/**
	 * Method to set block in database
	 * @param user
	 * @param blockedUser
	 * @return
	 */
	int setBlock(BlockTransfer block);
	
	
	/**
	 * Method to find block by id
	 * @param blockId
	 * @return {@link BlockTransfer} object
	 */
	BlockTransfer findBlock(int blockId);
	
	
	/**
	 * Method to find blocked users for specified user
	 * @param userId
	 * @return {@link List} of {@link BlockTransfer} object
	 */
	List<BlockTransfer> findBlockedUsersForSpecifiedUser(int userId);
	
	
	/**
	 * Method to remove block from user
	 * @param user
	 * @param blockedUser
	 * @return count of processed rows
	 */
	int removeBlock(int user, int blockedUser);
	
	/**
	 * Method to select full black list
	 * @return {@link List} of {@link BlockTransfer}
	 */
	List<BlockTransfer> selectBlackList();
 	
	

}
