package com.globalmart.productcatalogue.bl;

import java.util.ArrayList;
import java.util.List;

import com.globalmart.productcatalogue.bl.dto.ProductDTO;
import com.globalmart.productcatalogue.model.DatabaseOperation;
import com.globalmart.productcatalogue.model.ProductCatalogueManager;
import com.globalmart.productcatalogue.model.entity.Product;

/**
 * Business Logic of Product Catalogue Management Module that contains the logic
 * to save, retrieve, search and delete products from product catalog
 * 
 * @author skedia
 *
 */
public class ProductCatalogueManagementImpl implements ProductCatalogueManagement {

    private static ProductCatalogueManager opr = new DatabaseOperation();

    @Override
    public int addProduct(ProductDTO dto) {
	// Convert the dto to entity object
	Product product = new Product(dto.getName(), dto.getType(), dto.getCode(), dto.getPrice());
	// Persist the entity object
	return opr.insert(product);
    }

    @Override
    public ProductDTO getProduct(int id) {
	Product bdo = opr.read(id);
	ProductDTO result = null;
	if (bdo != null)
	    result = new ProductDTO(bdo.getId(), bdo.getName(), bdo.getType(), bdo.getCode(), bdo.getPrice());
	return result;
    }

    @Override
    public List<ProductDTO> searchProduct(String criteria, String value) {
	List<Product> pList = opr.search(criteria, value);
	List<ProductDTO> listOfProducts = new ArrayList<>();
	for (Product bdo : pList)
	    listOfProducts
		    .add(new ProductDTO(bdo.getId(), bdo.getName(), bdo.getType(), bdo.getCode(), bdo.getPrice()));
	return listOfProducts;
    }

    @Override
    public boolean deleteProduct(int id) {
	return opr.delete(id);
    }

}
