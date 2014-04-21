package dao.transfer;

/**
 * Class of block transfer object
 * @author Denis_Shipilov
 *
 */
public class BlockTransfer {
	
	
	private int blockId;
	private int userId;
	private int blockedUserId;
	private String blockedUserName;
	
	
	/**
	 * Constructor without blockId 
	 * Basically it used for creating block to set it in database
	 * @param userId
	 * @param blockedUserId
	 */
	public BlockTransfer(int userId, int blockedUserId) {
		super();
		this.userId = userId;
		this.blockedUserId = blockedUserId;
	}
	
	/**
	 * constructor of BlockTransfer with all fields
	 * @param blockId
	 * @param userId
	 * @param blockedUserId
	 */
	public BlockTransfer(int blockId, int userId, int blockedUserId,
			String blockedUserName) {
		super();
		this.blockId = blockId;
		this.userId = userId;
		this.blockedUserId = blockedUserId;
		this.blockedUserName = blockedUserName;
	}

	/**
	 * @return the blockId
	 */
	public int getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId the blockId to set
	 */
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the blockedUserId
	 */
	public int getBlockedUserId() {
		return blockedUserId;
	}

	/**
	 * @param blockedUserId the blockedUserId to set
	 */
	public void setBlockedUserId(int blockedUserId) {
		this.blockedUserId = blockedUserId;
	}

	/**
	 * @return the blockedUsername
	 */
	public String getBlockedUserName() {
		return blockedUserName;
	}

	/**
	 * @param blockedUsername the blockedUsername to set
	 */
	public void setBlockedUserName(String blockedUserName) {
		this.blockedUserName = blockedUserName;
	}
	
	

}
