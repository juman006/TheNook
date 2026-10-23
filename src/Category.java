
public class Category {
	private String categoryName;
	private String categoryImageName;
	
	Category(){}

	public Category(String categoryName, String categoryImageName) {
		this.categoryName = categoryName;
		this.categoryImageName = categoryImageName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryImageName() {
		return categoryImageName;
	}

	public void setCategoryImageName(String categoryImageName) {
		this.categoryImageName = categoryImageName;
	}
	
	
	
}

