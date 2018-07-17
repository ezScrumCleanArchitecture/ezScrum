package ntut.csie.ezScrum.useCase.sprint.io;

import ntut.csie.ezScrum.useCase.Output;

public interface EditSprintOutput extends Output{
	public boolean isEditSuccess();
	public void setEditSuccess(boolean editSuccess);
	public boolean isOverlap();
	public void setOverlap(boolean overlap);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
}
