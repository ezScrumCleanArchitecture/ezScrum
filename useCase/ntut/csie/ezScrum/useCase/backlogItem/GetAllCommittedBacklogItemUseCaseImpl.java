package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemOutput;

public class GetAllCommittedBacklogItemUseCaseImpl implements GetAllCommittedBacklogItemUseCase, GetAllCommittedBacklogItemInput{
	
	private ApplicationContext context;
	
	private String productId;
	private String sprintId;
	
	public GetAllCommittedBacklogItemUseCaseImpl() {};

	public GetAllCommittedBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void execute(GetAllCommittedBacklogItemInput input, GetAllCommittedBacklogItemOutput output) {
		String productId = input.getProductId();
		String sprintId = input.getSprintId();
		List<GetAllCommittedBacklogItemDTO> committedBacklogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				if(backlogItem.getSprintId() != null) {
					if(backlogItem.getSprintId().equals(sprintId)) {
						committedBacklogItemList.add(convertBacklogItemToDTO(backlogItem));
					}
				}
			}
		}
		Collections.sort(committedBacklogItemList, new Comparator<GetAllCommittedBacklogItemDTO>() {
			@Override
			public int compare(GetAllCommittedBacklogItemDTO dto1, GetAllCommittedBacklogItemDTO dto2) {
				return dto2.getImportance() - dto1.getImportance();
			}
			
		});
		output.setCommittedBacklogItemList(committedBacklogItemList);
	}

	private GetAllCommittedBacklogItemDTO convertBacklogItemToDTO(BacklogItem backlogItem) {
		GetAllCommittedBacklogItemDTO dto = new GetAllCommittedBacklogItemDTO();
		dto.setBacklogItemId(backlogItem.getBacklogItemId());
		dto.setOrderId(backlogItem.getOrderId());
		dto.setDescription(backlogItem.getDescription());
		dto.setEstimate(backlogItem.getEstimate());
		dto.setImportance(backlogItem.getImportance());
		Sprint sprint = context.getSprint(backlogItem.getSprintId());
		dto.setSprintOrderId(sprint.getOrderId());
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

	@Override
	public String getSprintId() {
		return sprintId;
	}

	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
