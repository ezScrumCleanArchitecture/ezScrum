package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface DropBacklogItemInput extends Input{
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
}
