package ntut.csie.ezScrum.useCase.backlogItem.io;

import ntut.csie.ezScrum.useCase.Input;

public interface GetAllNotYetCommittedBacklogItemInput extends Input{
	public String getProductId();
	public void setProductId(String productId);
}
