package co.vinod.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import co.vinod.entity.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Product.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "productName.empty",
				"'Product name' is mandatory");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitPrice", "unitPrice.empty", "'Unit price' is mandatory");

		Product pr = (Product) target;
		Double unitPrice = pr.getUnitPrice();
		Integer unitsInStock = pr.getUnitsInStock();
		Integer unitsOnOrder = pr.getUnitsOnOrder();
		Integer reorderLevel = pr.getReorderLevel();

		if (unitPrice != null && unitPrice < 0) {
			errors.rejectValue("unitPrice", "unitPrice.invalid", "'Unit price' must be > 0");
		}
		if (unitsInStock != null && unitsInStock < 0) {
			errors.rejectValue("unitsInStock", "unitsInStock.invalid", "'Units in stock' must be > 0");
		}
		if (unitsOnOrder != null && unitsOnOrder < 0) {
			errors.rejectValue("unitsOnOrder", "unitsOnOrder.invalid", "'Units on order' must be > 0");
		}
		if (reorderLevel != null && reorderLevel < 0) {
			errors.rejectValue("reorderLevel", "reorderLevel.invalid", "'Reorder level' must be > 0");
		}

		Integer categoryId = pr.getCategory().getCategoryId();
		Integer supplierId = pr.getSupplier().getSupplierId();

		if (categoryId == -1) {
			errors.rejectValue("category.categoryId", "category.not.selected", "Please select a category");
		}
		if (supplierId == -1) {
			errors.rejectValue("supplier.supplierId", "supplier.not.selected", "Please select a supplier");
		}

		Integer discontinued = pr.getDiscontinued();
		if (discontinued == null) {
			errors.rejectValue("discontinued", "discontinued.not.selected", "Please select an option");
		}
	}

}
