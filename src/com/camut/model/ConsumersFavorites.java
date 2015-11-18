package com.camut.model;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity CustomerFavorites . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_customer_favorites", catalog = "nomme")
public class ConsumersFavorites extends IdEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6896426220188550803L;
	// Fields

	private String consumersUuid;// 与用户一对一单向关联; one-to-one relationship with Consumers
	private String restaurantsUuid;// 商家id;  restaurants ID
	private Date favoritesdate;// 收藏时间; date of adding a restaurant to Favorites

	// Property accessors

	
	@Column(name="consumers_uuid")
	public String getConsumersUuid() {
		return consumersUuid;
	}

	public void setConsumersUuid(String consumersUuid) {
		this.consumersUuid = consumersUuid;
	}

	@Column(name = "restaurants_uuid")
	public String getRestaurantsUuid() {
		return this.restaurantsUuid;
	}

	public void setRestaurantsUuid(String restaurantsUuid) {
		this.restaurantsUuid = restaurantsUuid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "favoritesdate", length = 10)
	public Date getFavoritesdate() {
		return this.favoritesdate;
	}

	public void setFavoritesdate(Date favoritesdate) {
		this.favoritesdate = favoritesdate;
	}

}