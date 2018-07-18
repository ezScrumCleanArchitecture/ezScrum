package ntut.csie.ezScrum.restfulAPI.backlogItem;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCase;
import ntut.csie.ezScrum.useCase.backlogItem.AssignBacklogItemUseCaseImpl;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemInput;
import ntut.csie.ezScrum.useCase.backlogItem.io.AssignBacklogItemOutput;

@Path("/product/{productId}/backlogItem")
public class AssignBacklogItemRestfulAPI implements AssignBacklogItemOutput{
	
	private ApplicationContext context = ApplicationContext.getInstance();
	private AssignBacklogItemUseCase assignBacklogItemUseCase = new AssignBacklogItemUseCaseImpl(context);
	
	@POST
	@Path("/assignBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void assignBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String sprintId = "";
		String backlogItemIds = "";
		List<String> backlogItemIdList = new ArrayList<>();
		try {
			JSONObject backlogItemJSON = new JSONObject(backlogItemInfo);
			sprintId = backlogItemJSON.getString("sprintId");
			backlogItemIds = backlogItemJSON.getString("backlogItemIds");
			JSONArray backlogItemIdsJSON = new JSONArray(backlogItemIds);
			for(int i = 0; i < backlogItemIdsJSON.length(); i++) {
				backlogItemIdList.add(backlogItemIdsJSON.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		AssignBacklogItemInput input = new AssignBacklogItemUseCaseImpl();
		AssignBacklogItemOutput output = this;
		for(String backlogItemId : backlogItemIdList) {
			input.setSprintId(sprintId);
			input.setBacklogItemId(backlogItemId);
			assignBacklogItemUseCase.execute(input, output);
		}
	}
}
