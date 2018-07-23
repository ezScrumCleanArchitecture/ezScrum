package ntut.csie.ezScrum.useCase.sprint;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.sprint.io.SprintModel;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintOutput;

public class GetAllSprintUseCaseImpl implements GetAllSprintUseCase, GetAllSprintInput{
	private ApplicationContext context;
	private String productId;
	
	public GetAllSprintUseCaseImpl() {}
	
	public GetAllSprintUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(GetAllSprintInput input, GetAllSprintOutput output) {
		String productId = input.getProductId();
		List<SprintModel> sprintList = new ArrayList<>();
		for(Sprint sprint : context.getSprints()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(convertSprintToDTO(sprint));
			}
		}
		output.setSprintList(sprintList);
	}
	
	private SprintModel convertSprintToDTO(Sprint sprint) {
		SprintModel dto = new SprintModel();
		dto.setSprintId(sprint.getSprintId());
		dto.setOrderId(sprint.getOrderId());
		dto.setGoal(sprint.getGoal());
		dto.setStartDate(sprint.getStartDate());
		dto.setInterval(sprint.getInterval());
		dto.setEndDate(sprint.getEndDate());
		dto.setDemoDate(sprint.getDemoDate());
		dto.setDemoPlace(sprint.getDemoPlace());
		dto.setTeamSize(sprint.getTeamSize());
		dto.setDaily(sprint.getDaily());
		dto.setProductId(sprint.getProductId());
		dto.setSprintOverdue(sprint.isSprintOverdue());
		dto.setCreateTime(sprint.getCreateTime());
		dto.setUpdateTime(sprint.getUpdateTime());
		return dto;
	}
	
	@Override
	public String getProductId() {
		return productId;
	}
	
	@Override
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
