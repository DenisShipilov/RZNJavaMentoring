package dao.transfer;

/**
 * Class of category object
 * 
 * @author Denis_Shipilov
 * 
 */
public class CategoryTransfer {

	

	private int categoryId;
	private String category;
	private int parentCategory;
	
	/**
	 * Constructor of category transfer using all fields
	 * @param categoryId
	 * @param category
	 * @param parentCategory
	 */
	public CategoryTransfer(int categoryId, String category, 
												int parentCategory) {
		super();
		this.categoryId = categoryId;
		this.category = category;
		this.parentCategory = parentCategory;
	}
	
	
	/**
	 * Constructor of category transfer without categoryId field
	 * @param categoryId
	 * @param category
	 * @param parentCategory
	 */
	public CategoryTransfer(String category, int parentCategory) {
		super();
		this.category = category;
		this.parentCategory = parentCategory;
	}
	
	
	

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the parentCategory
	 */
	public int getParentCategory() {
		return parentCategory;
	}

	/**
	 * @param parentCategory
	 *            the parentCategory to set
	 */
	public void setParentCategory(int parentCategory) {
		this.parentCategory = parentCategory;
	}

}
