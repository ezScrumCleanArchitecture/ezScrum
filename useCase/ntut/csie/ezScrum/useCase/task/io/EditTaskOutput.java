package ntut.csie.ezScrum.useCase.task.io;

import ntut.csie.ezScrum.useCase.Output;

public interface EditTaskOutput extends Output{
	public boolean isEditSuccess();
	public void setEditSuccess(boolean editSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
