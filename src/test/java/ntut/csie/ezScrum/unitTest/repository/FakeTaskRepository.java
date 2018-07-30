package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.task.Task;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeTaskRepository implements Repository<Task>{
	
	private Map<String, Task> tasks;

	public FakeTaskRepository() {
		tasks = Collections.synchronizedMap(new LinkedHashMap<String, Task>());
	}
	
	@Override
	public void add(Task task) {
		tasks.put(task.getTaskId(), task);
	}

	@Override
	public void update(Task task) {
		tasks.replace(task.getTaskId(), task);
	}

	@Override
	public void remove(Task task) {
		tasks.remove(task.getTaskId());
	}

	@Override
	public Task get(String taskId) {
		return tasks.get(taskId);
	}

	@Override
	public Collection<Task> getAll() {
		return tasks.values();
	}

	@Override
	public int getNumberOfItems() {
		return tasks.size();
	}

	public void clearAll() {
		tasks.clear();
	}
	
}
