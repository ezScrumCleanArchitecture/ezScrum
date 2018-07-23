package ntut.csie.ezScrum.useCase.retrospective;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.io.RetrospectiveModel;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveOutput;

public class GetAllRetrospectiveUseCaseImpl implements GetAllRetrospectiveUseCase, GetAllRetrospectiveInput{
	private ApplicationContext context;
	
	private String productId;
	
	public GetAllRetrospectiveUseCaseImpl() {}
	
	public GetAllRetrospectiveUseCaseImpl(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void execute(GetAllRetrospectiveInput input, GetAllRetrospectiveOutput output) {
		String productId = input.getProductId();
		List<RetrospectiveModel> retrospectiveList = new ArrayList<>();
		for(Retrospective retrospective : context.getRetrospectives()) {
			if(retrospective.getProductId().equals(productId)) {
				retrospectiveList.add(convertRetrospectiveToDTO(retrospective));
			}
		}
		output.setRetrospectiveList(retrospectiveList);
	}
	
	private RetrospectiveModel convertRetrospectiveToDTO(Retrospective retrospective) {
		RetrospectiveModel dto = new RetrospectiveModel();
		dto.setRetrospectiveId(retrospective.getRetrospectiveId());
		dto.setOrderId(retrospective.getOrderId());
		dto.setDescription(retrospective.getDescription());
		dto.setProductId(retrospective.getProductId());
		Sprint sprint = context.getSprint(retrospective.getSprintId());
		if(sprint != null) {
			dto.setSprintOrderId(sprint.getOrderId());
		}
		dto.setCreateTime(retrospective.getCreateTime());
		dto.setUpdateTime(retrospective.getUpdateTime());
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
