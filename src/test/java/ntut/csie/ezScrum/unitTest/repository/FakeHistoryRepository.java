package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.history.History;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeHistoryRepository implements Repository<History>{

	private Map<String, History> histories;
	
	public FakeHistoryRepository() {
		histories = Collections.synchronizedMap(new LinkedHashMap<String, History>());
	}

	@Override
	public void add(History history) {
		histories.put(history.getHistoryId(), history);
	}

	@Override
	public void update(History history) {
		
	}

	@Override
	public void remove(History history) {
		histories.remove(history.getHistoryId());
	}

	@Override
	public History get(String historyId) {
		return null;
	}

	@Override
	public Collection<History> getAll() {
		return histories.values();
	}

	@Override
	public int getNumberOfItems() {
		return histories.size();
	}
	
	public void clearAll() {
		histories.clear();
	}
	
}
