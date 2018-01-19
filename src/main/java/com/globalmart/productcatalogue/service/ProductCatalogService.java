package com.globalmart.productcatalogue.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.globalmart.productcatalogue.bl.ProductCatalogueManagement;
import com.globalmart.productcatalogue.bl.ProductCatalogueManagementImpl;
import com.globalmart.productcatalogue.bl.dto.ProductDTO;

@Path("/productcatalogue")
public class ProductCatalogService {

    private static Logger LOG = Log.getLogger(ProductCatalogService.class);
    private static ProductCatalogueManagement pcm = new ProductCatalogueManagementImpl();

    /**
     * Adds a product
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDTO product) {
	Response response = null;
	try {
	    int id = pcm.addProduct(product);
	    LOG.info("Product Added with id: " + id);
	    response = Response.created(URI.create("/productcatalogue/get/" + id)).build();
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.serverError().entity("Exception in adding product: " + e.getMessage()).build();
	}
	return response;
    }

    /**
     * Gets a product based on id
     * 
     * @param id
     * @return
     */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") int id) {
	Response response = null;
	try {
	    ProductDTO result = pcm.getProduct(id);
	    if (result != null) {
		LOG.info("Product Found with id: " + id);
		response = Response.ok().entity(result).build();
	    } else {
		LOG.warn("Could not find product with id: " + id);
		response = Response.noContent().entity("Product with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.serverError()
		    .entity("Exception in fetching the product with given id: " + e.getMessage()).build();
	}
	return response;
    }

    /**
     * deletes a product based on id
     * 
     * @param id
     * @return
     */
    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") int id) {
	Response response = null;
	try {
	    boolean result = pcm.deleteProduct(id);
	    if (result) {
		LOG.info("Deleted the product with id: " + id);
		response = Response.ok().build();
	    } else {
		LOG.warn("No Product found with id: " + id);
		response = Response.noContent().entity("Product with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.serverError()
		    .entity("Exception in deleting the product with given id: " + e.getMessage()).build();
	}
	return response;
    }

    /**
     * Retrieves a list of products based on given search criteria and only
     * code, name or type can be used as search criteria
     * 
     * @param key
     * @param value
     * @return
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProducts(@QueryParam("key") String key, @QueryParam("value") String value) {
	Response response = null;
	try {
	    List<ProductDTO> products = pcm.searchProduct(key, value);
	    if (products == null || products.isEmpty()) {
		LOG.warn("Products with given query parameters are not found");
		response = Response.noContent().entity("No Content Found").build();
	    } else {
		LOG.info("Products with given query parameters are found");
		response = Response.ok().entity(products).build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.serverError()
		    .entity("Exception in searching the product with given query parameters: " + e.getMessage())
		    .build();
	}
	return response;
    }

    /**
     * Test API to check server is up or not
     */
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String testApp() {
	return "Hello! I am up and running";
    }
}
