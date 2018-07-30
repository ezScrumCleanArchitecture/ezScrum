package ntut.csie.ezScrum.unitTest.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.useCase.Repository;

public class FakeProductRepository implements Repository<Product>{
	
	private Map<String, Product> products;

	public FakeProductRepository(){
		products = Collections.synchronizedMap(new LinkedHashMap<String, Product>());
	}
	
	@Override
	public void add(Product product) {
		products.put(product.getProductId(), product);
	}

	@Override
	public void update(Product product) {
		products.replace(product.getProductId(), product);
	}

	@Override
	public void remove(Product product) {
		products.remove(product.getProductId());
	}

	@Override
	public Product get(String productId) {
		return products.get(productId);
	}

	@Override
	public Collection<Product> getAll() {
		return products.values();
	}

	@Override
	public int getNumberOfItems() {
		return products.size();
	}
	
	public void clearAll() {
		products.clear();
	}
	
}
