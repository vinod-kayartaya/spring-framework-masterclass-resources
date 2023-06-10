package co.vinod.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import co.vinod.entity.Product;

@Repository("htDao")
@SuppressWarnings("unchecked")
public class HibernateTemplateProductDao implements ProductDao {
	
	@Autowired(required = false)
	private HibernateTemplate template;

	@Override
	public void addProduct(Product product) throws DaoException {
		template.persist(product);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		template.merge(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		return template.get(Product.class, productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		Product p = getProduct(productId);
		p.setDiscontinued(1);
		template.merge(p);
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.between("unitPrice", min, max));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("categoryId", categoryId));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("unitsInStock", 0));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.gt("unitsOnOrder", 0));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.add(Restrictions.eq("discontinued", 1));
		return (List<Product>) template.findByCriteria(dc);
	}

	@Override
	public long count() throws DaoException {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.setProjection(Projections.rowCount());
		
		return (long) template.findByCriteria(dc).get(0);
	}

}







