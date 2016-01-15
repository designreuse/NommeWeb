package com.camut.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.camut.dao.NommeDiscountDao;
import com.camut.framework.constant.GlobalConstant.DELETE_STATUS;
import com.camut.model.Discount;
import com.camut.model.NommeDiscount;
import com.camut.model.Restaurants;
import com.camut.utils.UDSqlCommand;

/**
 * @dao NommeDiscountDaoImpl.java
 * @author Cuong Ich Truong
 * @createtime 2016 01 15
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class NommeDiscountDaoImpl extends BaseDao<NommeDiscount> implements NommeDiscountDao {

	/**
	 * @Title: getAllDiscounts
	 * @Description: get all nomme discounts
	 * @param:
	 * @return: List<NommeDiscount>
	 */
	@Override
	public List<NommeDiscount> getAllNommeDiscounts() {
		UDSqlCommand command = new UDSqlCommand();
		command.SelectFrom("NommeDiscount").Where("").GetNonDeletedRecordsOnly();
		
		List<NommeDiscount> list = this.find(command);
		return list;
	}

	/**
	 * @Title: addNommeDiscount
	 * @Description:
	 * @param: NommeDiscount
	 * @return: int
	 */
	@Override
	public int addNommeDiscount(NommeDiscount nommeDiscount) {
		try {
			nommeDiscount.setDeleteStatus(DELETE_STATUS.NOT_DELETED.getValue());
			this.save(nommeDiscount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateNommeDiscount
	 * @Description:
	 * @param: NommeDiscount
	 * @return: int
	 */
	@Override
	public int updateNommeDiscount(NommeDiscount nommeDiscount) {
		try {
			nommeDiscount.setDeleteStatus(DELETE_STATUS.NOT_DELETED.getValue());
			this.update(nommeDiscount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteNommeDiscount
	 * @Description: soft delete
	 * @param: NommeDiscount
	 * @return: int
	 */
	@Override
	public int deleteNommeDiscount(NommeDiscount nommeDiscount) {
		try {
			NommeDiscount toDelete = getNommeDiscountByUuid(nommeDiscount.getUuid());
			toDelete.setDeleteStatus(DELETE_STATUS.DELETED.getValue());
			this.update(toDelete);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getNommeDiscountByDishId
	 * @Description: get discount by free item's id
	 * @param: String
	 * @return: NommeDiscount
	 */
	@Override
	public NommeDiscount getNommeDiscountByDishId(int dishId) {
		UDSqlCommand command = new UDSqlCommand();
		command.CountFrom("NommeDiscount").Where("dishId=:dishId").GetNonDeletedRecordsOnly();

		NommeDiscount nommeDiscount = this.get(command);
		return nommeDiscount;
	}

	/**
	 * @Title: getNommeDiscountByUuid
	 * @Description: get nomme discount by uuid
	 * @param uuid
	 * @return NommeDiscount
	 */
	@Override
	public NommeDiscount getNommeDiscountByUuid(String uuid) {
		UDSqlCommand command = new UDSqlCommand();
		command.SelectFrom("NommeDiscount").Where("uuid=:uuid").GetNonDeletedRecordsOnly();

		command.AddParameters("uuid", uuid);

		NommeDiscount nommeDiscount = this.get(command);
		return nommeDiscount;
	}

	/**
	 * @Title: hardDeleteNommeDiscount
	 * @Description: WARINING: removing the record from the table cannot be
	 *               undone
	 * @param: NommeDiscount
	 * @return: int
	 */
	@Override
	public int hardDeleteNommeDiscount(NommeDiscount nommeDiscount) {
		try {
			this.delete(nommeDiscount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
}
