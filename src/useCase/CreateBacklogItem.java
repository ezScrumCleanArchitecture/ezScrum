package useCase;

import factory.domainModel.BacklogItemFactory;
import model.BacklogItem;

public class CreateBacklogItem {
	
	public String execute(ApplicationContext context, String productId,String description,int estimate,int importance,String notes) {
		BacklogItemFactory backlogItemFactory = new BacklogItemFactory(productId, description, estimate, importance, notes);
		BacklogItem backlogItem = (BacklogItem) backlogItemFactory.createDomainModel();
		context.getBacklogItems().put(backlogItem.getBacklogItemId(), backlogItem);
		return backlogItem.getBacklogItemId();
	}
}
