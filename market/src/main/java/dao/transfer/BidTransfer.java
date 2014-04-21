
package dao.transfer;
/**
 * Class of  'Bid'. It's used as  transfers object
 * @author Denis_Shipilov
 *
 */
public class BidTransfer {
	
	
	private int bidId;
	private int bidderId;
	private int itemId;
	private float bid;
	

	/**
	 * Constructor of Bid with all fields
	 * @param bidId
	 * @param bidderId
	 * @param itemId
	 * @param bid
	 */
	public BidTransfer(int bidId, int bidderId, int itemId, float bid) {
		super();
		this.bidId = bidId;
		this.bidderId = bidderId;
		this.itemId = itemId;
		this.bid = bid;
	}
	
	
	/**
	 * Constructor of Bid without bidId field
	 * @param bidderId
	 * @param itemId
	 * @param bid
	 */
	public BidTransfer(int bidderId, int itemId, float bid) {
		super();
		this.bidderId = bidderId;
		this.itemId = itemId;
		this.bid = bid;
	}
	
	
	/**
	 * @return the bidId
	 */
	public int getBidId() {
		return bidId;
	}
	/**
	 * @param bidId the bidId to set
	 */
	public void setBidId(int bidId) {
		this.bidId = bidId;
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
	 * @return the bid
	 */
	public float getBid() {
		return bid;
	}
	/**
	 * @param bid the bid to set
	 */
	public void setBid(float bid) {
		this.bid = bid;
	}

}
