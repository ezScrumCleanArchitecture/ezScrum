package ntut.csie.ezScrum.useCase.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import ntut.csie.ezScrum.model.Product;

public class ProductBuilder {
	private String productId;
	private String name;
	private String comment;
	private String create_time;
	
	public static ProductBuilder newInstance() {
		return new ProductBuilder();
	}
	
	public ProductBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public ProductBuilder comment(String comment) {
		this.comment = comment;
		return this;
	}
	
	public Product build() throws Exception {
		productId = UUID.randomUUID().toString();
		if(name == null) {
			throw new Exception("The name of product should not be null.");
		}
		Calendar calendar = Calendar.getInstance();
		create_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Product product = new Product(name,create_time);
		product.setProductId(productId);
		product.setComment(comment);
		return product;
	}
}
