
package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.CommittedBacklogItem;
import useCase.CreateBacklogItem;
import useCase.CreateCommittedBacklogItem;

public class CommittedBacklogItemTest {
	CreateBacklogItem newStory;

	@Before
	public void setUp() {
		newStory = new CreateBacklogItem();
	}
	@Test
	public void assignBacklogItemToSprint() {
		long productId = 1;
		String description = "As a ezScrum developer , I want to test addBacklogItem.";
		long sprintId = 1;
		CreateCommittedBacklogItem committedBacklogItem = new CreateCommittedBacklogItem();
		CommittedBacklogItem newCommittedBacklogItem = committedBacklogItem.addCommittedBacklogItem(sprintId,
				newStory.addBacklogItem(productId, description, 0, 0, null));
		assertEquals(1,newCommittedBacklogItem.getCommittedBacklogItemId());
	}
}
