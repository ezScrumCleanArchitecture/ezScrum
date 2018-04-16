package useCase;

import factory.domainModel.TaskFactory;
import model.Task;

public class CreateTask {
	public String execute(ApplicationContext context, String description, int estimate, String notes, String backlogItemId) {
		TaskFactory taskFactory = new TaskFactory(description, estimate, notes, backlogItemId);
		Task task = (Task) taskFactory.createDomainModel();
		context.getTasks().put(task.getTaskId(), task);
		return task.getTaskId();
	}
}
