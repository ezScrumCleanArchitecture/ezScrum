package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;

public class EditTaskUseCaseImpl implements EditTaskUseCase, EditTaskInput{
	
	private Repository<Task> taskRepository;
	private Repository<History> historyRepository;
	
	private String taskId;
	private String description;
	private int estimate;
	private int remains;
	private String notes;
	private String backlogItemId;
	
	public EditTaskUseCaseImpl() {}
	
	public EditTaskUseCaseImpl(Repository<Task> taskRepository,  Repository<History> historyRepository) {
		this.taskRepository = taskRepository;
		this.historyRepository = historyRepository;
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
		String originalDescription = task.getDescription();
		int originalEstimate = task.getEstimate();
		int originalRemains = task.getRemains();
		String originalNotes = task.getNotes();
		
		String editedDescription = input.getDescription();
		int editedEstimate = input.getEstimate();
		int editedRemains = input.getRemains();
		String editedNotes = input.getNotes();
		
		if(!originalDescription.equals(editedDescription)) {
			task.setDescription(input.getDescription());
			recordHistory(task.getTaskId(), Type.editDescription, originalDescription, editedDescription);
		}
		if(originalEstimate != editedEstimate) {
			task.setEstimate(input.getEstimate());
			recordHistory(task.getTaskId(), Type.editEstimate, String.valueOf(originalEstimate), String.valueOf(editedEstimate));
		}
		if(originalRemains != editedRemains) {
			task.setRemains(input.getRemains());
			recordHistory(task.getTaskId(), Type.editRemains, String.valueOf(originalRemains), String.valueOf(editedRemains));
		}
		if(!originalNotes.equals(editedNotes)) {
			task.setNotes(input.getNotes());
			recordHistory(task.getTaskId(), Type.editNotes, String.valueOf(originalNotes), String.valueOf(editedNotes));
		}
		
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
	
	private void recordHistory(String taskId, String type, String oldValue, String newValue) {
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(taskId).
					issueType(IssueType.task).
					type(type).
					oldValue(oldValue).
					newValue(newValue).
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
	}
	
}
