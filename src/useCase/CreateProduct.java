package useCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Product;

public class CreateProduct {
	private static long productId=0;
	
	public Product addProduct(String name,String comment) {
		productId++;
		Calendar calendar = Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		Product product = new Product(name,time);
		product.setProductId(productId);
		product.setComment(comment);
		return product;
	}

}
