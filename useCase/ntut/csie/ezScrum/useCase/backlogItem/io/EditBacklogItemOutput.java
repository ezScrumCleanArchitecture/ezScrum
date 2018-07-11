package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface EditBacklogItemOutput extends Output{
	public boolean isEditSuccess();
	public void setEditSuccess(boolean editSuccess);
}
