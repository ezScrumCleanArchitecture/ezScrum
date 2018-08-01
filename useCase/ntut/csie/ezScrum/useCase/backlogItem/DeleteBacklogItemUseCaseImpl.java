package ntut.csie.ezScrum.useCase.backlogItem;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.DeleteBacklogItemOutput;

public class DeleteBacklogItemUseCaseImpl implements DeleteBacklogItemUseCase, DeleteBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Task> taskRepository;
	private Repository<History> historyRepository;
	
	private String backlogItemId;

	public DeleteBacklogItemUseCaseImpl() {}
	
	public DeleteBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<Task> taskRepository, Repository<History> historyRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.taskRepository = taskRepository;
		this.historyRepository = historyRepository;
	}
	
	@Override
	public void execute(DeleteBacklogItemInput input, DeleteBacklogItemOutput output) {
		String backlogItemId = input.getBacklogItemId();
		BacklogItem backlogItem = backlogItemRepository.get(backlogItemId);
		if(backlogItem == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the backlog item is not exist.");
			return;
		}
		int orderId = backlogItem.getOrderId();
		int numberOfBacklogItems = backlogItemRepository.getNumberOfItems();
		BacklogItem[] backlogItems = backlogItemRepository.getAll().toArray(new BacklogItem[numberOfBacklogItems]);
		for(int i = orderId; i < numberOfBacklogItems; i++) {
			backlogItems[i].setOrderId(i);
			backlogItemRepository.update(backlogItems[i]);
		}
		backlogItemRepository.remove(backlogItem);
		output.setDeleteSuccess(true);
		for(Task task : taskRepository.getAll()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskRepository.remove(task);
			}
		}
		for(History history : historyRepository.getAll()) {
			if(history.getIssueId().equals(backlogItemId)) {
				historyRepository.remove(history);
			}
		}
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
