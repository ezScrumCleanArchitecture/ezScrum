package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Output;

public interface DeleteTaskOutput extends Output{
	public boolean isDeleteSuccess();
	public void setDeleteSuccess(boolean deleteSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
