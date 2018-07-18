package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Output;

public interface EditRetrospectiveOutput extends Output{
	public boolean isEditSuccess();
	public void setEditSuccess(boolean editSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
