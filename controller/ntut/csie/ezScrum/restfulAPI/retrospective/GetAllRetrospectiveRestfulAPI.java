package ntut.csie.ezScrum.restfulAPI.retrospective;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.ApplicationContext;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.RetrospectiveModel;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
public class GetAllRetrospectiveRestfulAPI implements GetAllRetrospectiveOutput{
	
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetAllRetrospectiveUseCase getAllRetrospectiveUseCase = applicationContext.newGetAllRetrospectiveUseCase();
	
	private List<RetrospectiveModel> retrospectiveList;
	
	@GET
	@Path("/getAllRetrospective")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllRetrospectiveOutput getAllRetrospective(@PathParam("productId") String productId) {
		GetAllRetrospectiveInput input = new GetAllRetrospectiveUseCaseImpl();
		input.setProductId(productId);
		
		GetAllRetrospectiveOutput output = this;
		
		getAllRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public List<RetrospectiveModel> getRetrospectiveList() {
		return retrospectiveList;
	}

	@Override
	public void setRetrospectiveList(List<RetrospectiveModel> retrospectiveList) {
		this.retrospectiveList = retrospectiveList;
	}

}
