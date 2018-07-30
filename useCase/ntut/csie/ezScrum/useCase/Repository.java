package ntut.csie.ezScrum.useCase;

import java.util.Collection;

public interface Repository<T> {
	void add(T item);
	void update(T item);
	void remove(T item);
	T get(String id);
	Collection<T> getAll();
	int getNumberOfItems();
}
