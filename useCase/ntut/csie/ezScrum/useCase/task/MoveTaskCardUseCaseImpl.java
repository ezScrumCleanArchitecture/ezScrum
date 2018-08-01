package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardOutput;

public class MoveTaskCardUseCaseImpl implements MoveTaskCardUseCase, MoveTaskCardInput {
	
	private Repository<Task> taskRepository;
	private Repository<History> historyRepository;
	
	private String taskId;
	private String status;
	
	public MoveTaskCardUseCaseImpl() {}
	
	public MoveTaskCardUseCaseImpl(Repository<Task> taskRepository , Repository<History> historyRepository) {
		this.taskRepository = taskRepository;
		this.historyRepository =historyRepository;
	}
	
	@Override
	public void execute(MoveTaskCardInput input, MoveTaskCardOutput output) {
		String taskId = input.getTaskId();
		Task task = taskRepository.get(taskId);
		if(task == null) {
			output.setMoveSuccess(false);
			output.setErrorMessage("Sorry, the task is not exist.");
			return;
		}
		String originalStatus = task.getStatus();
		String newStatus = input.getStatus();

		task.setStatus(newStatus);
		if(newStatus.equals("Done")) {
			task.setRemains(0);
		}
		taskRepository.update(task);
		output.setMoveSuccess(true);
		
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(taskId).
					issueType(IssueType.task).
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
