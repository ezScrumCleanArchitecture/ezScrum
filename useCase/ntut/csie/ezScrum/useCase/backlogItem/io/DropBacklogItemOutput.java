package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface DropBacklogItemOutput extends Output{
	public boolean isDropSuccess();
	public void setDropSuccess(boolean dropSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
