package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface DeleteBacklogItemOutput extends Output{
	public boolean isDeleteSuccess();
	public void setDeleteSuccess(boolean deleteSuccess);
}
