package ntut.csie.ezScrum.restfulAPI.task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.restfulAPI.task.viewModel.AddTaskViewModel;
import ntut.csie.ezScrum.restfulAPI.task.viewModel.TaskTableViewModel;
import ntut.csie.ezScrum.restfulAPI.task.viewModel.TaskViewModel;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.TaskManagerUseCase;
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

@Path("/backlogItem/{backlogItemId}/task")
@Singleton
public class TaskRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	TaskManagerUseCase taskManagerUseCase = new TaskManagerUseCase(context);
	
	@POST
	@Path("/addTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaskViewModel addTask(@PathParam("backlogItemId") String backlogItemId,
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
		
		AddTaskInput addTaskInput = new AddTaskInput();
		addTaskInput.setDescription(description);
		addTaskInput.setEstimate(estimate);
		addTaskInput.setNotes(notes);
		addTaskInput.setBacklogItemId(backlogItemId);
		
		AddTaskOutput addTaskOutput = taskManagerUseCase.addTask(addTaskInput);
		String taskId = addTaskOutput.getTaskId();
		
		TaskViewModel addTaskViewModel = new AddTaskViewModel();
		addTaskViewModel.setTaskId(taskId);
		
		return addTaskViewModel;
	}
	
	@GET
	@Path("/getAllTask")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskTableViewModel> getAllTask(@PathParam("backlogItemId") String backlogItemId) {
		GetTaskInput getTaskInput = new GetTaskInput();
		getTaskInput.setBacklogItemId(backlogItemId);
		
		List<TaskDTO> taskDTOList = taskManagerUseCase.getTasks(getTaskInput);
		
		List<TaskTableViewModel> taskTableViewModelList = new ArrayList<>();
		for(TaskDTO taskDTO : taskDTOList) {
			TaskTableViewModel taskTableViewModel = new TaskTableViewModel();
			taskTableViewModel.setTaskId(taskDTO.getTaskId());
			taskTableViewModel.setOrderId(taskDTO.getOrderId());
			taskTableViewModel.setDescription(taskDTO.getDescription());
			taskTableViewModel.setHandlerId(taskDTO.getHandlerId());
			taskTableViewModel.setStatus(taskDTO.getStatus());
			taskTableViewModel.setEstimate(taskDTO.getEstimate());
			taskTableViewModel.setRemains(taskDTO.getRemains());
			taskTableViewModel.setNotes(taskDTO.getNotes());
			taskTableViewModel.setBacklogItemOrderId(context.getBacklogItem(backlogItemId).getOrderId());
			taskTableViewModelList.add(taskTableViewModel);
		}
		return taskTableViewModelList;
	}
	
	@POST
	@Path("/editTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditTaskOutput editTask(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		String description = "";
		int estimate = 0;
		int remains = 0;
		String notes = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
			description = taskJSON.getString("description");
			estimate = taskJSON.getInt("estimate");
			remains = taskJSON.getInt("remains");
			notes = taskJSON.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		EditTaskInput editTaskInput = new EditTaskInput();
		editTaskInput.setTaskId(taskId);
		editTaskInput.setDescription(description);
		editTaskInput.setEstimate(estimate);
		editTaskInput.setRemains(remains);
		editTaskInput.setNotes(notes);
		editTaskInput.setBacklogItemId(backlogItemId);
		
		EditTaskOutput editTaskOutput = taskManagerUseCase.editTask(editTaskInput);
		
		return editTaskOutput;
	}
	
	@DELETE
	@Path("/deleteTask")
	public DeleteTaskOutput deleteBacklogItem(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DeleteTaskInput deleteTaskInput = new DeleteTaskInput();
		deleteTaskInput.setTaskId(taskId);
		
		DeleteTaskOutput deleteTaskOutput = taskManagerUseCase.deleteTask(deleteTaskInput);
		
		return deleteTaskOutput;
	}
	
	@POST
	@Path("/moveTaskCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MoveTaskCardOutput moveTaskCard(@PathParam("backlogItemId") String backlogItemId,
			String taskInfo) {
		String taskId = "";
		String status = "";
		try {
			JSONObject taskJSON = new JSONObject(taskInfo);
			taskId = taskJSON.getString("taskId");
			status = taskJSON.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		MoveTaskCardInput moveTaskCardInput = new MoveTaskCardInput();
		moveTaskCardInput.setTaskId(taskId);
		moveTaskCardInput.setStatus(status);
		
		MoveTaskCardOutput moveTaskCardOutput = taskManagerUseCase.moveTaskCard(moveTaskCardInput);
		
		return moveTaskCardOutput;
	}
}
