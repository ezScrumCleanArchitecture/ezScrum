package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Output;

public interface MoveTaskCardOutput extends Output{
	public boolean isMoveSuccess();
	public void setMoveSuccess(boolean moveSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
