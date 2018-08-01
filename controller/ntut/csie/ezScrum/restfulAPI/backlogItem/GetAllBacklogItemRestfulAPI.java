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
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.BacklogItemModel;

@Path("/product/{productId}/backlogItem")
public class GetAllBacklogItemRestfulAPI implements GetAllBacklogItemOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private Repository<Sprint> sprintRepository = new SprintRepository();
	private GetAllBacklogItemUseCase getAllBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(backlogItemRepository, sprintRepository);
	
	private List<BacklogItemModel> backlogItemList;
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllBacklogItemOutput getAllBacklogItem(@PathParam("productId") String productId) {
		GetAllBacklogItemInput input = new GetAllBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllBacklogItemOutput output = this;
		
		getAllBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<BacklogItemModel> getBacklogItemList() {
		return backlogItemList;
	}

	@Override
	public void setBacklogItemList(List<BacklogItemModel> backlogItemList) {
		this.backlogItemList = backlogItemList;
	}
	
}
