package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface AssignBacklogItemOutput extends Output{
	public boolean isAssignSuccess();
	public void setAssignSuccess(boolean assignSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
