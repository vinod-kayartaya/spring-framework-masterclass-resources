package co.vinod.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.vinod.dao.DaoException;
import co.vinod.dao.ProductDao;
import co.vinod.entity.Category;
import co.vinod.entity.Product;
import co.vinod.entity.Supplier;
import co.vinod.validators.ProductValidator;

@Controller
public class ProductController {

	@Autowired
	ProductDao htDao;

	@RequestMapping(method = RequestMethod.GET, path = "/all-products")
	public String getAllProducts(Model model) throws DaoException {
		model.addAttribute("pageTitle", "All products");
		model.addAttribute("products", htDao.getAllProducts());
		return "show-products";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/products-by-price-range")
	public String getProductsByPriceRange(Model model, @RequestParam Double min, @RequestParam Double max)
			throws DaoException {

		model.addAttribute("pageTitle", "Products priced between $" + min + " and $" + max);
		model.addAttribute("products", htDao.getProductsByPriceRange(min, max));

		return "show-products";
	}

	@RequestMapping("/product-details")
	public String getProductDetails(@RequestParam Integer id, Model model) throws DaoException {
		model.addAttribute("pr", htDao.getProduct(id));
		return "product-details";
	}

	@RequestMapping(path = "/add-product", method = RequestMethod.GET)
	public String addProduct(Model model) throws DaoException {
		model.addAttribute("pr", new Product());
		return "product-form";
	}

	@RequestMapping(path = "/edit-product", method = RequestMethod.GET)
	public String editProduct(Model model, @RequestParam Integer id) throws DaoException {
		model.addAttribute("pr", htDao.getProduct(id));
		return "product-form";
	}

	@ModelAttribute("categories")
	public List<Category> getCategoryList() throws DaoException {
		return htDao.getAllCategories();
	}

	@ModelAttribute("suppliers")
	public List<Supplier> getSupplierList() throws DaoException {
		return htDao.getAllSuppliers();
	}

	@RequestMapping(path = "/save-product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("pr") Product pr, BindingResult errors) throws DaoException {

		ProductValidator pv = new ProductValidator();
		pv.validate(pr, errors);

		if (errors.hasErrors()) {
			return "product-form";
		}

		if(pr.getProductId()==null) {
			htDao.addProduct(pr);
		}
		else {
			htDao.updateProduct(pr);
		}
		
		return "redirect:product-details?id=" + pr.getProductId();
	}
	

	

}
