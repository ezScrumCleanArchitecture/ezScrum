package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardOutput;

public class MoveTaskCardUseCaseImpl implements MoveTaskCardUseCase, MoveTaskCardInput {
	private ApplicationContext context;
	
	private String taskId;
	private String status;
	
	public MoveTaskCardUseCaseImpl() {}
	
	public MoveTaskCardUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(MoveTaskCardInput input, MoveTaskCardOutput output) {
		String taskId = input.getTaskId();
		Task task = context.getTask(taskId);
		if(task == null) {
			output.setMoveSuccess(false);
			output.setErrorMessage("Sorry, the task is not exist.");
			return;
		}
		String status = input.getStatus();
		task.setStatus(status);
		if(status.equals("Done")) {
			task.setRemains(0);
		}
		context.editTask(taskId, task);
		output.setMoveSuccess(true);
	}
	
	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
