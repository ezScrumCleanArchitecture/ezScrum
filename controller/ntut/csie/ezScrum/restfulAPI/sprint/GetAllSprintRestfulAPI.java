package ntut.csie.ezScrum.restfulAPI.sprint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.repository.sprint.SprintRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCase;
import ntut.csie.ezScrum.useCase.sprint.GetAllSprintUseCaseImpl;
import ntut.csie.ezScrum.useCase.sprint.io.SprintModel;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintInput;
import ntut.csie.ezScrum.useCase.sprint.io.GetAllSprintOutput;

@Path("/product/{productId}/sprint")
public class GetAllSprintRestfulAPI implements GetAllSprintOutput{
	
	private Repository<Sprint> sprintRepository = new SprintRepository();
	private GetAllSprintUseCase getAllSprintUseCase = new GetAllSprintUseCaseImpl(sprintRepository);
	
	private List<SprintModel> sprintList;

	@GET
	@Path("/getAllSprint")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllSprintOutput getAllSprint(@PathParam("productId") String productId) {
		GetAllSprintInput input = new GetAllSprintUseCaseImpl();
		input.setProductId(productId);
		
		GetAllSprintOutput output = this;
		
		getAllSprintUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public List<SprintModel> getSprintList() {
		return sprintList;
	}

	@Override
	public void setSprintList(List<SprintModel> sprintList) {
		this.sprintList = sprintList;
	}
	
}
