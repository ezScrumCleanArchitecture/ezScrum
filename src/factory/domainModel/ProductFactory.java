package factory.domainModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import model.Product;
import model.ezScrumDomainModel;

public class ProductFactory implements DomainModelFactory{
	
	private String name;
	private String comment;

	public ProductFactory() {}
	
	public ProductFactory(String name,String comment) {
		this.name = name;
		this.comment = comment;
	}
	
	public ezScrumDomainModel createDomainModel() {
		String productId = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Product product = new Product(name,time);
		product.setProductId(productId);
		product.setComment(comment);
		return product;
	}
}
