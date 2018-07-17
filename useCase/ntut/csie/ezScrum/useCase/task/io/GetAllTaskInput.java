package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllTaskInput extends Input{
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
