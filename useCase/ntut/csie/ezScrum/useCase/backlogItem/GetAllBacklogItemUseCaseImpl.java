package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemDTO;

public class GetAllBacklogItemUseCaseImpl implements GetAllBacklogItemUseCase, GetAllBacklogItemInput{

	private ApplicationContext context;
	private String productId;

	public GetAllBacklogItemUseCaseImpl() {};
	
	public GetAllBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(GetAllBacklogItemInput input, GetAllBacklogItemOutput output) {
		String productId = input.getProductId();
		List<GetAllBacklogItemDTO> backlogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				backlogItemList.add(convertBacklogItemToDTO(backlogItem));
			}
		}
		output.setBacklogItemList(backlogItemList);
	}

	private GetAllBacklogItemDTO convertBacklogItemToDTO(BacklogItem backlogItem) {
		GetAllBacklogItemDTO dto = new GetAllBacklogItemDTO();
		dto.setBacklogItemId(backlogItem.getBacklogItemId());
		dto.setOrderId(backlogItem.getOrderId());
		dto.setDescription(backlogItem.getDescription());
		dto.setEstimate(backlogItem.getEstimate());
		dto.setImportance(backlogItem.getImportance());
		dto.setProductId(backlogItem.getProductId());
		System.out.println("BacklogItem's sprint Id: " + backlogItem.getSprintId());
		for(Sprint sprint : context.getSprints()) {
			System.out.println(sprint.getSprintId());
		}
		Sprint sprint = context.getSprint(backlogItem.getSprintId());
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
