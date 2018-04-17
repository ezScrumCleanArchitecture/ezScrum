package ntut.csie.ezScrum.model;

public class BacklogItem extends ezScrumDomainModel {
	private String backlogItemId;
	private String description;
	private String Status;
	private int estimate;
	private int importance;
	private String notes;
	private String productId; 
	private String committedBacklogItemId;
	private String create_time;
	private String update_time;
	
	public BacklogItem() {}
	
	public BacklogItem(String productId,String description,String create_time) {
		this.productId=productId;
		this.description=description;
		this.create_time=create_time;
	}

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

	public String getCommittedBacklogItemId() {
		return committedBacklogItemId;
	}

	public void setCommittedBacklogItemId(String sprintId) {
		this.committedBacklogItemId = sprintId;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
