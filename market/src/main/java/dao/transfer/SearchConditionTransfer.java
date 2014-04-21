package dao.transfer;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Class of search condition object
 * used for advanced search
 * @author Denis_Shipilov
 *
 */
public class SearchConditionTransfer implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	
	private Integer itemId;
	private String title;
	private String description;
	private Float minPrice;
	private Float maxPrice;
	private boolean buyItNow;
	private Date startDate;
	private Date expireDate;
	private Integer bidderCount;
	
	private boolean expireDateExp;
	private boolean startDateExp;
	

	
		
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	 * @return the minPrice
	 */
	public Float getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public Float getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * @return the buyItNow
	 */
	public boolean isBuyItNow() {
		return buyItNow;
	}
	/**
	 * @param buyItNow the buyItNow to set
	 */
	public void setBuyItNow(boolean buyItNow) {
		this.buyItNow = buyItNow;
	}

	/**
	 * @return the expireBiddingDate
	 */
	public String getExpireDateStr() {
		expireDateExp = false;
		return getDateAsString(expireDate);
	}
	
	
	/**
	 * @return the startBiddingDate
	 */
	public String getStartDateStr() {
		startDateExp = false;
		return getDateAsString(startDate);
	}
	
	
	/**
	 * Method to
	 * @return expireDate as {@link Date}
	 */
	public Date getExpireDate() {
		return expireDate;	
	}
	
	
	/**
	 * Method to
	 * @return startDate as {@link Date}
	 */
	public Date getStartDate() {
		return startDate;	
	}
	
		
	/**
	 * Method to return date as string in special format 
	 * @param date
	 * @return
	 */
	private String getDateAsString(Date date) {
		Calendar cal = Calendar.getInstance();
		StringBuilder builder = new StringBuilder();
		if(date != null) {
		cal.setTime(date);
		builder.append(cal.get(Calendar.MONTH) + 1);
		builder.append("/");
		builder.append(cal.get(Calendar.DAY_OF_MONTH));
		builder.append("/");
		builder.append(cal.get(Calendar.YEAR));
		builder.append(" ");
		builder.append(cal.get(Calendar.HOUR_OF_DAY));
		builder.append(":");
		builder.append(cal.get(Calendar.MINUTE));
		} 
		return builder.toString();
	}
	
	/**
	 * @param expireBiddingDate the expireBiddingDate to set
	 */
	public void setExpireDateStr(String date) {
		try {
			this.expireDate = parseDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			expireDateExp = true;
		}
	}
	
	/**
	 * @param startBiddingDate the startBiddingDate to set
	 */
	public void setStartDateStr(String date) {
		try {
			this.startDate = parseDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			startDateExp = true;
		}
	}
	
	
	
	/*
	 * Method to parse string to date by pattern "MM/DD/YYYY HH:MM"
	 */
	private Date parseDate(String stringDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setLenient(false);
		Date date = null;
		if (!stringDate.equals("")) {
		date = (Date) formatter.parse(stringDate);
		}
		return date;
	}
	
	
	
	/**
	 * @return the bidderCount
	 */
	public Integer getBidderCount() {
		return bidderCount;
	}
	
	
	
	/**
	 * @param bidderCount the bidderCount to set
	 */
	public void setBidderCount(Integer bidderCount) {
		this.bidderCount = bidderCount;
	}
	
	
	/**
	 * @return the expireDateExp
	 */
	public boolean isExpireDateExp() {
		return expireDateExp;
	}
	
	
	/**
	 * @return the startDateExp
	 */
	public boolean isStartDateExp() {
		return startDateExp;
	}
	

	
}
