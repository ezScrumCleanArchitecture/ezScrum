package ntut.csie.ezScrum.restfulAPI.BacklogItem;

public class BacklogItemTableViewModel {

	private String backlogItemId;
	private long orderId;
	private String description;
	private String status;
	private int estimate;
	private int importance;
	private int sprintOrderId;
	private String notes;
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getSprintOrderId() {
		return sprintOrderId;
	}
	public void setSprintOrderId(int sprintOrderId) {
		this.sprintOrderId = sprintOrderId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}