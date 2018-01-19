package com.globalmart.productcatalogue.bl;

import java.util.List;

import com.globalmart.productcatalogue.bl.dto.ProductDTO;

/**
 * Interface to declare all the basic operations of product catalogue management
 * 
 * @author skedia
 *
 */
public interface ProductCatalogueManagement {

	public int addProduct(ProductDTO product);

	public ProductDTO getProduct(int id);

	public List<ProductDTO> searchProduct(String criteria, String value);

	public boolean deleteProduct(int id);
}
