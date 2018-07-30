package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

public class MoveStoryCardUseCaseImpl implements MoveStoryCardUseCase, MoveStoryCardInput{
	
	private Repository<BacklogItem> backlogItemRepository;
	
	private String backlogItemId;
	private String status;
	
	public MoveStoryCardUseCaseImpl() {}
	
	public MoveStoryCardUseCaseImpl(Repository<BacklogItem> backlogItemRepository) {
		this.backlogItemRepository = backlogItemRepository;
	}

	@Override
	public void execute(MoveStoryCardInput input, MoveStoryCardOutput output) {
		String backlogItemId = input.getBacklogItemId();
		String status = input.getStatus();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setMoveSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		backlogItem.setStatus(status);
		backlogItemRepository.update(backlogItem);
		output.setMoveSuccess(true);
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
