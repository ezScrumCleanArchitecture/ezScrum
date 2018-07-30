package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.BacklogItemModel;

public class GetAllBacklogItemUseCaseImpl implements GetAllBacklogItemUseCase, GetAllBacklogItemInput{

	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Sprint> sprintRepository;
	
	private String productId;

	public GetAllBacklogItemUseCaseImpl() {};
	
	public GetAllBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<Sprint> sprintRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(GetAllBacklogItemInput input, GetAllBacklogItemOutput output) {
		String productId = input.getProductId();
		List<BacklogItemModel> backlogItemList = new ArrayList<>();
		
		for(BacklogItem backlogItem : backlogItemRepository.getAll()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(convertBacklogItemToDTO(backlogItem));
			}
		}
		output.setBacklogItemList(backlogItemList);
	}

	private BacklogItemModel convertBacklogItemToDTO(BacklogItem backlogItem) {
		BacklogItemModel dto = new BacklogItemModel();
		dto.setBacklogItemId(backlogItem.getBacklogItemId());
		dto.setOrderId(backlogItem.getOrderId());
		dto.setDescription(backlogItem.getDescription());
		dto.setEstimate(backlogItem.getEstimate());
		dto.setImportance(backlogItem.getImportance());
		dto.setProductId(backlogItem.getProductId());
		Sprint sprint = sprintRepository.get(backlogItem.getSprintId());
		if(sprint != null) {
			dto.setSprintOrderId(sprint.getOrderId());
		}
		dto.setStatus(backlogItem.getStatus());
		dto.setNotes(backlogItem.getNotes());
		dto.setCreateTime(backlogItem.getCreateTime());
		dto.setUpdateTime(backlogItem.getUpdateTime());
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
