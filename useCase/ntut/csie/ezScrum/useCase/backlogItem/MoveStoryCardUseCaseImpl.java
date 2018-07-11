package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

public class MoveStoryCardUseCaseImpl implements MoveStoryCardUseCase, MoveStoryCardInput{
	
	private ApplicationContext context;
	
	private String backlogItemId;
	private String status;
	
	public MoveStoryCardUseCaseImpl() {}
	
	public MoveStoryCardUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void execute(MoveStoryCardInput input, MoveStoryCardOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setStatus(input.getStatus());
		context.editBacklogItem(backlogItemId, backlogItem);
		output.setMoveStoryCardSuccess(true);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

}
