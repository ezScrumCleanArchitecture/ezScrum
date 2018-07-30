package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.CommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllCommittedBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class GetAllCommittedBacklogItemRestfulAPI implements GetAllCommittedBacklogItemOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private Repository<Sprint> sprintRepository = new SprintRepository();
	private GetAllCommittedBacklogItemUseCase getCommittedBacklogItemUseCase = new GetAllCommittedBacklogItemUseCaseImpl(backlogItemRepository, sprintRepository);
	
	private List<CommittedBacklogItemModel> committedBacklogItemList;

	@GET
	@Path("/sprint/{sprintId}/getAllCommittedBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllCommittedBacklogItemOutput getAllCommittedBacklogItem(@PathParam("productId") String productId, 
			@PathParam("sprintId") String sprintId) {
		GetAllCommittedBacklogItemInput input = new GetAllCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		input.setSprintId(sprintId);
		
		GetAllCommittedBacklogItemOutput output = this;
		
		getCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<CommittedBacklogItemModel> getCommittedBacklogItemList() {
		return committedBacklogItemList;
	}

	@Override
	public void setCommittedBacklogItemList(List<CommittedBacklogItemModel> committedBacklogItemList) {
		this.committedBacklogItemList = committedBacklogItemList;
	}
	
}
