package ntut.csie.ezScrum.restfulAPI.Task;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Task.TaskInputDTO;
import ntut.csie.ezScrum.useCase.Task.TaskOutputDTO;
import ntut.csie.ezScrum.useCase.Task.TaskManagerUseCase;

@Path("/backlogItem/{backlogItemId}/task")
@Singleton
public class TaskRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	
	@POST
	@Path("/addTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addBacklogItem(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String description = "";
		int estimate = 0;
		String notes = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			description = taskJSON.getString("description");
			estimate = taskJSON.getInt("estimate");
			notes = taskJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		
		TaskInputDTO taskInputDTO = new TaskInputDTO();
		taskInputDTO.setDescription(description);
		taskInputDTO.setEstimate(estimate);
		taskInputDTO.setNotes(notes);
		taskInputDTO.setBacklogItemId(backlogItemId);
		
		String taskId = taskManagerUseCase.addTask(taskInputDTO);
		return taskId;
	}
	
	@GET
	@Path("/getAllTask")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskOutputDTO> getAllBacklogItem(@PathParam("backlogItemId") String backlogItemId) {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		List<TaskOutputDTO> taskList = taskManagerUseCase.getTasksForUI(backlogItemId);
		return taskList;
	}
	
}
