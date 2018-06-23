package ntut.csie.ezScrum.useCase.backlogItem.io;

public class GetCommittedBacklogItemInput {
	private String productId;
	private String sprintId;
	
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
	
}
