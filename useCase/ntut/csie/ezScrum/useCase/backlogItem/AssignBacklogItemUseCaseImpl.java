package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemOutput;

public class AssignBacklogItemUseCaseImpl implements AssignBacklogItemUseCase, AssignBacklogItemInput{

	private ApplicationContext context;
	private String sprintId;
	private String backlogItemId;

	public AssignBacklogItemUseCaseImpl() {}
	
	public AssignBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(AssignBacklogItemInput input, AssignBacklogItemOutput output) {
		String sprintId = input.getSprintId();
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = context.getBacklogItem(backlogItemId);
		backlogItem.setSprintId(sprintId);
		context.editBacklogItem(backlogItemId, backlogItem);
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
