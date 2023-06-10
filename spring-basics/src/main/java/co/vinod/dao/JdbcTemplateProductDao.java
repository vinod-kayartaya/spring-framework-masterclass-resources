package co.vinod.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.vinod.entity.Product;

@Repository("jtDao")
public class JdbcTemplateProductDao implements ProductDao {
	
	@Autowired(required = false)
	private JdbcTemplate template;
	
	private RowMapper<Product> prm = (rs, i) -> {
		Product p = new Product();
		p.setProductId(rs.getInt("product_id"));
		p.setProductName(rs.getString("product_name"));
		p.setSupplierId(rs.getInt("supplier_id"));
		p.setCategoryId(rs.getInt("category_id"));
		p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
		p.setUnitPrice(rs.getDouble("unit_price"));
		p.setUnitsInStock(rs.getInt("units_in_stock"));
		p.setUnitsOnOrder(rs.getInt("units_on_order"));
		p.setReorderLevel(rs.getInt("reorder_level"));
		p.setDiscontinued(rs.getInt("discontinued"));
		return p;
	};

	@Override
	public void addProduct(Product p) throws DaoException {
		String sql = "insert into products values (?,?,?,?,?,?,?,?,?,?)";
		template.update(sql, p.getProductId(), p.getProductName(), p.getSupplierId(), p.getCategoryId(),
				p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(), p.getUnitsOnOrder(),
				p.getReorderLevel(), p.getDiscontinued());
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		String sql = "update products set product_name=?, supplier_id=?, category_id=?, quantity_per_unit=?, "
				+ "unit_price=?, units_in_stock=?, units_on_order=?, reorder_level=?, discontinued=? "
				+ "where product_id=?";
		template.update(sql, p.getProductName(), p.getSupplierId(), p.getCategoryId(),
				p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(), p.getUnitsOnOrder(),
				p.getReorderLevel(), p.getDiscontinued(), p.getProductId());
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		String sql = "Select * from products where product_id=?";
		return template.queryForObject(sql, prm, productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		String sql = "update products set discontinued=1 where product_id=?";
		template.update(sql, productId);
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		String sql = "select * from products";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		String sql = "select * from products where unit_price between ? and ?";
		return template.query(sql, prm, min, max);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		String sql = "select * from products where category_id = ?";
		return template.query(sql, prm, categoryId);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		String sql = "select * from products where units_in_stock=0";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		String sql = "select * from products where units_on_order>0";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		String sql = "select * from products where discontinued=1";
		return template.query(sql, prm);
	}

	@Override
	public long count() throws DaoException {
		String sql = "select count(*) from products";
		return template.queryForObject(sql, Long.class);
	}

}
