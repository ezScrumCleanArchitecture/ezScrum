package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface DeleteBacklogItemInput extends Input{
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
