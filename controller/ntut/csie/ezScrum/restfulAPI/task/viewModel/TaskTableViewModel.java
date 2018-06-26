package ntut.csie.ezScrum.restfulAPI.task.viewModel;

public class TaskTableViewModel extends TaskViewModel {
	private int orderId;
	private String description;
	private String handlerId;
	private String status;
	private int estimate;
	private int remains;
	private String notes;
	private long backlogItemOrderId;
	
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
	public String getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
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
	public int getRemains() {
		return remains;
	}
	public void setRemains(int remains) {
		this.remains = remains;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public long getBacklogItemOrderId() {
		return backlogItemOrderId;
	}
	public void setBacklogItemOrderId(long backlogItemOrderId) {
		this.backlogItemOrderId = backlogItemOrderId;
	}
}
