package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;

public class DeleteBacklogItemUseCaseImpl implements DeleteBacklogItemUseCase, DeleteBacklogItemInput{

	private ApplicationContext context;
	private String backlogItemId;

	public DeleteBacklogItemUseCaseImpl() {}
	
	public DeleteBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(DeleteBacklogItemInput input, DeleteBacklogItemOutput output) {
		String backlogItemId = input.getBacklogItemId();
		int orderId = context.getBacklogItem(backlogItemId).getOrderId();
		int numberOfBacklogItems = context.getNumberOfBacklogItems();
		BacklogItem[] backlogItems = context.getBacklogItems().toArray(new BacklogItem[numberOfBacklogItems]);
		for(int i = orderId; i < numberOfBacklogItems; i++) {
			backlogItems[i].setOrderId(i);
			context.editBacklogItem(backlogItems[i].getBacklogItemId(), backlogItems[i]);
		}
		context.deleteBacklogItem(backlogItemId);
		output.setDeleteSuccess(true);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}

}
