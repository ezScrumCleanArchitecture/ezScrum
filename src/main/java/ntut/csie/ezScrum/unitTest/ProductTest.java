package ntut.csie.ezScrum.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.CreateProduct;

public class ProductTest {
	ApplicationContext context;

	@Before
	public void setUp() {
		context = ApplicationContext.newInstance();
	}
	
	@After
	public void tearDown() {
		context.getProducts().clear();
	}

	@Test
	public void addProductTest() {
		CreateProduct createProductUseCase = new CreateProduct();
		String name = "ezScrum";
		String productId = createProductUseCase.execute(context, name, null);
		
		assertEquals(name,context.getProducts().get(productId).getName());
	}
}
