package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Input;

public interface DeleteTaskInput extends Input{
	public String getTaskId();
	public void setTaskId(String taskId);
}
