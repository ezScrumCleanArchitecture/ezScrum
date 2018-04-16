package model;

public class CommittedBacklogItem {
	private long committedBacklogItemId;
	private BacklogItem backlogItem;
	private long sprintId;
	
	public CommittedBacklogItem() {}
	
	public CommittedBacklogItem(long sprintId,BacklogItem backlogItem) {
		this.sprintId=sprintId;
		this.backlogItem=backlogItem;
	}

	public long getCommittedBacklogItemId() {
		return committedBacklogItemId;
	}

	public void setCommittedBacklogItemId(long committedBacklogItemId) {
		this.committedBacklogItemId = committedBacklogItemId;
	}

	public BacklogItem getBacklogItem() {
		return backlogItem;
	}

	public void setBacklogItem(BacklogItem backlogItem) {
		this.backlogItem = backlogItem;
	}

	public long getSprintId() {
		return sprintId;
	}

	public void setSprintId(long sprintId) {
		this.sprintId = sprintId;
	}
}
