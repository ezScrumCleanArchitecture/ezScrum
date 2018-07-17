package ntut.csie.ezScrum.unitTest.factory;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.backlogItem.BacklogItemBuilder;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.sprint.SprintBuilder;
import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.model.task.TaskBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class TestFactory {
	private ApplicationContext context = ApplicationContext.getInstance();
	
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
		context.addProduct(product);
		return product;
	}
	
	public BacklogItem getNewBacklogItem(String productId, String description) {
		int orderId =context.getNumberOfBacklogItems()+1;
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
		context.addBacklogItem(backlogItem);
		return backlogItem;
	}
	
	public Sprint getNewSprint(String productId, String goal, int interval, String startDate, String endDate, String demoDate) {
		int orderId = context.getNumberOfSprints() + 1;
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
		context.addSprint(sprint);
		return sprint;
	}
	
	public Task getNewTask(String backlogItemId, String description) {
		int orderId = context.getNumberOfTasks() + 1;
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
		context.addTask(task);
		return task;
	}
}
