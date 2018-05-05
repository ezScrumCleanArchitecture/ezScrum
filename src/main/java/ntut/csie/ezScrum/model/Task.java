package ntut.csie.ezScrum.model;

public class Task {
	
	private String taskId;
	private long serialId;
	private String description;
	private String handlerId;
	private String status;
	private int estimate;
	private int remain;
	private String notes;
	private String backlogItemId;
	private String createTime;
	private String updateTime;
	
	public Task() {}
	
	public Task(String description, String backlogItemId, String createTime) {
		this.description = description;
		this.backlogItemId = backlogItemId;
		this.createTime = createTime;
		this.updateTime = createTime;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public long getSerialId() {
		return serialId;
	}
	public void setSerialId(long serialId) {
		this.serialId = serialId;
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
	public int getRemain() {
		return remain;
	}
	public void setRemain(int remain) {
		this.remain = remain;
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
