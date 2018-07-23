package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemOutput;

public class GetAllNotYetCommittedBacklogItemUseCaseImpl implements GetAllNotYetCommittedBacklogItemUseCase, GetAllNotYetCommittedBacklogItemInput{

	private ApplicationContext context;
	
	private String productId;

	public GetAllNotYetCommittedBacklogItemUseCaseImpl() {};
	
	public GetAllNotYetCommittedBacklogItemUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(GetAllNotYetCommittedBacklogItemInput input, GetAllNotYetCommittedBacklogItemOutput output) {
		String productId = input.getProductId();
		List<NotYetCommittedBacklogItemModel> notYetCommittedBacklogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : context.getBacklogItems()) {
			if(backlogItem.getProductId().equals(productId)) {
				if(backlogItem.getSprintId() == null) {
					notYetCommittedBacklogItemList.add(convertBacklogItemToDTO(backlogItem));
				}
			}
		}
		output.setNotYetCommittedBacklogItemList(notYetCommittedBacklogItemList);
	}
	
	private NotYetCommittedBacklogItemModel convertBacklogItemToDTO(BacklogItem backlogItem) {
		NotYetCommittedBacklogItemModel dto = new NotYetCommittedBacklogItemModel();
		dto.setBacklogItemId(backlogItem.getBacklogItemId());
		dto.setOrderId(backlogItem.getOrderId());
		dto.setDescription(backlogItem.getDescription());
		dto.setEstimate(backlogItem.getEstimate());
		dto.setImportance(backlogItem.getImportance());
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
