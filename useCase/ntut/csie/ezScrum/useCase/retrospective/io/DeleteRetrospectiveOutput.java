package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Output;

public interface DeleteRetrospectiveOutput extends Output{
	public boolean isDeleteSuccess();
	public void setDeleteSuccess(boolean deleteSuccess);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
