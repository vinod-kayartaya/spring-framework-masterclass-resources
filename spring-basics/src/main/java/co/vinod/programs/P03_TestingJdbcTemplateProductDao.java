package co.vinod.programs;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.cfg.AppConfig4;
import co.vinod.dao.DaoException;
import co.vinod.dao.ProductDao;
import co.vinod.entity.Product;

public class P03_TestingJdbcTemplateProductDao {

	public static void main(String[] args) throws DaoException {
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig4.class);
		
		ProductDao dao = ctx.getBean("jtDao", ProductDao.class);

		Product p = dao.getProduct(1);
		System.out.println(p);
		p.setUnitPrice(p.getUnitPrice()+1);
		dao.updateProduct(p);
		System.out.println("Price updated");
		
		List<Product> list = dao.getProductsByPriceRange(50.0, 200.0);
		System.out.println("There are " + list.size() + " products between $50 and $200");
		
		list = dao.getDiscontinuedProducts();
		System.out.println("There are " + list.size() + " discontinued products");
		ctx.close();
	}

}
