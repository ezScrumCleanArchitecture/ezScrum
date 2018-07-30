package ntut.csie.ezScrum.repository.task;

import ntut.csie.ezScrum.model.task.Task;

public class TaskMapper {
	
	public Task transformToTask(TaskData data) {
		Task task = new Task();
		task.setTaskId(data.getTaskId());
		task.setOrderId(data.getOrderId());
		task.setDescription(data.getDescription());
		task.setHandlerId(data.getHandlerId());
		task.setStatus(data.getStatus());
		task.setEstimate(data.getEstimate());
		task.setRemains(data.getRemains());
		task.setNotes(data.getNotes());
		task.setBacklogItemId(data.getBacklogItemId());
		task.setCreateTime(data.getCreateTime());
		task.setUpdateTime(data.getUpdateTime());
		return task;
	}
	
	public TaskData transformToTaskData(Task task) {
		TaskData data = new TaskData();
		data.setTaskId(task.getTaskId());
		data.setOrderId(task.getOrderId());
		data.setDescription(task.getDescription());
		data.setHandlerId(task.getHandlerId());
		data.setStatus(task.getStatus());
		data.setEstimate(task.getEstimate());
		data.setRemains(task.getRemains());
		data.setNotes(task.getNotes());
		data.setBacklogItemId(task.getBacklogItemId());
		data.setCreateTime(task.getCreateTime());
		data.setUpdateTime(task.getUpdateTime());
		return data;
	}
	
}
