package ntut.csie.ezScrum.useCase.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import ntut.csie.ezScrum.model.Task;

public class TaskBuilder {
	private String taskId;
	private long serialId;
	private String description;
	private int estimate;
	private String notes;
	private String backlogItemId;
	private String createTime;
	
	public static TaskBuilder newInstance() {
		return new TaskBuilder();
	}
	
	public TaskBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public TaskBuilder estimate(int estimate) {
		this.estimate = estimate;
		return this;
	}
	
	public TaskBuilder notes(String notes) {
		this.notes = notes;
		return this;
	}
	
	public TaskBuilder backlogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
		return this;
	}
	
	public Task build() throws Exception{
		taskId = UUID.randomUUID().toString();
		if(description == null) {
			throw new Exception("The description of the task should not be null.");
		}
		Calendar calendar = Calendar.getInstance();
		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Task task = new Task(description, backlogItemId, createTime);
		task.setTaskId(taskId);
		task.setSerialId(++serialId);
		task.setEstimate(estimate);
		task.setRemain(estimate);
		task.setNotes(notes);
		task.setStatus("Not Check Out");
		return task;
	}
}
