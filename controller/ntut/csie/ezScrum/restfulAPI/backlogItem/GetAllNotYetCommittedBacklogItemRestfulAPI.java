package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.repository.backlogItem.BacklogItemRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.NotYetCommittedBacklogItemModel;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class GetAllNotYetCommittedBacklogItemRestfulAPI implements GetAllNotYetCommittedBacklogItemOutput{
	
	private Repository<BacklogItem> backlogItemRepository = new BacklogItemRepository();
	private GetAllNotYetCommittedBacklogItemUseCase getNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(backlogItemRepository);
	
	private List<NotYetCommittedBacklogItemModel> notYetCommittedBacklogItemList;

	@GET
	@Path("/getAllNotYetCommittedBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllNotYetCommittedBacklogItemOutput getAllNotYetCommittedBacklogItem(@PathParam("productId") String productId) {
		GetAllNotYetCommittedBacklogItemInput input = new GetAllNotYetCommittedBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllNotYetCommittedBacklogItemOutput output = this;
		getNotYetCommittedBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<NotYetCommittedBacklogItemModel> getNotYetCommittedBacklogItemList() {
		return notYetCommittedBacklogItemList;
	}

	@Override
	public void setNotYetCommittedBacklogItemList(
			List<NotYetCommittedBacklogItemModel> notYetCommittedBacklogItemList) {
		this.notYetCommittedBacklogItemList = notYetCommittedBacklogItemList;
	}
	
}
