package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllNotYetCommittedBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemDTO;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllNotYetCommittedBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
@Singleton
public class GetAllNotYetCommittedBacklogItemRestfulAPI implements GetAllNotYetCommittedBacklogItemOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private GetAllNotYetCommittedBacklogItemUseCase getNotYetCommittedBacklogItemUseCase = new GetAllNotYetCommittedBacklogItemUseCaseImpl(context);
	
	private List<GetAllNotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList;

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
	public List<GetAllNotYetCommittedBacklogItemDTO> getNotYetCommittedBacklogItemList() {
		return notYetCommittedBacklogItemList;
	}

	@Override
	public void setNotYetCommittedBacklogItemList(
			List<GetAllNotYetCommittedBacklogItemDTO> notYetCommittedBacklogItemList) {
		this.notYetCommittedBacklogItemList = notYetCommittedBacklogItemList;
	}
}
