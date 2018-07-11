package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.GetAllBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemOutput;
import ntut.csie.ezScrum.useCase.backlogItem.io.GetAllBacklogItemDTO;

@Path("/product/{productId}/backlogItem")
@Singleton
public class GetAllBacklogItemRestfulAPI implements GetAllBacklogItemOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private GetAllBacklogItemUseCase getBacklogItemUseCase = new GetAllBacklogItemUseCaseImpl(context);
	
	private List<GetAllBacklogItemDTO> backlogItemList;
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllBacklogItemOutput getAllBacklogItem(@PathParam("productId") String productId) {
		GetAllBacklogItemInput input = new GetAllBacklogItemUseCaseImpl();
		input.setProductId(productId);
		
		GetAllBacklogItemOutput output = this;
		
		getBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<GetAllBacklogItemDTO> getBacklogItemList() {
		return backlogItemList;
	}

	@Override
	public void setBacklogItemList(List<GetAllBacklogItemDTO> backlogItemList) {
		this.backlogItemList = backlogItemList;
	}
	
	
	
}
