package ntut.csie.ezScrum.factory.testData;

import ntut.csie.ezScrum.useCase.ApplicationContext;
import ntut.csie.ezScrum.useCase.CreateProduct;

public class ProductTestDataFactory implements TestDataFactory{
	
	private ApplicationContext context;
	private String name;
	private String comment;
	
	public ProductTestDataFactory(ApplicationContext context) {
		this.context = context;
		this.name = "ezScrum";
		this.comment = "This is a web base of scrum tool.";
	}
	
	public ProductTestDataFactory(ApplicationContext context, String name, String comment) {
		this.context = context;
		this.name = name;
		this.comment = comment;
	}
	
	public String createTestData() {
		CreateProduct createProductUseCase = new CreateProduct();
		String productId = createProductUseCase.execute(context, name, comment);
		return productId;
	}
}
