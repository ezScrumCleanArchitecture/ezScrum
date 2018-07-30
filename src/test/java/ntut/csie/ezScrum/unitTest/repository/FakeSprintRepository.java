package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.sprint.Sprint;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeSprintRepository  implements Repository<Sprint>{
	
	private Map<String, Sprint> sprints;
	
	public FakeSprintRepository() {
		this.sprints = Collections.synchronizedMap(new LinkedHashMap<String, Sprint>());
	}
	
	@Override
	public void add(Sprint sprint) {
		sprints.put(sprint.getSprintId(), sprint);
	}

	@Override
	public void update(Sprint sprint) {
		sprints.replace(sprint.getSprintId(), sprint);
	}

	@Override
	public void remove(Sprint sprint) {
		sprints.remove(sprint.getSprintId());
	}

	@Override
	public Sprint get(String sprintId) {
		return sprints.get(sprintId);
	}

	@Override
	public Collection<Sprint> getAll() {
		return sprints.values();
	}

	@Override
	public int getNumberOfItems() {
		return sprints.size();
	}

	public void clearAll() {
		sprints.clear();
	}
	
}
