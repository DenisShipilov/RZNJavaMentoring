package dao.transfer;

/**
 * Class of history transfer object to hold history of bids for
 * specified item
 * @author Denis_Shipilov
 *
 */
public class HistoryTransfer {
	
	
	private int itemId;
	private String title;
	private int sellerId;
	private int bidderId;
	private String bidder;
	private int bidCount;
	
	
	/**
	 * constructor of history transfer
	 * @param itemId
	 * @param itemName
	 * @param bidder
	 */
	public HistoryTransfer(int itemId, String itemName,
			int sellerId,
			 int bidderId, String bidder,
			int bidCount) {
		super();
		this.itemId = itemId;
		this.title = itemName;
		this.sellerId = sellerId;
		this.bidderId = bidderId;
		this.bidder = bidder;
		this.bidCount = bidCount;
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
	 * @return the itemName
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param itemName the itemName to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the bidder
	 */
	public String getBidder() {
		return bidder;
	}


	/**
	 * @param bidder the bidder to set
	 */
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}


	/**
	 * @return the bidCount
	 */
	public int getBidCount() {
		return bidCount;
	}


	/**
	 * @param bidCount the bidCount to set
	 */
	public void setBidCount(int bidCount) {
		this.bidCount = bidCount;
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
	

}
