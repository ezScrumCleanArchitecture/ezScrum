package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllCommittedBacklogItemInput extends Input{
	public String getProductId();
	public void setProductId(String productId);
	public String getSprintId();
	public void setSprintId(String sprintId);
}
