package ntut.csie.ezScrum.useCase.sprint;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.sprint.io.SprintModel;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintOutput;

public class GetAllSprintUseCaseImpl implements GetAllSprintUseCase, GetAllSprintInput{
	
	private Repository<Sprint> sprintRepository = new SprintRepository();
	
	private String productId;
	
	public GetAllSprintUseCaseImpl() {}
	
	public GetAllSprintUseCaseImpl(Repository<Sprint> sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(GetAllSprintInput input, GetAllSprintOutput output) {
		String productId = input.getProductId();
		List<SprintModel> sprintList = new ArrayList<>();
		for(Sprint sprint : sprintRepository.getAll()) {
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
