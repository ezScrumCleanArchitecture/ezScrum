package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.MoveStoryCardOutput;

public class MoveStoryCardUseCaseImpl implements MoveStoryCardUseCase, MoveStoryCardInput{
	
	private Repository<BacklogItem> backlogItemRepository;
	private Repository<History> historyRepository;
	
	private String backlogItemId;
	private String status;
	
	public MoveStoryCardUseCaseImpl() {}
	
	public MoveStoryCardUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<History> historyRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.historyRepository = historyRepository;
	}

	@Override
	public void execute(MoveStoryCardInput input, MoveStoryCardOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setMoveSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		String originalStatus = backlogItem.getStatus();
		String newStatus = input.getStatus();
		backlogItem.setStatus(newStatus);
		backlogItemRepository.update(backlogItem);
		output.setMoveSuccess(true);
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(backlogItemId).
					issueType(IssueType.backlogItem).
					type(Type.changeStatus).
					oldValue(originalStatus).
					newValue(newStatus).
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
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
