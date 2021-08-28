package com.devsuperior.dscatalog.factory;

import com.devsuperior.dscatalog.entities.Category;

public class CategoryFactory {
	
	
	public static Category createCategoryComputers () {
		return new Category(3L, "Computadores");
	}
	
	public static Category createCategoryComputersIgnorecase () {
		return new Category(3L, "compTadores");
	}

}
