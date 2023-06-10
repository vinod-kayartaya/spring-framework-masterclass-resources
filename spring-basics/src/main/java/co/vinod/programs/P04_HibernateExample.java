package co.vinod.programs;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import co.vinod.entity.Category;

public class P04_HibernateExample {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		props.setProperty("hibernate.connection.url", "jdbc:h2:tcp://localhost/~/spring-training");
		props.setProperty("hibernate.connection.user", "sa");
		props.setProperty("hibernate.connection.password", "");
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		
		Configuration cfg = new Configuration();
		cfg.setProperties(props);
		cfg.addAnnotatedClass(Category.class);
		
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		
		Category c1 = session.get(Category.class, 1);
		System.out.println(c1);
		System.out.println("---------------");
		
		Query<Category> qry = session.createQuery("from Category", Category.class);
		List<Category> list = qry.getResultList();
		
		for(Category c: list) {
			System.out.println(c);
		}
		System.out.println("---------------");
		
		Category c2 = new Category();
		c2.setCategoryId(9);
		c2.setCategoryName("Stationaries");
		c2.setDescription("Paper, clips etc.");
		
		Transaction tx = session.beginTransaction();
		try {
			session.persist(c2);
			tx.commit();
			System.out.println("New category added!");
		}
		catch(Exception ex) {
			tx.rollback();
			System.out.println("There was an error while adding new category");
		}
		
		session.close();
		factory.close();
	}

}
