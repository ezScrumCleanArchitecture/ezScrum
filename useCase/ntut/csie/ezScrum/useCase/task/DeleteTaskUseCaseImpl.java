package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase, DeleteTaskInput {
	
	private Repository<Task> taskRepository;
	
	private String taskId;	
	
	public DeleteTaskUseCaseImpl() {}
	
	public DeleteTaskUseCaseImpl(Repository<Task> taskRepository) {
		this.taskRepository = taskRepository;
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
