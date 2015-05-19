package mybatis.spring;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzy.base.entity.Order;
import com.zzy.base.entity.Person;
import com.zzy.base.repository.order.OrderDao;
import com.zzy.base.repository.order.PersonDao;
import com.zzy.base.service.order.OrderService;

public class MyBatisTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void init() {
		context = new ClassPathXmlApplicationContext("application*.xml");
	}
	@Test
	public void loadOrder() {
		OrderDao orderDao =  (OrderDao) context.getBean("orderDao");
		Order order = orderDao.findById(1L);
		System.out.println(order);
	}
	@Test
	public void loadPerson() {
		PersonDao orderDao =  (PersonDao) context.getBean("personDao");
		Person person = orderDao.findById(1L);
		System.out.println(person);
	}
	@Test
	public void tranDelete() {
		OrderService orderService =  (OrderService) context.getBean("orderService");
		orderService.deleteOrderById(null);
	}
	
}
