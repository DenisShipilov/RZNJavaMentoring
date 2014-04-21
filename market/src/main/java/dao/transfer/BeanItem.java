package dao.transfer;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * Class to contain item as will be showing it on web page
 * 
 * @author Denis_Shipilov
 * 
 */
public class BeanItem implements Serializable {

	/**
	 * identifier of beanItems
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int itemId;
	private String title;
	private String description;
	private String category;
	private int sellerId;
	private String seller;
	private int startPrice;
	private int bidInc;
	private int bestOffer;
	private int bidderId;
	private String bidder;
	private Date startBiddingDate;
	private int timeLeft;
	private String buyItNow;

	/**
	 * 
	 * Constructor of bid items using all fields
	 * 
	 * @param itemId
	 * @param title
	 * @param description
	 * @param seller
	 * @param startPrice
	 * @param bidInc
	 * @param bestOffer
	 * @param bidder
	 * @param startBiddingDate
	 * @param timeLeft
	 * @param buyInNow
	 */
	public BeanItem(int itemId, String title, String description, 
			String category, int sellerId,
			String seller, int startPrice, int bidInc, int bestOffer,
			int bidderId,
			String bidder, Date startBiddingDate,int timeLeft,
			String buyItNow) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.sellerId = sellerId;
		this.seller = seller;
		this.startPrice = startPrice;
		this.bidInc = bidInc;
		this.bestOffer = bestOffer;
		this.bidderId = bidderId;
		this.bidder = bidder;
		this.startBiddingDate = startBiddingDate;
		this.timeLeft = timeLeft;
		this.buyItNow = buyItNow;
	}

	/**
	 * 
	 * Constructor of bid without best offer,
	 * bid increment, stop date, and bidder
	 * 
	 * @param itemId
	 * @param title
	 * @param description
	 * @param seller
	 * @param startPrice
	 * @param buyInNow
	 */
	public BeanItem(int itemId, String title, String description,
			String category, int sellerId,
			String seller, int startPrice,String buyItNow) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.sellerId = sellerId;
		this.seller = seller;
		this.startPrice = startPrice;
		bidder = null;
		startBiddingDate = null;
		this.buyItNow = buyItNow;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the seller
	 */
	public String getSeller() {
		return seller;
	}

	/**
	 * @param seller
	 *            the seller to set
	 */
	public void setSeller(String seller) {
		this.seller = seller;
	}

	/**
	 * @return the startPrice
	 */
	public int getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice
	 *            the startPrice to set
	 */
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the bidInc
	 */
	public int getBidInc() {
		return bidInc;
	}

	/**
	 * @param bidInc
	 *            the bidInc to set
	 */
	public void setBidInc(int bidInc) {
		this.bidInc = bidInc;
	}

	/**
	 * @return the bestOffer
	 */
	public int getBestOffer() {
		return bestOffer;
	}

	/**
	 * @param bestOffer
	 *            the bestOffer to set
	 */
	public void setBestOffer(int bestOffer) {
		this.bestOffer = bestOffer;
	}

	/**
	 * @return the bidder
	 */
	public String getBidder() {
		return bidder == null ? "" : bidder;
	}

	/**
	 * @param bidder
	 *            the bidder to set
	 */
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}

	/**
	 * @return the stopDate
	 */
	public Date getStopDate() {
		Date stopDate = null;
		if(timeLeft != 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(startBiddingDate.getTime());
			cal.add(Calendar.DAY_OF_YEAR, timeLeft/24);
			stopDate = new Date(cal.getTimeInMillis());
		}
		return stopDate;
	}
	

	/*
	 * Method to get stop date in millisecinds 
	 */
	public long getMillisecondStopDate(){
		long result = 0;
		if(timeLeft != 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(startBiddingDate.getTime());
			cal.add(Calendar.DAY_OF_YEAR, timeLeft/24);
			result = cal.getTimeInMillis();
		}
		return result;
		
	}

	/**
	 * @return the buyItNow
	 */
	public String getBuyItNow() {
		return buyItNow;
	}

	/**
	 * @param buyItNow the buyItNow to set
	 */
	public void setBuyItNow(String buyItNow) {
		this.buyItNow = buyItNow;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the startBiddingDate
	 */
	public Date getStartBiddingDate() {
		return startBiddingDate;
	}

	/**
	 * @param startBiddingDate the startBiddingDate to set
	 */
	public void setStartBiddingDate(Date startBiddingDate) {
		this.startBiddingDate = startBiddingDate;
	}

	/**
	 * @return the timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @param timeLeft the timeLeft to set
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
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

	
	 
}
