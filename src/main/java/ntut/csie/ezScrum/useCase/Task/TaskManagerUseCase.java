package ntut.csie.ezScrum.useCase.Task;

import java.util.ArrayList;

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
	
	public ArrayList<Task> getTasks(String backlogItemId){
		ArrayList<Task> taskList = new ArrayList<Task>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public ArrayList<TaskDTO> getTasksForUI(String backlogItemId){
		ArrayList<TaskDTO> taskList = new ArrayList<TaskDTO>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(covertTaskToDTO(task));
			}
		}
		return taskList;
	}
	
	private TaskDTO covertTaskToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setSerialId(task.getSerialId());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setHandlerId(task.getHandlerId());
		taskDTO.setStatus(task.getStatus());
		taskDTO.setEstimate(task.getEstimate());
		taskDTO.setRemain(task.getRemain());
		taskDTO.setNotes(task.getNotes());
		String backlogItemId = task.getBacklogItemId();
		taskDTO.setBacklogItemSerialId(context.getBacklogItem(backlogItemId).getSerialId());
		return taskDTO;
	}
	
	public String editTask(Task task) {
		context.editTask(task);
		return task.getTaskId();
	}
	
	public String deleteTask(String taskId) {
		context.deleteTask(taskId);
		return taskId;
	}
}
