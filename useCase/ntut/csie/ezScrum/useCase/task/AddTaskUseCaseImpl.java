package ntut.csie.ezScrum.useCase.task;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;

public class AddTaskUseCaseImpl implements AddTaskUseCase, AddTaskInput{
	private ApplicationContext context;
	
	private String description;
	private int estimate;
	private String notes;
	private String backlogItemId;
	
	public AddTaskUseCaseImpl() {}
	
	public AddTaskUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(AddTaskInput input, AddTaskOutput output) {
		int orderId = context.getNumberOfTasks() + 1;
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
		context.addTask(task);
		output.setAddSuccess(true);
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
}
