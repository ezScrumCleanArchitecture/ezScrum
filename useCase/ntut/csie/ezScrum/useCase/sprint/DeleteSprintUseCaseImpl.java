package ntut.csie.ezScrum.useCase.sprint;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;

public class DeleteSprintUseCaseImpl implements DeleteSprintUseCase ,DeleteSprintInput{
	private ApplicationContext context;
	private String sprintId;

	public DeleteSprintUseCaseImpl() {}
	
	public DeleteSprintUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(DeleteSprintInput input, DeleteSprintOutput output) {
		String sprintId = input.getSprintId();
		Sprint sprint = context.getSprint(sprintId);
		if(sprint == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		int orderId = sprint.getOrderId();
		int numberOfSprints = context.getNumberOfSprints();
		Sprint[] sprints = context.getSprints().toArray(new Sprint[numberOfSprints]);
		for(int i = orderId; i < numberOfSprints; i++) {
			sprints[i].setOrderId(i);
			context.editSprint(sprints[i].getSprintId(), sprints[i]);
		}
		context.deleteSprint(sprintId);
		output.setDeleteSuccess(true);
		output.setErrorMessage("Delete sprint success.");
	}
	
	@Override
	public String getSprintId() {
		return sprintId;
	}
	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}


}
