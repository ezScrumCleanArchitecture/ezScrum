package ntut.csie.ezScrum.useCase;

import ntut.csie.ezScrum.factory.domainModel.ProductFactory;
import ntut.csie.ezScrum.model.Product;

public class CreateProduct {
	
	public String execute(ApplicationContext context, String name,String comment) {
		ProductFactory productFactory = new ProductFactory(name, comment);
		Product product = (Product) productFactory.createDomainModel();
		context.getProducts().put(product.getProductId(), product);
		return product.getProductId();
	}

}
