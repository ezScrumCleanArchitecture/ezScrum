package unitTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import factory.testData.BacklogItemTestDataFactory;
import factory.testData.ProductTestDataFactory;
import model.BacklogItem;
import useCase.ApplicationContext;
import useCase.CreateBacklogItem;
import useCase.GetAllBacklogItem;

public class BacklogItemTest {
	ApplicationContext context;
	String productId;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
		ProductTestDataFactory productTestDataFactory = new ProductTestDataFactory(context);
		productId = productTestDataFactory.createTestData();
	}
	
	@After
	public void tearDown() {
		context.getProducts().clear();
		context.getBacklogItems().clear();
	}

	@Test
	public void addBacklogItemTest() {
		CreateBacklogItem createBacklogItemUseCase = new CreateBacklogItem();
		String description = "As a ezScrum developer , I want to test addBacklogItem.";
		String backlogItemId1 = createBacklogItemUseCase.execute(context, productId, description, 0, 0, null);
		assertEquals(description, context.getBacklogItems().get(backlogItemId1).getDescription());
		assertEquals(backlogItemId1, context.getBacklogItems().get(backlogItemId1).getBacklogItemId());
		String backlogItemId2 = createBacklogItemUseCase.execute(context, productId, description, 0, 0, null);
		assertEquals(backlogItemId2, context.getBacklogItems().get(backlogItemId2).getBacklogItemId());
	}

	@Test
	public void getBacklogItemTest() {
		String[] description = {"As a ezScrum developer, I want to get the first backlog item.",
				"As a ezScrum developer, I want to get the second backlog item.",
				"As a ezScrum developer, I want to get the third backlog item."
		};
		for(int i=0; i<description.length; i++) {
			BacklogItemTestDataFactory backlogItemTestDataFactory = new BacklogItemTestDataFactory(context, productId, description[i], 0, 0, null);
			backlogItemTestDataFactory.createTestData();
		}
		
		GetAllBacklogItem getAllBacklogItem  = new GetAllBacklogItem();
		ArrayList<BacklogItem> backlogItemList = getAllBacklogItem.execute(context, productId);
		for(int i=0; i<backlogItemList.size(); i++) {
			assertEquals(description[i], backlogItemList.get(i).getDescription());
		}
		assertEquals(description.length, backlogItemList.size());
	}
}
