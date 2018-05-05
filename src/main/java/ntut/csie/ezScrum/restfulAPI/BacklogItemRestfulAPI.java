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
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemDTO;
import ntut.csie.ezScrum.useCase.BacklogItem.BacklogItemManagerUseCase;

@Path("/product/{productId}/backlogItem")
@Singleton
public class BacklogItemRestfulAPI {
	
	ApplicationContext context = ApplicationContext.getInstance();
	
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
			e.printStackTrace();
		}
		BacklogItemManagerUseCase backlogItemUseCase = new BacklogItemManagerUseCase(context);
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					productId(productId).
					description(description).
					estimate(estimate).
					importance(importance).
					notes(notes).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String backlogItemId = backlogItemUseCase.addBacklogItem(backlogItem);
		return backlogItemId;
	}
	
	@GET
	@Path("/getAllBacklogItem")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<BacklogItemDTO> getAllBacklogItem(@PathParam("productId") String productId) {
		BacklogItemManagerUseCase backlogItemManagerUseCase = new BacklogItemManagerUseCase(context);
		ArrayList<BacklogItemDTO> backlogItemList = backlogItemManagerUseCase.getBacklogItemsForUI(productId);
		return backlogItemList;
	}
	
}
