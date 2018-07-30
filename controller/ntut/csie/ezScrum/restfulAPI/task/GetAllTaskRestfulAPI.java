package ntut.csie.ezScrum.restfulAPI.task;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.repository.task.TaskRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCase;
import ntut.csie.ezScrum.useCase.task.GetAllTaskUseCaseImpl;
import ntut.csie.ezScrum.useCase.task.io.TaskModel;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskInput;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskOutput;

@Path("/backlogItem/{backlogItemId}/task")
public class GetAllTaskRestfulAPI implements GetAllTaskOutput{
	
	private Repository<Task> taskRepository = new TaskRepository();
	private GetAllTaskUseCase getAllTaskUseCase = new GetAllTaskUseCaseImpl(taskRepository);
	
	private List<TaskModel> taskList;
	
	@GET
	@Path("/getAllTask")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllTaskOutput getAllTask(@PathParam("backlogItemId") String backlogItemId) {
		GetAllTaskInput input = new GetAllTaskUseCaseImpl();
		input.setBacklogItemId(backlogItemId);
		
		GetAllTaskOutput output = this;
		
		getAllTaskUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public List<TaskModel> getTaskList() {
		return taskList;
	}

	@Override
	public void setTaskList(List<TaskModel> taskList) {
		this.taskList = taskList;
	}
	
}
