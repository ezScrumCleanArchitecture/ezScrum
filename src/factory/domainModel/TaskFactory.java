package factory.domainModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import model.Task;
import model.ezScrumDomainModel;

public class TaskFactory implements DomainModelFactory{
	
	private String description;
	private int estimate;
	private String notes;
	private String backlogItemId;

	public TaskFactory() {}
	public TaskFactory(String description, int estimate, String notes, String backlogItemId) {
		this.description = description;
		this.estimate = estimate;
		this.notes = notes;
		this.backlogItemId = backlogItemId;
	}
	
	public ezScrumDomainModel createDomainModel() {
		String taskId = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Task task = new Task(description, backlogItemId, time);
		task.setTaskId(taskId);
		task.setEstimate(estimate);
		task.setRemain(estimate);
		task.setNotes(notes);
		task.setStatus("Not Check Out");
		return task;
	}
}
