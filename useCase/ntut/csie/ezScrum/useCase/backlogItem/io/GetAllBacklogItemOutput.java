package ntut.csie.ezScrum.useCase.backlogItem.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllBacklogItemOutput extends Output{
	public List<BacklogItemModel> getBacklogItemList();
	public void setBacklogItemList(List<BacklogItemModel> backlogItemList);
}
