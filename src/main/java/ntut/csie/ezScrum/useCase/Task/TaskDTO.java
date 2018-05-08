package ntut.csie.ezScrum.useCase.Task;

public class TaskDTO {
	private long serialId;
	private String description;
	private String handlerId;
	private String status;
	private int estimate;
	private int remain;
	private String notes;
	private long backlogItemSerialId;
	
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
	public long getBacklogItemSerialId() {
		return backlogItemSerialId;
	}
	public void setBacklogItemSerialId(long backlogItemSerialId) {
		this.backlogItemSerialId = backlogItemSerialId;
	}
	
}
