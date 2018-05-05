package ntut.csie.ezScrum.useCase.BacklogItem;

public class BacklogItemDTO {

	private long serialId;
	private String description;
	private String Status;
	private int estimate;
	private int importance;
	private long sprintSerialId;
	private String notes;
	
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
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
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
	public long getSprintSerialId() {
		return sprintSerialId;
	}
	public void setSprintSerialId(long sprintSerialId) {
		this.sprintSerialId = sprintSerialId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
