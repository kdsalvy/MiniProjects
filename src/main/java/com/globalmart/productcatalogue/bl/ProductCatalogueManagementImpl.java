package com.globalmart.productcatalogue.bl;

import java.util.ArrayList;
import java.util.List;

import com.globalmart.productcatalogue.bl.dto.ProductDTO;
import com.globalmart.productcatalogue.model.DatabaseOperation;
import com.globalmart.productcatalogue.model.entity.Product;

/**
 * Business Logic of Product Catalogue Management Module that contains the logic
 * to save, retrieve, search and delete products from product catalog
 * 
 * @author skedia
 *
 */
public class ProductCatalogueManagementImpl implements ProductCatalogueManagement {

    static DatabaseOperation opr = new DatabaseOperation();

    @Override
    public int addProduct(ProductDTO dto) {
	Product product = new Product(dto.getId(), dto.getName(), dto.getType(), dto.getCode(), dto.getPrice());
	return opr.insert(product);
    }

    @Override
    public ProductDTO getProduct(int id) {
	Product bdo = opr.read(id);
	return new ProductDTO(bdo.getId(), bdo.getName(), bdo.getType(), bdo.getCode(), bdo.getPrice());
    }

    @Override
    public List<ProductDTO> searchProduct(String criteria, String value) {
	List<Product> pList = opr.search(criteria, value);
	List<ProductDTO> pDTOList = new ArrayList<>();
	for(Product bdo : pList)
	    pDTOList.add(new ProductDTO(bdo.getId(), bdo.getName(), bdo.getType(), bdo.getCode(), bdo.getPrice()));
	return pDTOList;
    }

    @Override
    public boolean deleteProduct(int id) {
	return opr.delete(id);
    }

}
