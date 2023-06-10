package co.vinod.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import co.vinod.cfg.AppConfig3;
import co.vinod.dao.DaoException;
import co.vinod.dao.ProductDao;

public class P01_GetProductCount {

	public static void main(String[] args) throws DaoException {
		// our dependency
		ProductDao dao;
		
		// a variable representing the spring container
		AnnotationConfigApplicationContext ctx;
		
		// object of spring container
		ctx = new AnnotationConfigApplicationContext(AppConfig3.class);
		
		System.out.println("------------------");
		
		dao = ctx.getBean("jdbcDao", ProductDao.class);
		ProductDao dao2 = ctx.getBean("jdbcDao", ProductDao.class);
		
		System.out.println("dao2 == dao is " + (dao2==dao));
		
		System.out.println("dao is an instanceof " + dao.getClass().getName());
		System.out.println("There are " + dao.count() + " products.");
		System.out.println("There are " + dao2.count() + " products.");
		System.out.println("There are " + dao2.count() + " products.");
		
		
		ctx.close();
	}

}
