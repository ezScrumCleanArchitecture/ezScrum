package ntut.csie.ezScrum.useCase.backlogItem.io;

public class AssignBacklogItemInput {
	private String sprintId;
	private String backlogItemId;
	
	public String getSprintId() {
		return sprintId;
	}
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	public String getBacklogItemId() {
		return backlogItemId;
	}
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
