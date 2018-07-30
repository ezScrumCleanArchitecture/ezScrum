package ntut.csie.ezScrum.useCase.sprint;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.DeleteSprintOutput;

public class DeleteSprintUseCaseImpl implements DeleteSprintUseCase ,DeleteSprintInput{
	
	private Repository<Sprint> sprintRepository = new SprintRepository();
	
	private String sprintId;

	public DeleteSprintUseCaseImpl() {}
	
	public DeleteSprintUseCaseImpl(Repository<Sprint> sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(DeleteSprintInput input, DeleteSprintOutput output) {
		String sprintId = input.getSprintId();
		Sprint sprint = sprintRepository.get(sprintId);
		if(sprint == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		int orderId = sprint.getOrderId();
		int numberOfSprints = sprintRepository.getNumberOfItems();
		Sprint[] sprints = sprintRepository.getAll().toArray(new Sprint[numberOfSprints]);
		for(int i = orderId; i < numberOfSprints; i++) {
			sprints[i].setOrderId(i);
			sprintRepository.update(sprints[i]);
		}
		sprintRepository.remove(sprint);
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
