package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.model.history.HistoryBuilder;
import ntut.csie.ezScrum.model.history.IssueType;
import ntut.csie.ezScrum.model.history.Type;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;

public class AddTaskUseCaseImpl implements AddTaskUseCase, AddTaskInput{
	
	private Repository<Task> taskRepository;
	private Repository<History> historyRepository;
	
	private String description;
	private int estimate;
	private String notes;
	private String backlogItemId;
	
	public AddTaskUseCaseImpl() {}
	
	public AddTaskUseCaseImpl(Repository<Task> taskRepository,Repository<History> historyRepository) {
		this.taskRepository = taskRepository;
		this.historyRepository = historyRepository;
	}
	
	@Override
	public void execute(AddTaskInput input, AddTaskOutput output) {
		int orderId = taskRepository.getNumberOfItems() + 1;
		Task task = null;
		try {
			task = TaskBuilder.newInstance().
					orderId(orderId).
					description(input.getDescription()).
					estimate(input.getEstimate()).
					notes(input.getNotes()).
					backlogItemId(input.getBacklogItemId()).
					build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		taskRepository.add(task);
		output.setAddSuccess(true);
		
		recordHistory(task.getTaskId(),IssueType.task,Type.create,null);
		recordHistory(task.getBacklogItemId(),IssueType.backlogItem,Type.addTask, "\"" + task.getDescription() + "\"");
		
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
	
	private void recordHistory(String issueId,String issueType ,String type,String newValue) {
		History history = null;
		try {
			history = HistoryBuilder.newInstance().
					issueId(issueId).
					issueType(issueType).
					type(type).
					newValue(newValue).
					build();
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		historyRepository.add(history);
	}
	
}
