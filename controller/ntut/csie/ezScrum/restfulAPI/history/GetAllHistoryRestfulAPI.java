package ntut.csie.ezScrum.restfulAPI.history;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.repository.history.HistoryRepository;
import ntut.csie.ezScrum.useCase.Repository;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCase;
import ntut.csie.ezScrum.useCase.history.GetAllHistoryUseCaseImpl;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryInput;
import ntut.csie.ezScrum.useCase.history.io.GetAllHistoryOutput;
import ntut.csie.ezScrum.useCase.history.io.HistoryModel;

@Path("/history/{issueId}")
public class GetAllHistoryRestfulAPI implements GetAllHistoryOutput{
	
	private Repository<History> historyRepository = new HistoryRepository();
	private GetAllHistoryUseCase getAllHistoryUseCase = new GetAllHistoryUseCaseImpl(historyRepository);
	
	private List<HistoryModel> historyList;
	
	@GET
	@Path("/getAllHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllHistoryOutput getAllHistory(@PathParam("issueId") String issueId) {
		GetAllHistoryInput input = new GetAllHistoryUseCaseImpl();
		input.setIssueId(issueId);
		
		GetAllHistoryOutput output = this;
		
		getAllHistoryUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<HistoryModel> getHistoryList() {
		return historyList;
	}

	@Override
	public void setHistoryList(List<HistoryModel> historyList) {
		this.historyList = historyList;
	}

}
