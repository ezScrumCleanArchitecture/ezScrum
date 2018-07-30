package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.retrospective.Retrospective;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeRetrospectiveRepository implements Repository<Retrospective>{
	
	private Map<String, Retrospective> retrospectives;
	
	public FakeRetrospectiveRepository() {
		retrospectives = Collections.synchronizedMap(new LinkedHashMap<String, Retrospective>());
	}
	
	@Override
	public void add(Retrospective retrospective) {
		retrospectives.put(retrospective.getRetrospectiveId(), retrospective);
	}

	@Override
	public void update(Retrospective retrospective) {
		retrospectives.replace(retrospective.getRetrospectiveId(), retrospective);
	}

	@Override
	public void remove(Retrospective retrospective) {
		retrospectives.remove(retrospective.getRetrospectiveId());
	}

	@Override
	public Retrospective get(String retrospectiveId) {
		return retrospectives.get(retrospectiveId);
	}

	@Override
	public Collection<Retrospective> getAll() {
		return retrospectives.values();
	}

	@Override
	public int getNumberOfItems() {
		return retrospectives.size();
	}

	public void clearAll() {
		retrospectives.clear();
	}
	
}
