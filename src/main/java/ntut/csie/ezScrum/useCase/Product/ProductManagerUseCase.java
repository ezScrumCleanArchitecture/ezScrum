package ntut.csie.ezScrum.useCase.Product;

import ntut.csie.ezScrum.model.Product;
import ntut.csie.ezScrum.useCase.ApplicationContext;

public class ProductManagerUseCase {
	
	private ApplicationContext context;
	
	public ProductManagerUseCase(ApplicationContext context) {
		this.context = context;
	}
	
	public String addProduct(Product product) {
		context.addProduct(product);
		return product.getProductId();
	}

}
