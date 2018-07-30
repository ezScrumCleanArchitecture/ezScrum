package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemOutput;

public class AssignBacklogItemUseCaseImpl implements AssignBacklogItemUseCase, AssignBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	
	private String sprintId;
	private String backlogItemId;

	public AssignBacklogItemUseCaseImpl() {}
	
	public AssignBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository) {
		this.backlogItemRepository = backlogItemRepository;
	}
	
	@Override
	public void execute(AssignBacklogItemInput input, AssignBacklogItemOutput output) {
		String sprintId = input.getSprintId();
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setAssignSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		backlogItem.setSprintId(sprintId);
		backlogItemRepository.update(backlogItem);
		output.setAssignSuccess(true);
	}

	@Override
	public String getSprintId() {
		return sprintId;
	}

	@Override
	public void setSprintId(String sprintId) {
		this.sprintId= sprintId;
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
