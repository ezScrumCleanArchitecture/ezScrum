package useCase;

import factory.domainModel.ProductFactory;
import model.Product;

public class CreateProduct {
	
	public String execute(ApplicationContext context, String name,String comment) {
		ProductFactory productFactory = new ProductFactory(name, comment);
		Product product = (Product) productFactory.createDomainModel();
		context.getProducts().put(product.getProductId(), product);
		return product.getProductId();
	}

}
