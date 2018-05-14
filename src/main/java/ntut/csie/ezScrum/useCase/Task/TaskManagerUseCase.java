package ntut.csie.ezScrum.useCase.Task;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class TaskManagerUseCase {
	
	private ApplicationContext context;
	
	public TaskManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public String addTask(TaskInputDTO taskInputDTO) {
		Task task = null;
		try {
			task = TaskBuilder.newInstance().
					description(taskInputDTO.getDescription()).
					estimate(taskInputDTO.getEstimate()).
					notes(taskInputDTO.getNotes()).
					backlogItemId(taskInputDTO.getBacklogItemId()).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addTask(task);
		return task.getTaskId();
	}
	
	public List<Task> getTasks(String backlogItemId){
		List<Task> taskList = new ArrayList<Task>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public List<TaskOutputDTO> getTasksForUI(String backlogItemId){
		List<TaskOutputDTO> taskList = new ArrayList<TaskOutputDTO>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(covertTaskToDTO(task));
			}
		}
		return taskList;
	}
	
	private TaskOutputDTO covertTaskToDTO(Task task) {
		TaskOutputDTO taskDTO = new TaskOutputDTO();
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
	
	public String editTask(String taskId, TaskInputDTO taskInputDTO) {
		Task task = context.getTask(taskId);
		task.setDescription(taskInputDTO.getDescription());
		task.setEstimate(taskInputDTO.getEstimate());
		task.setNotes(taskInputDTO.getNotes());
		context.editTask(task);
		return task.getTaskId();
	}
	
	public String deleteTask(String taskId) {
		context.deleteTask(taskId);
		return taskId;
	}
}
