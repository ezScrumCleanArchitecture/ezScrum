package ntut.csie.ezScrum.useCase.backlogItem.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllCommittedBacklogItemOutput extends Output{
	public List<GetAllCommittedBacklogItemDTO> getCommittedBacklogItemList();
	public void setCommittedBacklogItemList(List<GetAllCommittedBacklogItemDTO> committedBacklogItemList);
}
