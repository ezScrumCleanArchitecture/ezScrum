package unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import useCase.CreateProduct;

public class ProductTest {
	CreateProduct newProduct;

	@Before
	public void setUp() {
		newProduct = new CreateProduct();
	}

	@Test
	public void addBacklogItemTest() {
		String name = "ezScrum";

		assertEquals(1, newProduct.addProduct(name, null).getProductId());
		assertEquals("ezScrum",newProduct.addProduct(name, null).getName());
	}
}
