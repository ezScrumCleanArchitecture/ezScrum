package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Input;

public interface DeleteSprintInput extends Input{
	public String getSprintId();
	public void setSprintId(String sprintId);
}
