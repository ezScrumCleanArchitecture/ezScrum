package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Output;

public interface AddSprintOutput extends Output{
	public boolean isAddSuccess();
	public void setAddSuccess(boolean addSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
