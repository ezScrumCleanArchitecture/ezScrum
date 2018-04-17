package ntut.csie.ezScrum.factory.testData;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.CreateBacklogItem;

public class BacklogItemTestDataFactory implements TestDataFactory{
	
	private ApplicationContext context;
	private String productId;
	private String description;
	private int estimate;
	private int importance;
	private String notes;

	public BacklogItemTestDataFactory(ApplicationContext context) {
		this.context = context;
		ProductTestDataFactory productTestDataFactory = new ProductTestDataFactory(this.context);
		this.productId = productTestDataFactory.createTestData();
		this.description = "As a ezScrum developer , I want to test addBacklogItem.";
		this.estimate = 3;
		this.importance = 90;
		this.notes = "Please use factory pattern to add backlog item.";
	}
	
	public BacklogItemTestDataFactory(ApplicationContext context, String productId, String description,
			int estimate, int importance, String notes) {
		this.context = context;
		this.productId = productId;
		this.description = description;
		this.estimate = estimate;
		this.importance = importance;
		this.notes = notes;
	}
	
	public String createTestData() {
		CreateBacklogItem createBacklogItemUseCase = new CreateBacklogItem();
		String backlogItemId = createBacklogItemUseCase.execute(context, productId, description, estimate, importance, notes);
		return backlogItemId;
	}
}
