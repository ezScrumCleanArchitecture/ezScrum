package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase, DeleteTaskInput {
	
	private Repository<Task> taskRepository;
	private Repository<History> historyRepository;
	
	private String taskId;	
	
	public DeleteTaskUseCaseImpl() {}
	
	public DeleteTaskUseCaseImpl(Repository<Task> taskRepository ,Repository<History> historyRepository) {
		this.taskRepository = taskRepository;
		this.historyRepository = historyRepository;
	}
	
	@Override
	public void execute(DeleteTaskInput input, DeleteTaskOutput output) {
		String taskId = input.getTaskId();
		Task task = taskRepository.get(taskId);
		if(task == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the task is not exist.");
			return;
		}
		int orderId = task.getOrderId();
		int numberOfTasks = taskRepository.getNumberOfItems();
		Task[] tasks = taskRepository.getAll().toArray(new Task[numberOfTasks]);
		for(int i = orderId; i < numberOfTasks; i++) {
			tasks[i].setOrderId(i);
			taskRepository.update(tasks[i]);
		}
		taskRepository.remove(task);
		output.setDeleteSuccess(true);
		
		History backlogItemHistory = null;
		try {
			backlogItemHistory = HistoryBuilder.newInstance().
					issueId(task.getBacklogItemId()).
					issueType(IssueType.backlogItem).
					type(Type.removeTask).
					newValue("\"" + task.getDescription() + "\"").
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(backlogItemHistory);
		
		for(History history : historyRepository.getAll()) {
			if(history.getIssueId().equals(taskId)) {
				historyRepository.remove(history);
			}
		}
	}
	
	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
