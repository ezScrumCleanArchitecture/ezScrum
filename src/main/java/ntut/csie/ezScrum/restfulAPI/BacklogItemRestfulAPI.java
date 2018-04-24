package ntut.csie.ezScrum.restfulAPI;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.CreateBacklogItem;
import ntut.csie.ezScrum.useCase.GetAllBacklogItem;

@Path("/product/{productId}/backlogItem")
@Singleton
public class BacklogItemRestfulAPI {
	
	public ApplicationContext context = ApplicationContext.newInstance();
	
	@POST
	@Path("/addBacklogItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addBacklogItem(@PathParam("productId") String productId,
			String backlogItemInfo) {
		String description = "";
		int estimate = 0;
		int importance = 0;
		String notes = "";
		try {
			JSONObject backlogItem = new JSONObject(backlogItemInfo);
			description = backlogItem.getString("description");
			estimate = backlogItem.getInt("estimate");
			importance = backlogItem.getInt("importance");
			notes = backlogItem.getString("notes");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateBacklogItem createBacklogItemUseCase = new CreateBacklogItem();
		String backlogItemId = createBacklogItemUseCase.execute(context, productId, description, estimate, importance, notes);
		return backlogItemId;
	}
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<BacklogItem> getAllBacklogItem(@PathParam("productId") String productId) {
		GetAllBacklogItem getAllBacklogItemUseCase = new GetAllBacklogItem();
		ArrayList<BacklogItem> backlogItemList = getAllBacklogItemUseCase.execute(context, productId);
		return backlogItemList;
	}
	
}
