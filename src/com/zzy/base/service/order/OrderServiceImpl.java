package com.zzy.base.service.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzy.base.repository.order.OrderDao;
import com.zzy.base.repository.order.PersonDao;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderDao orderDao;
	@Resource
	private PersonDao personDao;
	@Override
	public void deleteOrderById(Long[] id) {
		orderDao.deleteById(id);
	}

}
