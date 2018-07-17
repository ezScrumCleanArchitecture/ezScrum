package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase, DeleteTaskInput {
	private ApplicationContext context;
	
	private String taskId;	
	
	public DeleteTaskUseCaseImpl() {}
	
	public DeleteTaskUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(DeleteTaskInput input, DeleteTaskOutput output) {
		String taskId = input.getTaskId();
		Task task = context.getTask(taskId);
		if(task == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the task is not exist.");
			return;
		}
		int orderId = task.getOrderId();
		int numberOfTasks = context.getNumberOfTasks();
		Task[] tasks = context.getTasks().toArray(new Task[numberOfTasks]);
		for(int i = orderId; i < numberOfTasks; i++) {
			tasks[i].setOrderId(i);
			context.editTask(tasks[i].getTaskId(), tasks[i]);
		}
		context.deleteTask(taskId);
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
