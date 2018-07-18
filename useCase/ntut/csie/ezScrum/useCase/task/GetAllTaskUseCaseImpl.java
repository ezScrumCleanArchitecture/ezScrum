package ntut.csie.ezScrum.useCase.task;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskDTO;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskInput;
import ntut.csie.ezScrum.useCase.task.io.GetAllTaskOutput;

public class GetAllTaskUseCaseImpl implements GetAllTaskUseCase, GetAllTaskInput {
	private ApplicationContext context;
	
	private String backlogItemId;
	
	public GetAllTaskUseCaseImpl() {}
	
	public GetAllTaskUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(GetAllTaskInput input, GetAllTaskOutput output) {
		String backlogItemId = input.getBacklogItemId();
		List<GetAllTaskDTO> taskList = new ArrayList<>();
		for(Task task : context.getTasks()) {
			if(task.getBacklogItemId().equals(backlogItemId)) {
				taskList.add(convertTaskToDTO(task));
			}
		}
		output.setTaskList(taskList);
	}
	
	private GetAllTaskDTO convertTaskToDTO(Task task) {
		GetAllTaskDTO dto = new GetAllTaskDTO();
		dto.setTaskId(task.getTaskId());
		dto.setOrderId(task.getOrderId());
		dto.setDescription(task.getDescription());
		dto.setHandlerId(task.getHandlerId());
		dto.setStatus(task.getStatus());
		dto.setEstimate(task.getEstimate());
		dto.setRemains(task.getRemains());
		dto.setNotes(task.getNotes());
		dto.setBacklogItemId(task.getBacklogItemId());
		return dto;
	}
	
	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
