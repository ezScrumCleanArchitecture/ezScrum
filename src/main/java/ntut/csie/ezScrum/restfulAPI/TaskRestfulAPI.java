package ntut.csie.ezScrum.restfulAPI;

import java.util.ArrayList;

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

import ntut.csie.ezScrum.model.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.Task.TaskBuilder;
import ntut.csie.ezScrum.useCase.Task.TaskDTO;
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
		Task task = null;
		try {
			task = TaskBuilder.newInstance().
					description(description).
					estimate(estimate).
					notes(notes).
					backlogItemId(backlogItemId).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String taskId = taskManagerUseCase.addTask(task);
		return taskId;
	}
	
	@GET
	@Path("/getAllTask")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TaskDTO> getAllBacklogItem(@PathParam("backlogItemId") String backlogItemId) {
		TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
		ArrayList<TaskDTO> taskList = taskManagerUseCase.getTasksForUI(backlogItemId);
		return taskList;
	}
	
}
