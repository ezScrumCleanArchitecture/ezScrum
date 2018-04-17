package ntut.csie.ezScrum.model;

public class CommittedBacklogItem extends ezScrumDomainModel  {
	private String committedBacklogItemId;
	private BacklogItem backlogItem;
	private String sprintId;
	
	public CommittedBacklogItem() {}
	
	public CommittedBacklogItem(String sprintId,BacklogItem backlogItem) {
		this.sprintId=sprintId;
		this.backlogItem=backlogItem;
	}

	public String getCommittedBacklogItemId() {
		return committedBacklogItemId;
	}

	public void setCommittedBacklogItemId(String committedBacklogItemId) {
		this.committedBacklogItemId = committedBacklogItemId;
	}

	public BacklogItem getBacklogItem() {
		return backlogItem;
	}

	public void setBacklogItem(BacklogItem backlogItem) {
		this.backlogItem = backlogItem;
	}

	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
