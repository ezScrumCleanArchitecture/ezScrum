package useCase;

import java.util.ArrayList;

import model.BacklogItem;

public class GetAllBacklogItem {
	
	public ArrayList<BacklogItem> execute(ApplicationContext context, String productId) {
		ArrayList<BacklogItem> backlogItemList = new ArrayList<BacklogItem>();
		for(BacklogItem backlogItem : context.getBacklogItems().values()) {
			if(backlogItem.getProductId() == productId) {
				backlogItemList.add(backlogItem);
			}
		}
		return backlogItemList;
	}

}
