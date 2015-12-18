package com.camut.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
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
import com.camut.pageModel.PageDiscount;
import com.camut.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml", "classpath*:spring-hibernate.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DiscountDaoTest {

	@Autowired
	private DiscountDao discountDao;

	private int totalRecords = 0;
	private Restaurants testRestaurant;

	@Before
	public void initialize() {
		testRestaurant = getTestRestaurant();
		totalRecords = discountDao.getAllDiscounts(testRestaurant).size();
	}

	@Test
	public void getDiscountByRestaurantUuidTest() {
		CartHeader cartHeader = new CartHeader();
		cartHeader.setRestaurantUuid(testRestaurant.getUuid());
		cartHeader.setOrderType(ORDER_TYPE.DELIVERY.getValue());
		cartHeader.setTotal(100.0);
		List<Discount> discountList = discountDao.getDiscountByRestaurantUuid(cartHeader.getRestaurantUuid(),
				cartHeader.getOrderType(), cartHeader.getTotal());// cartHeader.getDishFee());
		int notExpected = 0;
		assertNotEquals(notExpected, discountList.size());
	}

	@Test
	public void getAllDiscountsTest() {
		List<Discount> discountList = discountDao.getAllDiscounts(testRestaurant);
		assertEquals(totalRecords, discountList.size());
	}

	@Test
	public void addDiscountTest() {
		int result = addTestDiscount();
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		List<Discount> newDiscountList = discountDao.getAllDiscounts(testRestaurant);
		int expectedSize = totalRecords;
		assertEquals(expectedSize, newDiscountList.size());

		Discount discount = getTestDiscount();
		discount.setConsumePrice(null);
		result = discountDao.addDiscount(discount);
		expectedResult = -1;
		assertEquals(expectedResult, result);
	}

	@Test
	public void addDiscountFaildedTest() {
		int result = addTestDiscount();
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		List<Discount> newDiscountList = discountDao.getAllDiscounts(testRestaurant);
		int expectedSize = totalRecords;
		assertEquals(expectedSize, newDiscountList.size());
	}

	@Test
	public void updateDiscountTest() {
		long discountId = Long.parseLong("86");

		double expectedPrice = 40.0;
		Discount returnedDiscount = discountDao.getDiscount(discountId);
		returnedDiscount.setConsumePrice(expectedPrice);
		int result = discountDao.updateDiscount(returnedDiscount);
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		Discount updatedDiscount = discountDao.getDiscount(discountId);
		double updatedPrice = updatedDiscount.getConsumePrice();
		double delta = 0.001;
		assertEquals(expectedPrice, updatedPrice, delta);

		Discount newDiscount = getTestDiscount();
		result = discountDao.updateDiscount(newDiscount);
		expectedResult = -1;
		assertEquals(expectedResult, result);

	}

	@Test
	public void deleteDiscountTest() {
		Discount discount = getTestDiscount();
		int result = discountDao.addDiscount(discount);
		totalRecords++;
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		result = discountDao.deleteDiscount(discount);
		totalRecords--;
		expectedResult = 1;
		assertEquals(expectedResult, result);

		int expectedSize = totalRecords;
		List<Discount> newDiscountList = discountDao.getAllDiscounts(testRestaurant);
		assertEquals(expectedSize, newDiscountList.size());

		Discount newDiscount = getTestDiscount();
		result = discountDao.deleteDiscount(newDiscount);
		expectedResult = -1;
		assertEquals(expectedResult, result);
	}

	@Test
	public void hardDeleteDiscountTest() {
		Discount discount = getTestDiscount();
		int result = discountDao.addDiscount(discount);
		totalRecords++;
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		result = discountDao.hardDeleteDiscount(discount);
		totalRecords--;
		expectedResult = 1;
		assertEquals(expectedResult, result);

		int expectedSize = totalRecords;
		List<Discount> newDiscountList = discountDao.getAllDiscounts(testRestaurant);
		assertEquals(expectedSize, newDiscountList.size());

		Discount newDiscount = getTestDiscount();
		newDiscount.setId(null);
		result = discountDao.hardDeleteDiscount(newDiscount);
		expectedResult = 1;
		assertEquals(expectedResult, result);
	}

	@Test
	public void getDiscountTest() {
		long discountId = Long.parseLong("85");
		Discount discount = discountDao.getDiscount(discountId);

		double orderPrice = discount.getConsumePrice();
		double delta = 0.001;
		double expectedPrice = 30.0;
		assertEquals(expectedPrice, orderPrice, delta);

	}

	@Test
	public void getDiscountByDishIdTest() {
		int dishId = 123;
		Discount discount = getTestDiscount();
		discount.setDishId(dishId);
		int result = discountDao.addDiscount(discount);
		totalRecords++;
		int expectedResult = 1;
		assertEquals(expectedResult, result);

		result = discountDao.getDiscountByDishId(dishId);
		expectedResult = 1;
		assertEquals(expectedResult, result);
	}

	@Test
	public void chooseDiscountTest() {
		Map<String, Object> idMap1 = getIdMap("80", "81");
		int expectedResult = 2;
		List<Discount> discountList = discountDao.chooseDiscount(idMap1);
		assertEquals(expectedResult, discountList.size());

		idMap1 = getIdMap("80", "0");
		expectedResult = 1;
		discountList = discountDao.chooseDiscount(idMap1);
		assertEquals(expectedResult, discountList.size());

		idMap1 = getIdMap("", "");
		expectedResult = 0;
		discountList = discountDao.chooseDiscount(idMap1);
		assertEquals(expectedResult, discountList.size());
	}

	public int addTestDiscount() {
		Discount discount = new Discount();
		discount.setRestaurants(testRestaurant);
		discount.setConsumePrice(20.0);
		discount.setContent("unit test");
		discount.setType(DISCOUNT_TYPE.CASH_COUPON.getValue());
		totalRecords++;
		return discountDao.addDiscount(discount);
	}

	public Discount getTestDiscount() {
		Discount discount = new Discount();
		discount.setRestaurants(testRestaurant);
		discount.setConsumePrice(20.0);
		discount.setContent("unit test");
		discount.setType(DISCOUNT_TYPE.CASH_COUPON.getValue());
		return discount;
	}

	public Map<String, Object> getIdMap(String newDiscountId, String oldDiscountId) {
		Map<String, Object> idMap = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(newDiscountId)) {
			idMap.put("newId", Long.parseLong(newDiscountId));
		}
		if (StringUtil.isNotEmpty(oldDiscountId)) {
			idMap.put("oldId", Long.parseLong(oldDiscountId));
		}
		return idMap;
	}

	public Restaurants getTestRestaurant() {
		Restaurants restaurant = new Restaurants();
		restaurant.setUuid("9b740d70ad8c4214ba6561e512ea8949");
		return restaurant;
	}
}
