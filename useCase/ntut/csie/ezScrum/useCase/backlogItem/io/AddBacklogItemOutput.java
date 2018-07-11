package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Output;

public interface AddBacklogItemOutput extends Output{
	
	public String getBacklogItemId();

	public void setBacklogItemId(String backlogItemId);
}
