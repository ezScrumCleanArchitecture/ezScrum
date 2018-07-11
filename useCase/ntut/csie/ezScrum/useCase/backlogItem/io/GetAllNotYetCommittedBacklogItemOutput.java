package ntut.csie.ezScrum.useCase.backlogItem.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllNotYetCommittedBacklogItemOutput extends Output{
	public List<GetAllNotYetCommittedBacklogItemDTO> getNotYetCommittedBacklogItemList();
	public void setNotYetCommittedBacklogItemList(List<GetAllNotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList);
}
