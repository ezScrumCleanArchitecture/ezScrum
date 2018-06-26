package ntut.csie.ezScrum.useCase.retrospective.io;

public class RetrospectiveDTO {
	private String retrospectiveId;
	private int orderId;
	private String description;
	private String productId;
	private int sprintOrderId;
	private String createTime;
	private String updateTime;

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

	public int getSprintOrderId() {
		return sprintOrderId;
	}

	public void setSprintOrderId(int sprintOrderId) {
		this.sprintOrderId = sprintOrderId;
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
