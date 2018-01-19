package com.globalmart.productcatalogue.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.globalmart.productcatalogue.model.entity.Product;

/**
 * This class performs the insert, read, delete and search operations
 * 
 * @author skedia
 *
 */
public class DatabaseOperation implements ProductCatalogueManager {

    private static Logger LOG = Log.getLogger(DatabaseOperation.class);
    private static final Object _LOCK = new Object();

    public int insert(Product product) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	try {
	    entityManager.getTransaction().begin();
	    synchronized (_LOCK) {
		entityManager.persist(product);
		entityManager.flush();
	    }
	    entityManager.getTransaction().commit();
	} catch (Exception e) {
	    e.printStackTrace();
	    entityManager.getTransaction().rollback();
	    throw e;
	} finally {
	    entityManager.close();
	}
	return product.getId();
    }

    public Product read(int id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	Product product = null;
	try {
	    product = entityManager.find(Product.class, id);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    entityManager.close();
	    if (product == null)
		LOG.warn("No records records were found with given id value !!");
	}
	return product;
    }

    public boolean delete(int id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	boolean result = false;
	try {
	    entityManager.getTransaction().begin();
	    Product entity = null;
	    synchronized (_LOCK) {
		entity = entityManager.find(Product.class, id);
		if (entity != null) {
		    entityManager.remove(entity);
		    entityManager.getTransaction().commit();
		    result = true;
		} else {
		    LOG.warn("No records records were found with given id value !!");
		    result = false;
		}
	    }
	} catch (Exception e) {
	    result = false;
	    e.printStackTrace();
	    entityManager.getTransaction().rollback();
	    throw e;
	} finally {
	    entityManager.close();
	}
	return result;
    }

    public List<Product> search(String key, String value) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	try {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
	    Root<Product> root = criteriaQuery.from(Product.class);
	    criteriaQuery.select(root);
	    Predicate where = criteriaBuilder.equal(root.get(key), value);
	    criteriaQuery.where(where);
	    return entityManager.createQuery(criteriaQuery).getResultList();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    entityManager.close();
	}
    }
}
