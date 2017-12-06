package com.globalmart.productcatalogue.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalmart.productcatalogue.bl.ProductCatalogueManagement;
import com.globalmart.productcatalogue.bl.ProductCatalogueManagementImpl;
import com.globalmart.productcatalogue.bl.dto.ProductDTO;

@Path("/productcatalogue")
public class ProductCatalogService {

    static ProductCatalogueManagement pcm = new ProductCatalogueManagementImpl();

    /**
     * Adds a product
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDTO product) {
	int id = pcm.addProduct(product);
	return Response.ok(id).build();
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
	return Response.ok(pcm.getProduct(id)).build();
    }

    /**
     * deletes a product based on id
     * 
     * @param id
     * @return
     */
    @DELETE
    @Path("remove/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
	return Response.ok(pcm.deleteProduct(id)).build();
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
    @Path("search/{key}/{value}")
    public Response searchProducts(@PathParam("key") String key, @PathParam("value") String value) {
	ObjectMapper mapper = new ObjectMapper();
	String pList = "";
	try {
	    pList = mapper.writeValueAsString(pcm.searchProduct(key, value));
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}
	return Response.ok(pList).build();
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
