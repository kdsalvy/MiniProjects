package com.globalmart.productcatalogue.model;

import java.util.List;

import com.globalmart.productcatalogue.model.entity.Product;

public interface IDatabaseOperation {

	public int insert(Product product);

	public Product read(int id);

	public boolean delete(int id);

	public List<Product> search(String key, String value);
}
