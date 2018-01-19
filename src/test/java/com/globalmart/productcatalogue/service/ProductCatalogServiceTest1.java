package com.globalmart.productcatalogue.service;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Test;

import com.globalmart.productcatalogue.bl.dto.ProductDTO;

public class ProductCatalogServiceTest1 {

    private static final String APP_URL = "http://localhost:8080/productcatalogue";
    
    @Test
    public void testAddNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(null, MediaType.APPLICATION_JSON));
	Assert.assertEquals(response.getStatus(), 204);
    }
    
    @Test
    public void testViewNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("get").path("1").request().accept(MediaType.APPLICATION_JSON)
		.get(Response.class);
	Assert.assertEquals(response.getStatus(), 204);
    }
    
    @Test
    public void testSearchNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("search").queryParam("key","type").queryParam("value","Detergent").request()
		.accept(MediaType.APPLICATION_JSON).get(Response.class);
	Assert.assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testDeleteNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("remove").path("1").request().accept(MediaType.APPLICATION_JSON)
		.delete(Response.class);
	Assert.assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testAddPositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.CREATED.getStatusCode());
    }
    
    @Test
    public void testViewPositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	// Add an element first
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.CREATED.getStatusCode());
	
	String id = responseAdd.readEntity(String.class);
	// Then view it
	Response responseRemoval = target.path("get").path(id).request().accept(MediaType.APPLICATION_JSON)
		.get(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 200);
    }
    
    @Test
    public void testSearchPositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	// Add an element first
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.CREATED.getStatusCode());
	// Then search it
	Response responseRemoval = target.path("search").queryParam("key", "type").queryParam("value","Stationery")
		.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 200);
    }
    
    @Test
    public void testDeletePositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	// Add an element first
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.CREATED.getStatusCode());
	// Then remove it
	String id = responseAdd.readEntity(String.class);
	Response responseRemoval = target.path("remove").path(id).request().accept(MediaType.APPLICATION_JSON)
		.delete(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 200);
    }
    
    private static URI getApplicationContext() {
	URI result = null;
	try {
	    result = UriBuilder.fromUri(APP_URL).build();
	} catch (UriBuilderException e) {
	    System.out.println("Make sure test URL is correctly provided !!!!");
	}
	return result;
    }
}
