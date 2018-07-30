package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;

public class EditTaskUseCaseImpl implements EditTaskUseCase, EditTaskInput{
	
	private Repository<Task> taskRepository;
	
	private String taskId;
	private String description;
	private int estimate;
	private int remains;
	private String notes;
	private String backlogItemId;
	
	public EditTaskUseCaseImpl() {}
	
	public EditTaskUseCaseImpl(Repository<Task> taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@Override
	public void execute(EditTaskInput input, EditTaskOutput output) {
		String taskId = input.getTaskId();
		Task task = taskRepository.get(taskId);
		if(task == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the task is not exist.");
			return;
		}
		task.setDescription(input.getDescription());
		task.setEstimate(input.getEstimate());
		task.setRemains(input.getRemains());
		task.setNotes(input.getNotes());
		taskRepository.update(task);
		output.setEditSuccess(true);
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
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int getEstimate() {
		return estimate;
	}
	
	@Override
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
	
	@Override
	public int getRemains() {
		return remains;
	}
	
	@Override
	public void setRemains(int remains) {
		this.remains = remains;
	}
	
	@Override
	public String getNotes() {
		return notes;
	}
	
	@Override
	public void setNotes(String notes) {
		this.notes = notes;
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
