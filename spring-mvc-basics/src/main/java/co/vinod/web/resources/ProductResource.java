package co.vinod.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.vinod.dao.DaoException;
import co.vinod.dao.ProductDao;
import co.vinod.entity.ErrorResponse;
import co.vinod.entity.Product;
import co.vinod.entity.ProductList;

@RequestMapping("/api/products")
@RestController
public class ProductResource {

	@Autowired
	ProductDao htDao;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public ResponseEntity<ProductList> getAllProducts() throws DaoException {
		ProductList pl = new ProductList();
		pl.setProducts(htDao.getAllProducts());

		return ResponseEntity.ok(pl);
	}

	@RequestMapping(path = "/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<?> getProductById(@PathVariable Integer id) throws DaoException {

		Product pr = htDao.getProduct(id);
		if (pr == null) {
			ErrorResponse er = new ErrorResponse();
			er.setMessage("No product found!");
			er.setData(id);
			return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(pr);

	}

	@RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" }, consumes = {
			"application/json", "application/xml" })
	public ResponseEntity<?> addProduct(@RequestBody Product pr) {
		try {
			htDao.addProduct(pr);
			pr = htDao.getProduct(pr.getProductId());
			return ResponseEntity.ok(pr);
		} catch (DaoException ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}", produces = { "application/json",
			"application/xml" }, consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product pr) {
		try {
			pr.setProductId(id);
			htDao.updateProduct(pr);
			pr = htDao.getProduct(pr.getProductId());
			return ResponseEntity.ok(pr);
		} catch (Exception ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		try {
			Product pr = htDao.getProduct(id);
			if (pr == null) {
				ErrorResponse er = new ErrorResponse();
				er.setMessage("No product found!");
				er.setData(id);
				return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
			}

			htDao.deleteProduct(id);
			pr = htDao.getProduct(id);
			return ResponseEntity.ok(pr);
		} catch (Exception ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}

}
