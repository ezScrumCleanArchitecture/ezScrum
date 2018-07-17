package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Output;

public interface DeleteSprintOutput extends Output{
	public boolean isDeleteSuccess();
	public void setDeleteSuccess(boolean deleteSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
