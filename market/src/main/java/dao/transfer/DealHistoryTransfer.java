package dao.transfer;

/**
 * Class of message history transfer object
 * 
 * @author Denis_Shipilov
 * 
 */
public class DealHistoryTransfer {

	

	private int dealId;
	private int sellerId;
	private int bidderId;
	private int itemId;

	/**
	 * Constructor without history message id
	 * 
	 * @param sellerId
	 * @param bidderId
	 * @param itemId
	 */
	public DealHistoryTransfer(int sellerId, 
								  int bidderId, int itemId) {
		super();
		this.sellerId = sellerId;
		this.bidderId = bidderId;
		this.itemId = itemId;
	}

	/**
	 * Constructor of history message transfer
	 * @param historyId
	 * @param sellerId
	 * @param bidderId
	 * @param itemId
	 */
	public DealHistoryTransfer(int historyId,
								  int sellerId, int bidderId,
			int itemId) {
		super();
		this.dealId = historyId;
		this.sellerId = sellerId;
		this.bidderId = bidderId;
		this.itemId = itemId;
	}
	
	
	/**
	 * Constructor of deal history transfer
	 * use for setting  deal "time is up" 
	 * @param sellerId
	 * @param itemId
	 */
	public DealHistoryTransfer(int sellerId, int itemId) {
		super();
		this.sellerId = sellerId;
		this.itemId = itemId;
	}


	/**
	 * @return the sellerId
	 */
	public int getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the bidderId
	 */
	public int getBidderId() {
		return bidderId;
	}

	/**
	 * @param bidderId the bidderId to set
	 */
	public void setBidderId(int bidderId) {
		this.bidderId = bidderId;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the dealId
	 */
	public int getDealId() {
		return dealId;
	}

	/**
	 * @param dealId the dealId to set
	 */
	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

}
