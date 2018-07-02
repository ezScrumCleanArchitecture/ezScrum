package ntut.csie.ezScrum.useCase.backlogItem.io;

public class EditBacklogItemInput {
	private String backlogItemId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	private String productId;
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEstimate() {
		return estimate;
	}
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	} 
}