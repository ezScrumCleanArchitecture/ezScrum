package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import useCase.CreateBacklogItem;

public class BacklogItemTest {
	CreateBacklogItem newStory;

	@Before
	public void setUp() {
		newStory = new CreateBacklogItem();
	}

	@Test
	public void addBacklogItemTest() {
		long productId = 1;
		String description = "As a ezScrum developer , I want to test addBacklogItem.";

		assertEquals(1, newStory.addBacklogItem(productId, description, 0, 0, null).getBacklogItemId());
		assertEquals(2, newStory.addBacklogItem(productId, description, 0, 0, null).getBacklogItemId());
		assertEquals(description, newStory.addBacklogItem(productId, description, 0, 0, null).getDescription());
	}

	
}
