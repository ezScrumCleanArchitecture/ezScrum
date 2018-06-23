package ntut.csie.ezScrum.model.product;

public class Product{
	private String productId;
	private String name;
	private String comment;
	private String createTime;
	private String updateTime;
	
	public Product() {
	}
	
	public Product(String name,String createTime) {
		this.name=name;
		this.createTime=createTime;
		this.updateTime=createTime;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
