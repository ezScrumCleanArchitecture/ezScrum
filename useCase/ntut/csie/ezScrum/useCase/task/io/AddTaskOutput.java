package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Output;

public interface AddTaskOutput extends Output{
	public boolean isAddSuccess();
	public void setAddSuccess(boolean addSuccess);
}
