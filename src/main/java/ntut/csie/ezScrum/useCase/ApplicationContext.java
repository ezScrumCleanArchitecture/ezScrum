package ntut.csie.ezScrum.useCase;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

import ntut.csie.ezScrum.model.BacklogItem;
import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.model.Sprint;
import ntut.csie.ezScrum.model.Task;

public class ApplicationContext {
	private static ApplicationContext instance=null;
	private Map<String, Product> products;
	private Map<String, BacklogItem> backlogItems;
	private Map<String, Sprint> sprints;
	private Map<String, Task> tasks;
	
	private ApplicationContext() {
		products = Collections.synchronizedMap(new LinkedHashMap<String, Product>());
		backlogItems = Collections.synchronizedMap(new LinkedHashMap<String, BacklogItem>());
		sprints = Collections.synchronizedMap(new LinkedHashMap<String, Sprint>());
		tasks = Collections.synchronizedMap(new LinkedHashMap<String, Task>());
	}
	public static ApplicationContext getInstance() {
		if(instance == null) {
			instance = new ApplicationContext();
		}
		return instance;
	}
	
	public void addProduct(Product product) {
		products.put(product.getProductId(), product);
	}
	
	public void addBacklogItem(BacklogItem backlogItem) {
		backlogItems.put(backlogItem.getBacklogItemId(), backlogItem);
	}
	
	public void addSprint(Sprint sprint) {
		sprints.put(sprint.getSprintId(), sprint);
	}
	
	public void addTask(Task task) {
		tasks.put(task.getTaskId(), task);
	}
	
	public Collection<Product> getProducts() {
		return products.values();
	}
	
	public Product getProduct(String productId) {
		return products.get(productId);
	}
	
	public Collection<BacklogItem> getBacklogItems() {
		return backlogItems.values();
	}
	
	public BacklogItem getBacklogItem(String backlogItemId) {
		return backlogItems.get(backlogItemId);
	}
	
	public Collection<Sprint> getSprints() {
		return sprints.values();
	}
	
	public Sprint getSprint(String sprintId) {
		return sprints.get(sprintId);
	}
	
	public Collection<Task> getTasks(){
		return tasks.values();
	}
	
	public Task getTask(String taskId) {
		return tasks.get(taskId);
	}
	
	public void clearProducts() {
		products.clear();
	}
	
	public void clearBacklogItems() {
		backlogItems.clear();
	}
	
	public void clearSprints() {
		sprints.clear();
	}
	
	public void clearTasks() {
		tasks.clear();
	}
}
