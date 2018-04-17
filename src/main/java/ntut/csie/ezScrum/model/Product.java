package ntut.csie.ezScrum.model;

public class Product extends ezScrumDomainModel {
	private String productId;
	private String name;
	private String comment;
	private String create_time;
	private String update_time;
	
	public Product() {
	}
	
	public Product(String name,String create_time) {
		this.name=name;
		this.create_time=create_time;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
