package ntut.csie.ezScrum.useCase.backlogItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.io.CommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemOutput;

public class GetAllCommittedBacklogItemUseCaseImpl implements GetAllCommittedBacklogItemUseCase, GetAllCommittedBacklogItemInput{
	
	private Repository<BacklogItem> backlogItemRepository;
	private Repository<Sprint> sprintRepository;
	
	private String productId;
	private String sprintId;
	
	public GetAllCommittedBacklogItemUseCaseImpl() {};

	public GetAllCommittedBacklogItemUseCaseImpl(Repository<BacklogItem> backlogItemRepository, Repository<Sprint> sprintRepository) {
		this.backlogItemRepository = backlogItemRepository;
		this.sprintRepository = sprintRepository;
	}

	@Override
	public void execute(GetAllCommittedBacklogItemInput input, GetAllCommittedBacklogItemOutput output) {
		String productId = input.getProductId();
		String sprintId = input.getSprintId();
		List<CommittedBacklogItemModel> committedBacklogItemList = new ArrayList<>();
		for(BacklogItem backlogItem : backlogItemRepository.getAll()) {
			if(backlogItem.getProductId().equals(productId)) {
				if(backlogItem.getSprintId() != null) {
					if(backlogItem.getSprintId().equals(sprintId)) {
						committedBacklogItemList.add(convertBacklogItemToDTO(backlogItem));
					}
				}
			}
		}
		Collections.sort(committedBacklogItemList, new Comparator<CommittedBacklogItemModel>() {
			@Override
			public int compare(CommittedBacklogItemModel dto1, CommittedBacklogItemModel dto2) {
				return dto2.getImportance() - dto1.getImportance();
			}
			
		});
		output.setCommittedBacklogItemList(committedBacklogItemList);
	}

	private CommittedBacklogItemModel convertBacklogItemToDTO(BacklogItem backlogItem) {
		CommittedBacklogItemModel dto = new CommittedBacklogItemModel();
		dto.setBacklogItemId(backlogItem.getBacklogItemId());
		dto.setOrderId(backlogItem.getOrderId());
		dto.setDescription(backlogItem.getDescription());
		dto.setEstimate(backlogItem.getEstimate());
		dto.setImportance(backlogItem.getImportance());
		Sprint sprint = sprintRepository.get(backlogItem.getSprintId());
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
