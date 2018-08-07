package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.DropBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DropBacklogItemOutput;

public class DropBacklogItemUseCaseImpl implements DropBacklogItemUseCase, DropBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Sprint> sprintRepository;
	private Repository<History> historyRepository;
	
	private String backlogItemId;
	
	public DropBacklogItemUseCaseImpl() {}
	
	public DropBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<Sprint> sprintRepository, Repository<History> historyRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.sprintRepository = sprintRepository;
		this.historyRepository = historyRepository;
	}
	
	@Override
	public void execute(DropBacklogItemInput input, DropBacklogItemOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setDropSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		String sprintId = backlogItem.getSprintId();
		Sprint sprint = sprintRepository.get(sprintId);
		if(sprint == null) {
			output.setDropSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		String sprintGoal = sprint.getGoal();
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(backlogItem.getBacklogItemId()).
					issueType(IssueType.backlogItem).
					type(Type.dropFromSprint).
					newValue("\"" + sprintGoal + "\"").
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
		backlogItem.setSprintId(null);
		backlogItemRepository.update(backlogItem);
		output.setDropSuccess(true);
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
