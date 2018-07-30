package ntut.csie.ezScrum.repository.task;

public class TaskData {
	
	private String taskId;
	private int orderId;
	private String description;
	private String handlerId;
	private String status;
	private int estimate;
	private int remains;
	private String notes;
	private String backlogItemId;
	private String createTime;
	private String updateTime;
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
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
