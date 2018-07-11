package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface MoveStoryCardOutput extends Output{
	public boolean isMoveStoryCardSuccess();
	public void setMoveStoryCardSuccess(boolean moveStoryCardSuccess);
}
