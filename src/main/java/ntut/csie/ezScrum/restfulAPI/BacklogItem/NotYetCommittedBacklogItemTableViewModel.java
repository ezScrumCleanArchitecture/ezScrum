package ntut.csie.ezScrum.restfulAPI.BacklogItem;

public class NotYetCommittedBacklogItemTableViewModel {

	private String backlogItemId;
	private long serialId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
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
	
}
