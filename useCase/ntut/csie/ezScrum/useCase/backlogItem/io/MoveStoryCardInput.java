package ntut.csie.ezScrum.useCase.backlogItem.io;

public class MoveStoryCardInput {
	private String backlogItemId;
	private String status;
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
