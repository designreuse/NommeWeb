package com.camut.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ViewConsumerClassificationId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_consumer_classification")
public class ViewConsumerClassification  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    
	private String restaurantUuid;
    private String consumerUuid;
    private Integer classificationId;
    private String iosImageUrl;
    private String iosHoverImageUrl;
    private String androidImageUrl;
    private String androidHoverImageUrl;
    private String webImageUrl;
    private String webHoverImageUrl;
    private String classificationName;

   
    // Property accessors

    @Column(name="restaurant_uuid")

    public String getRestaurantUuid() {
        return this.restaurantUuid;
    }
    
    public void setRestaurantUuid(String restaurantUuid) {
        this.restaurantUuid = restaurantUuid;
    }

    @Column(name="consumer_uuid")
    @Id
    public String getConsumerUuid() {
        return this.consumerUuid;
    }
    
    public void setConsumerUuid(String consumerUuid) {
        this.consumerUuid = consumerUuid;
    }

    @Column(name="classification_id", nullable=false)
    @Id
    public Integer getClassificationId() {
        return this.classificationId;
    }
    
    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }

    @Column(name="ios_image_url")

    public String getIosImageUrl() {
        return this.iosImageUrl;
    }
    
    public void setIosImageUrl(String iosImageUrl) {
        this.iosImageUrl = iosImageUrl;
    }

    @Column(name="ios_hover_image_url")

    public String getIosHoverImageUrl() {
        return this.iosHoverImageUrl;
    }
    
    public void setIosHoverImageUrl(String iosHoverImageUrl) {
        this.iosHoverImageUrl = iosHoverImageUrl;
    }

    @Column(name="android_image_url")

    public String getAndroidImageUrl() {
        return this.androidImageUrl;
    }
    
    public void setAndroidImageUrl(String androidImageUrl) {
        this.androidImageUrl = androidImageUrl;
    }

    @Column(name="android_hover_image_url")

    public String getAndroidHoverImageUrl() {
        return this.androidHoverImageUrl;
    }
    
    public void setAndroidHoverImageUrl(String androidHoverImageUrl) {
        this.androidHoverImageUrl = androidHoverImageUrl;
    }

    @Column(name="web_image_url")

    public String getWebImageUrl() {
        return this.webImageUrl;
    }
    
    public void setWebImageUrl(String webImageUrl) {
        this.webImageUrl = webImageUrl;
    }

    @Column(name="web_hover_image_url")
    
    public String getWebHoverImageUrl() {
        return this.webHoverImageUrl;
    }
    
    public void setWebHoverImageUrl(String webHoverImageUrl) {
        this.webHoverImageUrl = webHoverImageUrl;
    }

    @Column(name="classification_name", length=50)

    public String getClassificationName() {
        return this.classificationName;
    }
    
    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ViewConsumerClassification) ) return false;
		 ViewConsumerClassification castOther = ( ViewConsumerClassification ) other; 
         
		 return ( (this.getRestaurantUuid()==castOther.getRestaurantUuid()) || ( this.getRestaurantUuid()!=null && castOther.getRestaurantUuid()!=null && this.getRestaurantUuid().equals(castOther.getRestaurantUuid()) ) )
 && ( (this.getConsumerUuid()==castOther.getConsumerUuid()) || ( this.getConsumerUuid()!=null && castOther.getConsumerUuid()!=null && this.getConsumerUuid().equals(castOther.getConsumerUuid()) ) )
 && ( (this.getClassificationId()==castOther.getClassificationId()) || ( this.getClassificationId()!=null && castOther.getClassificationId()!=null && this.getClassificationId().equals(castOther.getClassificationId()) ) )
 && ( (this.getIosImageUrl()==castOther.getIosImageUrl()) || ( this.getIosImageUrl()!=null && castOther.getIosImageUrl()!=null && this.getIosImageUrl().equals(castOther.getIosImageUrl()) ) )
 && ( (this.getIosHoverImageUrl()==castOther.getIosHoverImageUrl()) || ( this.getIosHoverImageUrl()!=null && castOther.getIosHoverImageUrl()!=null && this.getIosHoverImageUrl().equals(castOther.getIosHoverImageUrl()) ) )
 && ( (this.getAndroidImageUrl()==castOther.getAndroidImageUrl()) || ( this.getAndroidImageUrl()!=null && castOther.getAndroidImageUrl()!=null && this.getAndroidImageUrl().equals(castOther.getAndroidImageUrl()) ) )
 && ( (this.getAndroidHoverImageUrl()==castOther.getAndroidHoverImageUrl()) || ( this.getAndroidHoverImageUrl()!=null && castOther.getAndroidHoverImageUrl()!=null && this.getAndroidHoverImageUrl().equals(castOther.getAndroidHoverImageUrl()) ) )
 && ( (this.getWebImageUrl()==castOther.getWebImageUrl()) || ( this.getWebImageUrl()!=null && castOther.getWebImageUrl()!=null && this.getWebImageUrl().equals(castOther.getWebImageUrl()) ) )
 && ( (this.getWebHoverImageUrl()==castOther.getWebHoverImageUrl()) || ( this.getWebHoverImageUrl()!=null && castOther.getWebHoverImageUrl()!=null && this.getWebHoverImageUrl().equals(castOther.getWebHoverImageUrl()) ) )
 && ( (this.getClassificationName()==castOther.getClassificationName()) || ( this.getClassificationName()!=null && castOther.getClassificationName()!=null && this.getClassificationName().equals(castOther.getClassificationName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRestaurantUuid() == null ? 0 : this.getRestaurantUuid().hashCode() );
         result = 37 * result + ( getConsumerUuid() == null ? 0 : this.getConsumerUuid().hashCode() );
         result = 37 * result + ( getClassificationId() == null ? 0 : this.getClassificationId().hashCode() );
         result = 37 * result + ( getIosImageUrl() == null ? 0 : this.getIosImageUrl().hashCode() );
         result = 37 * result + ( getIosHoverImageUrl() == null ? 0 : this.getIosHoverImageUrl().hashCode() );
         result = 37 * result + ( getAndroidImageUrl() == null ? 0 : this.getAndroidImageUrl().hashCode() );
         result = 37 * result + ( getAndroidHoverImageUrl() == null ? 0 : this.getAndroidHoverImageUrl().hashCode() );
         result = 37 * result + ( getWebImageUrl() == null ? 0 : this.getWebImageUrl().hashCode() );
         result = 37 * result + ( getWebHoverImageUrl() == null ? 0 : this.getWebHoverImageUrl().hashCode() );
         result = 37 * result + ( getClassificationName() == null ? 0 : this.getClassificationName().hashCode() );
         return result;
   }   





}