package ntut.csie.ezScrum.unitTest.useCase;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.ezScrum.model.product.Product;
import ntut.csie.ezScrum.model.product.ProductBuilder;
import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.product.ProductManagerUseCase;

public class ProductUseCaseTest {
	
	private ApplicationContext context;

	@Before
	public void setUp() {
		context = ApplicationContext.getInstance();
	}
	
	@After
	public void tearDown() {
		context.clearProducts();
	}

	@Test
	public void Should_RequiredDataInsertIntoProduct_When_AddProductWithRequiredParamemter() {
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
		String name = "ezScrum";
		Product product = null;
		try {
			product = ProductBuilder.
					newInstance().
					name(name).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String productId = productManagerUseCase.addProduct(product);
		Product testedProduct = context.getProduct(productId);
		assertEquals(name, testedProduct.getName());
		assertEquals(null, testedProduct.getComment());
	}
	
	@Test
	public void Should_AllDataInsertIntoProduct_When_AddProductWithAllParamemter() {
		ProductManagerUseCase productManagerUseCase = new ProductManagerUseCase(context);
		String name = "ezScrum";
		String comment = "This is the comment for ezScrum Product.";
		Product product = null;
		try {
			product = ProductBuilder.newInstance().
					name(name).
					comment(comment).
					build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String productId = productManagerUseCase.addProduct(product);
		Product testedProduct = context.getProduct(productId);
		assertEquals(name, testedProduct.getName());
	}
	
	@Test
	public void Should_ThrowExcpetion_When_AddProductWithEmptyParamemter() {
		try {
			ProductBuilder.newInstance().
				name(null).
				build();
		} catch (Exception e) {
			assertEquals("The name of product should not be null.", e.getMessage());
		}
	}
	
}
