package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemOutput;

public class AssignBacklogItemUseCaseImpl implements AssignBacklogItemUseCase, AssignBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Sprint> sprintRepository;
	private Repository<History> historyRepository;
	
	private String sprintId;
	private String backlogItemId;

	public AssignBacklogItemUseCaseImpl() {}
	
	public AssignBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<Sprint> sprintRepository, Repository<History> historyRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.sprintRepository = sprintRepository;
		this.historyRepository = historyRepository;
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
		Sprint sprint = sprintRepository.get(sprintId);
		String sprintGoal = sprint.getGoal();
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(backlogItem.getBacklogItemId()).
					issueType(IssueType.backlogItem).
					type(Type.assignToSprint).
					newValue("\"" + sprintGoal + "\"").
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
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
