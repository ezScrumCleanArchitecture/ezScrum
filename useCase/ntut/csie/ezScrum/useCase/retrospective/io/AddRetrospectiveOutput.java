package ntut.csie.ezScrum.useCase.retrospective.io;

import ntut.csie.ezScrum.useCase.Output;

public interface AddRetrospectiveOutput extends Output{
	public boolean isAddSuccess();
	public void setAddSuccess(boolean addSuccess);
}
