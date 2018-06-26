package ntut.csie.ezScrum.useCase;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.model.task.Task;

public class ApplicationContext {
	private static ApplicationContext instance = null;
	private Map<String, Product> products;
	private Map<String, BacklogItem> backlogItems;
	private Map<String, Sprint> sprints;
	private Map<String, Task> tasks;
	private Map<String, Retrospective> retrospectives;
	
	private ApplicationContext() {
		products = Collections.synchronizedMap(new LinkedHashMap<String, Product>());
		backlogItems = Collections.synchronizedMap(new LinkedHashMap<String, BacklogItem>());
		sprints = Collections.synchronizedMap(new LinkedHashMap<String, Sprint>());
		tasks = Collections.synchronizedMap(new LinkedHashMap<String, Task>());
		retrospectives = Collections.synchronizedMap(new LinkedHashMap<String, Retrospective>());
	}
	public static ApplicationContext getInstance() {
		if(instance == null) {
			instance = new ApplicationContext();
		}
		return instance;
	}
	
	public int getNumberOfProduct() {
		return products.size();
	}
	
	public int getNumberOfBacklogItems() {
		return backlogItems.size();
	}
	
	public int getNumberOfSprints() {
		return sprints.size();
	}
	
	public int getNumberOfTasks() {
		return tasks.size();
	}
	
	public int getNumberOfRetrospectives() {
		return retrospectives.size();
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
	
	public void addRetrospective(Retrospective retrospective) {
		retrospectives.put(retrospective.getRetrospectiveId(), retrospective);
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
	
	public Collection<Retrospective> getRetrospectives(){
		return retrospectives.values();
	}
	
	public Retrospective getRetrospective(String retrospectiveId) {
		return retrospectives.get(retrospectiveId);
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
	
	public void clearRetrospectives() {
		retrospectives.clear();
	}
	
	public void editProduct(Product product) {
		products.replace(product.getProductId(), product);
	}
	
	public void editBacklogItem(String backlogItemId, BacklogItem backlogItem) {
		backlogItems.replace(backlogItemId, backlogItem);
	}
	
	public void editSprint(String sprintId, Sprint sprint) {
		sprints.replace(sprintId, sprint);
	}
	
	public void editTask(String taskId, Task task) {
		tasks.replace(taskId, task);
	}
	
	public void editRetrospective(String retrospectiveId, Retrospective retrospective) {
		retrospectives.replace(retrospectiveId, retrospective);
	}
	
	public void deleteProduct(String productId) {
		products.remove(productId);
	}
	
	public void deleteBacklogItem(String backlogItemId) {
		backlogItems.remove(backlogItemId);
	}
	
	public void deleteSprint(String sprintId) {
		sprints.remove(sprintId);
	}
	
	public void deleteTask(String taskId) {
		tasks.remove(taskId);
	}
	
	public void deleteRetrospective(String retrospectiveId) {
		retrospectives.remove(retrospectiveId);
	}
}
