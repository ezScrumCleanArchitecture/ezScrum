package ntut.csie.ezScrum.model.retrospective;

public class Retrospective {
	private String retrospectiveId;
	private int orderId;
	private String description;
	private String productId;
	private String sprintId;
	private String createTime;
	private String updateTime;
	
	public Retrospective() {
		
	}

	public Retrospective(String description, String productId, String sprintId, String createTime) {
		this.description = description;
		this.productId = productId;
		this.sprintId = sprintId;
		this.createTime = createTime;
		this.updateTime = createTime;
	}

	public String getRetrospectiveId() {
		return retrospectiveId;
	}

	public void setRetrospectiveId(String retrospectiveId) {
		this.retrospectiveId = retrospectiveId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
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
