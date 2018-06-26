package ntut.csie.ezScrum.useCase.task;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.io.AddTaskInput;
import ntut.csie.ezScrum.useCase.task.io.AddTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskInput;
import ntut.csie.ezScrum.useCase.task.io.DeleteTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskInput;
import ntut.csie.ezScrum.useCase.task.io.EditTaskOutput;
import ntut.csie.ezScrum.useCase.task.io.GetTaskInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardInput;
import ntut.csie.ezScrum.useCase.task.io.MoveTaskCardOutput;
import ntut.csie.ezScrum.useCase.task.io.TaskDTO;

public class TaskManagerUseCase {
	
	private ApplicationContext context;
	
	public TaskManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public AddTaskOutput addTask(AddTaskInput addTaskInput) {
		int orderId = context.getNumberOfTasks() + 1;
		Task task = null;
		try {
			task = TaskBuilder.newInstance().
					orderId(orderId).
					description(addTaskInput.getDescription()).
					estimate(addTaskInput.getEstimate()).
					notes(addTaskInput.getNotes()).
					backlogItemId(addTaskInput.getBacklogItemId()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addTask(task);
		AddTaskOutput addTaskOutput = new AddTaskOutput();
		addTaskOutput.setTaskId(task.getTaskId());
		return addTaskOutput;
	}
	
	public List<TaskDTO> getTasks(GetTaskInput getTaskInput){
		String backlogItemId = getTaskInput.getBacklogItemId();
		List<TaskDTO> taskList = new ArrayList<>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(convertTaskToGetTaskOutput(task));
			}
		}
		return taskList;
	}
	
	private TaskDTO convertTaskToGetTaskOutput(Task task) {
		TaskDTO getTaskOutput = new TaskDTO();
		getTaskOutput.setTaskId(task.getTaskId());
		getTaskOutput.setOrderId(task.getOrderId());
		getTaskOutput.setDescription(task.getDescription());
		getTaskOutput.setHandlerId(task.getHandlerId());
		getTaskOutput.setStatus(task.getStatus());
		getTaskOutput.setEstimate(task.getEstimate());
		getTaskOutput.setRemains(task.getRemains());
		getTaskOutput.setNotes(task.getNotes());
		getTaskOutput.setBacklogItemId(task.getBacklogItemId());
		return getTaskOutput;
	}
	
	public EditTaskOutput editTask(EditTaskInput editTaskInput) {
		String taskId = editTaskInput.getTaskId();
		Task task = context.getTask(taskId);
		task.setDescription(editTaskInput.getDescription());
		task.setEstimate(editTaskInput.getEstimate());
		task.setRemains(editTaskInput.getRemains());
		task.setNotes(editTaskInput.getNotes());
		context.editTask(taskId, task);
		EditTaskOutput editTaskOutput = new EditTaskOutput();
		editTaskOutput.setEditSuccess(true);
		return editTaskOutput;
	}
	
	public DeleteTaskOutput deleteTask(DeleteTaskInput deleteTaskInput) {
		String taskId = deleteTaskInput.getTaskId();
		int orderId = context.getTask(taskId).getOrderId();
		int numberOfTasks = context.getNumberOfTasks();
		Task[] tasks = context.getTasks().toArray(new Task[numberOfTasks]);
		for(int i = orderId; i < numberOfTasks; i++) {
			tasks[i].setOrderId(i);
			context.editTask(tasks[i].getTaskId(), tasks[i]);
		}
		context.deleteTask(taskId);
		DeleteTaskOutput deleteTaskOutput = new DeleteTaskOutput();
		deleteTaskOutput.setDeleteSuccess(true);
		return deleteTaskOutput;
	}
	
	public MoveTaskCardOutput moveTaskCard(MoveTaskCardInput moveTaskCardInput) {
		String taskId = moveTaskCardInput.getTaskId();
		Task task = context.getTask(taskId);
		String status = moveTaskCardInput.getStatus();
		task.setStatus(status);
		if(status.equals("Done")) {
			task.setRemains(0);
		}
		context.editTask(taskId, task);
		MoveTaskCardOutput moveTaskCardOutput = new MoveTaskCardOutput();
		moveTaskCardOutput.setMoveTaskCardSuccess(true);
		return moveTaskCardOutput;
	}
}
