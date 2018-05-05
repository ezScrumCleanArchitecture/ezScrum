package ntut.csie.ezScrum.useCase.Task;

import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class TaskManagerUseCase {
	
	private ApplicationContext context;
	
	public TaskManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public String addTask(Task task) {
		context.addTask(task);
		return task.getTaskId();
	}
}
