package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface MoveStoryCardInput extends Input{
	public String getBacklogItemId();
	public void setBacklogItemId(String backlogItemId);
	public String getStatus();
	public void setStatus(String status);
}
