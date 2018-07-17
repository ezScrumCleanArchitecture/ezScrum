package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Input;

public interface MoveTaskCardInput extends Input{
	public String getTaskId();
	public void setTaskId(String taskId);
	public String getStatus();
	public void setStatus(String status);
}
