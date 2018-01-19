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
	Response response = target.path("remove").path("1").request().accept(MediaType.TEXT_PLAIN)
		.delete(Response.class);
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
    public void testUpdateNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("search").path("1").path("2").request().accept(MediaType.TEXT_PLAIN)
		.get(Response.class);
	Assert.assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testDeleteNegative() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	Response response = target.path("remove").path("1").request().accept(MediaType.TEXT_PLAIN)
		.delete(Response.class);
	Assert.assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testAddPositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	
	// Add an element first
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.OK.getStatusCode());
	// Then remove it
	Response responseRemoval = target.path("remove").path("1").request()
		.delete(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 204);
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
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.OK.getStatusCode());
	// Then remove it
	Response responseRemoval = target.path("remove").path("1").request()
		.delete(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 204);
    }
    
    @Test
    public void testUpdatePositive() {
	ProductDTO stub = new ProductDTO(1, "One", "Stationery", "A2E1",45.00d);
	
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(getApplicationContext());
	
	// Add an element first
	Response responseAdd = target.path("add").request().accept(MediaType.APPLICATION_JSON)
		.post(Entity.entity(stub, MediaType.APPLICATION_JSON));
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.OK.getStatusCode());
	// Then remove it
	Response responseRemoval = target.path("remove").path("1").request()
		.delete(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 204);
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
	Assert.assertEquals(responseAdd.getStatus(), Response.Status.OK.getStatusCode());
	// Then remove it
	Response responseRemoval = target.path("remove").path("1").request()
		.delete(Response.class);
	Assert.assertEquals(responseRemoval.getStatus(), 204);
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
