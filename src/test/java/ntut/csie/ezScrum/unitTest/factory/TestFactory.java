package ntut.csie.ezScrum.unitTest.factory;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.backlogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.retrospective.RetrospectiveBuilder;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.sprint.SprintBuilder;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.unitTest.repository.FakeBacklogItemRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeProductRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeRetrospectiveRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeSprintRepository;
import ntut.csie.ezScrum.unitTest.repository.FakeTaskRepository;

public class TestFactory {
	
	private FakeProductRepository fakeProductRepository;
	private FakeSprintRepository fakeSprintRepository;
	private FakeBacklogItemRepository fakeBacklogItemRepository;
	private FakeTaskRepository fakeTaskRepository;
	private FakeRetrospectiveRepository fakeRetrospectiveRepository;
	
	public TestFactory(FakeProductRepository fakeProductRepository, FakeSprintRepository fakeSprintRepository,
			FakeBacklogItemRepository fakeBacklogItemRepository, FakeTaskRepository fakeTaskRepository,
			FakeRetrospectiveRepository fakeRetrospectiveRepository) {
		this.fakeProductRepository = fakeProductRepository;
		this.fakeSprintRepository = fakeSprintRepository;
		this.fakeBacklogItemRepository = fakeBacklogItemRepository;
		this.fakeTaskRepository = fakeTaskRepository;
		this.fakeRetrospectiveRepository = fakeRetrospectiveRepository;
	}
	
	public Product getNewProduct() {
		Product product = null;
		try {
			product = ProductBuilder.newInstance().
					name("ezScrum").
					comment("This is the comment for ezScrum Product.").
					build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		fakeProductRepository.add(product);
		return product;
	}
	
	public BacklogItem getNewBacklogItem(String productId, String description) {
		int orderId = fakeBacklogItemRepository.getNumberOfItems() + 1;
		BacklogItem backlogItem = null;
		try {
			backlogItem = BacklogItemBuilder.newInstance().
					orderId(orderId).
					productId(productId).
					description(description).
					estimate(8).
					importance(90).
					notes("The note of backlog item.").
					build();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		fakeBacklogItemRepository.add(backlogItem);
		return backlogItem;
	}
	
	public Sprint getNewSprint(String productId, String goal, int interval, String startDate, String endDate, String demoDate) {
		int orderId = fakeSprintRepository.getNumberOfItems() + 1;
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					orderId(orderId).
					goal(goal).
					interval(interval).
					startDate(startDate).
					endDate(endDate).
					demoDate(demoDate).
					demoPlace("The place for demo ezScrum").
					productId(productId).
					build();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		fakeSprintRepository.add(sprint);
		return sprint;
	}
	
	public Task getNewTask(String backlogItemId, String description) {
		int orderId = fakeTaskRepository.getNumberOfItems() + 1;
		Task task = null;
		try {
			task = TaskBuilder.newInstance().
					orderId(orderId).
					description(description).
					estimate(5).
					notes("Please use factory pattern to add task test data.").
					backlogItemId(backlogItemId).
					build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		fakeTaskRepository.add(task);
		return task;
	}
	
	public Retrospective getNewRetrospective(String productId, String sprintId, String description) {
		int orderId = fakeRetrospectiveRepository.getNumberOfItems() + 1;
		Retrospective retrospective = null;
		try {
			retrospective = RetrospectiveBuilder.newInstance().
					orderId(orderId).
					productId(productId).
					sprintId(sprintId).
					description(description).
					build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		fakeRetrospectiveRepository.add(retrospective);
		return retrospective;
	}
	
}
