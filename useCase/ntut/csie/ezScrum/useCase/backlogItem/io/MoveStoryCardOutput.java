package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface MoveStoryCardOutput extends Output{
	public boolean isMoveSuccess();
	public void setMoveSuccess(boolean moveSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
