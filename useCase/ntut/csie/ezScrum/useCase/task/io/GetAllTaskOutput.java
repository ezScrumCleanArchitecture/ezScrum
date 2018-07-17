package ntut.csie.ezScrum.useCase.task.io;

import java.util.List;

import ntut.csie.ezScrum.useCase.Output;

public interface GetAllTaskOutput extends Output{
	public List<GetAllTaskDTO> getTaskList();
	public void setTaskList(List<GetAllTaskDTO> taskList);
}
