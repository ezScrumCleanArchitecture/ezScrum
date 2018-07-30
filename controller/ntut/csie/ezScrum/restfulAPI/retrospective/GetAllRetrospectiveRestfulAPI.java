package ntut.csie.ezScrum.restfulAPI.retrospective;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.retrospective.RetrospectiveRepository;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCase;
import ntut.csie.ezScrum.useCase.retrospective.GetAllRetrospectiveUseCaseImpl;
import ntut.csie.ezScrum.useCase.retrospective.io.RetrospectiveModel;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveInput;
import ntut.csie.ezScrum.useCase.retrospective.io.GetAllRetrospectiveOutput;

@Path("/product/{productId}/retrospective")
public class GetAllRetrospectiveRestfulAPI implements GetAllRetrospectiveOutput{
	
	private Repository<Retrospective> retrospectiveRepository = new RetrospectiveRepository();
	private Repository<Sprint> sprintRepository = new SprintRepository();
	private GetAllRetrospectiveUseCase getAllRetrospectiveUseCase = new GetAllRetrospectiveUseCaseImpl(retrospectiveRepository, sprintRepository);
	
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
