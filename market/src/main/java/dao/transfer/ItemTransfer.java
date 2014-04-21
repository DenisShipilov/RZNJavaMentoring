package dao.transfer;

import java.io.Serializable;
import java.util.Date;

/**
 * Class of item transfer object
 * @author Denis_Shipilov
 *
 */
public class ItemTransfer implements Serializable {	

	
	
	private static final int MINUTES_IN_HOUR = 60;



	/**
	 * ID if item transfer
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private int category;
	private int itemId;
	private int sellerId;
	private String title;
	private String description;
	private float startPrice;
	private float timeleft;
	private Date startBiddingDate;
	private boolean buyItNow;
	private float bidIncrement;
	
	
	/**
	 * Constructor of ItemTransfer without parameters
     * @param anInt
     * @param rsInt
     * @param string
     * @param rsString
     * @param aFloat
     * @param rsFloat
     * @param v
     */
	public ItemTransfer(int anInt, int rsInt, String string, String rsString, float aFloat, float rsFloat, float v) {
		super();
	}
	
	/**
	 * Constructor of item transfer object 
	 * without itemId field
	 * @param sellerId
	 * @param title
	 * @param description
	 * @param startPrice
	 * @param timeleft
	 * @param startBiddingDate
	 * @param byItNow
	 * @param bidIncrement
	 */
	public ItemTransfer(int sellerId, String title, String description,
			int category, float startPrice, float timeleft,
			Date startBiddingDate,
			boolean  buyItNow, float bidIncrement) {
		super();
		this.sellerId = sellerId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.startPrice = startPrice;
		this.timeleft = timeleft;
		this.startBiddingDate = startBiddingDate;
		this.buyItNow = buyItNow;
		this.bidIncrement = bidIncrement;
	}
	
	
	
	/**
	 * 
	 * Constructor of Item using all fields
	 * @param itemId
	 * @param sellerId
	 * @param title
	 * @param description
	 * @param startPrice
	 * @param timeleft
	 * @param startBindingDate
	 * @param byItNow
	 * @param bidIncrement
	 */
	public ItemTransfer(int itemId, int sellerId, String title,
			String description, int category,
			float startPrice, float timeleft,
			Date startBindingDate, boolean buyItNow, float bidIncrement) {
		super();
		this.itemId = itemId;
		this.sellerId = sellerId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.startPrice = startPrice;
		this.timeleft = timeleft;
		this.startBiddingDate = startBindingDate;
		this.buyItNow = buyItNow;
		this.bidIncrement = bidIncrement;
	}
	
	
	/** 
	 * Constructor of item transfer without 
	 * startBindingDate as {@link Date}
	 * and buyItNow {@link Boolean} cos in oracle it's other 
	 * formats
	 * @param itemId
	 * @param sellerId
	 * @param title
	 * @param description
	 * @param startPrice
	 * @param timeleft
	 * @param bidIncrement
	 */
	public ItemTransfer(int itemId, int sellerId, String title,
			String description, int category, float startPrice, float timeleft,
			float bidIncrement) {
		super();
		this.itemId = itemId;
		this.sellerId = sellerId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.startPrice = startPrice;
		this.timeleft = timeleft;
		this.bidIncrement = bidIncrement;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the startPrice
	 */
	public float getStartPrice() {
		return startPrice;
	}
	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}
	/**
	 * @return the timeleft
	 */
	public float getTimeLeft() {
		return timeleft;
	}
	/**
	 * @param timeleft the timeleft to set
	 */
	public void setTimeLeft(float timeleft) {
		this.timeleft = timeleft;
	}
	
	/**
	 * Set time left if parameter is divide by ":" character 
	 * @param timeLeft
	 */
	public void setTimeLeftStr(String timeString) {
		this.timeleft = 0;
		if(!timeString.equals("")){
		String delims = "[:]";
		String[] times = timeString.split(delims);
		this.timeleft = Integer.valueOf(times[0]) + 
						(Float.valueOf(times[1])/60);
		}
				
	}
	
	public String getTimeLeftStr() {
		int hour = (int)timeleft;
		String minuteZero = "";
		int minutes = (int) Math.floor((timeleft - hour)*MINUTES_IN_HOUR);
		//set zero to time left str
		if(minutes < 10) {
			minuteZero = "0";
		}
		return hour + ":" + minuteZero + minutes;
		
	}
	/**
	 * @return the startBindingDate
	 */
	public Date getStartBiddingDate() {
		return startBiddingDate;
	}
	/**
	 * @param startBindingDate the startBindingDate to set
	 */
	public void setStartBiddingDate(Date startBindingDate) {
		this.startBiddingDate = startBindingDate;
	}
	/**
	 * @return the byItNow
	 */
	public boolean getBuyItNow() {
		return buyItNow;
	}
	/**
	 * @param byItNow the byItNow to set
	 */
	public void setBuyItNow(boolean byItNow) {
		this.buyItNow = byItNow;
	}
	/**
	 * @return the bidIncrement
	 */
	public float getBidIncrement() {
		return bidIncrement;
	}
	/**
	 * @param bidIncrement the bidIncrement to set
	 */
	public void setBidIncrement(float bidIncrement) {
		this.bidIncrement = bidIncrement;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

}
