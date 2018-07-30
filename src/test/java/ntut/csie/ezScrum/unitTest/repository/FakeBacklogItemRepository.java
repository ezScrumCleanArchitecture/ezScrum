package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.backlogItem.BacklogItem;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeBacklogItemRepository implements Repository<BacklogItem>{
	
	private Map<String, BacklogItem> backlogItems;
	
	public FakeBacklogItemRepository() {
		backlogItems = Collections.synchronizedMap(new LinkedHashMap<String, BacklogItem>());
	}

	@Override
	public void add(BacklogItem backlogItem) {
		backlogItems.put(backlogItem.getBacklogItemId(), backlogItem);
	}

	@Override
	public void update(BacklogItem backlogItem) {
		backlogItems.replace(backlogItem.getBacklogItemId(), backlogItem);
	}

	@Override
	public void remove(BacklogItem backlogItem) {
		backlogItems.remove(backlogItem.getBacklogItemId());
	}

	@Override
	public BacklogItem get(String backlogItemId) {
		return backlogItems.get(backlogItemId);
	}

	@Override
	public Collection<BacklogItem> getAll() {
		return backlogItems.values();
	}

	@Override
	public int getNumberOfItems() {
		return backlogItems.size();
	}

	public void clearAll() {
		backlogItems.clear();
	}
	
}
