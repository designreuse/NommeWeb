package com.camut.dao;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.camut.framework.constant.GlobalConstant.DISCOUNT_TYPE;
import com.camut.framework.constant.GlobalConstant.ORDER_TYPE;
import com.camut.model.CartHeader;
import com.camut.model.Discount;
import com.camut.model.Restaurants;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml", "classpath*:spring-hibernate.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DiscountDaoTest {

	@Autowired
	private DiscountDao discountDao;
	private RestaurantsDao restaurantsDao;

	@Test
	public void getDiscountByRestaurantUuidTest() {
		CartHeader cartHeader = new CartHeader();
		cartHeader.setRestaurantUuid("9b740d70ad8c4214ba6561e512ea8949");
		cartHeader.setOrderType(ORDER_TYPE.DELIVERY.getValue());
		cartHeader.setTotal(100.0);
		List<Discount> discountList = discountDao.getDiscountByRestaurantUuid(cartHeader.getRestaurantUuid(),
				cartHeader.getOrderType(), cartHeader.getTotal());// cartHeader.getDishFee());
		int notExpected = 0;
		assertNotEquals(notExpected, discountList.size());
	}

	@Test
	public void getAllDiscountsTest() {
		Restaurants restaurant = new Restaurants();
		restaurant.setUuid("9b740d70ad8c4214ba6561e512ea8949");
		List<Discount> discountList = discountDao.getAllDiscounts(restaurant);
		int expected = 3;
		assertEquals(expected, discountList.size());
	}

	@Test
	public void addDiscountTest() {
		Restaurants restaurant = new Restaurants();
		restaurant.setUuid("9b740d70ad8c4214ba6561e512ea8949");

		Discount discount = new Discount();
		discount.setRestaurants(restaurant);
		discount.setConsumePrice(20.0);
		discount.setContent("unit test");
		discount.setType(DISCOUNT_TYPE.CASH_COUPON.getValue());

		List<Discount> discountList = discountDao.getAllDiscounts(restaurant);
		int expectedSize = discountList.size() + 1;

		int result = discountDao.addDiscount(discount);
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		List<Discount> newDiscountList = discountDao.getAllDiscounts(restaurant);
		assertEquals(expectedSize, newDiscountList.size());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void updateDiscountTest() {
		Restaurants restaurant = new Restaurants();
		restaurant.setUuid("9b740d70ad8c4214ba6561e512ea8949");

//		Discount discount = new Discount();
		long discountId = Long.parseLong("86");
//		discount.setId(discountId);
//		discount.setRestaurants(restaurant);
//		discount.setConsumePrice(20.0);
//		discount.setContent("unit test updateDiscount");
//		discount.setType(DISCOUNT_TYPE.CASH_COUPON.getValue());

//		int result = discountDao.addDiscount(discount);
//		int expectedResult = 1;
//		assertEquals(expectedResult, result);

		double expectedPrice = 40.0;
		Discount returnedDiscount = discountDao.getDiscount(discountId);
		returnedDiscount.setConsumePrice(expectedPrice);
		int result = discountDao.updateDiscount(returnedDiscount);
		int expectedResult = 1;
		assertEquals(expectedResult, result);
		
		Discount updatedDiscount = discountDao.getDiscount(discountId);
		double updatedPrice = updatedDiscount.getConsumePrice();
		double delta =  0.001;
		assertEquals(expectedPrice, updatedPrice, delta);
	}
}
