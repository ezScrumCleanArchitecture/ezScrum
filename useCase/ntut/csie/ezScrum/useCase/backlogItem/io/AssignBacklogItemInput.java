package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface AssignBacklogItemInput extends Input{
	public String getSprintId();
	public void setSprintId(String sprintId);
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
