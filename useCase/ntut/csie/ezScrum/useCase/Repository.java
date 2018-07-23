package ntut.csie.ezScrum.useCase;

import java.util.Collection;

public interface Repository<T> {
	void add(T item);
	void update(T item);
	void remove(T item);
	Collection<T> query(SqlSpecification sqlSpecification);
}
