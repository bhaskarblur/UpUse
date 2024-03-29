package com.classified.upuse.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsModel {

    public class adsResp {

        @SerializedName("status")
        public String success;
        @SerializedName("message")
        public String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public adsResult result;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public adsResult getResult() {
            return result;
        }

        public void setResult(adsResult result) {
            this.result = result;
        }
    }

    public class adsResult {
        @SerializedName("product_id")
        public String ad_id;
        public String user_id;
        @SerializedName("posted_by")
        public String post_by;
        @SerializedName("posted_by_image")
        public String post_by_image;
        @SerializedName("posted_by_number")
        public String post_by_number;
        public String ad_category_id;
        @SerializedName("product_category_name")
        public String ad_category_name;
        @SerializedName("product_title")
        public String ad_title;
        @SerializedName("product_name")
        public String product_name;
        @SerializedName("product_description")
        public String description;
        @SerializedName("product_image")
        public List<imagesres> images;

        public String getPost_by_number() {
            return post_by_number;
        }

        public void setPost_by_number(String post_by_number) {
            this.post_by_number = post_by_number;
        }

        public String condition;
        public String in_warranty;
        public String brand;
        @SerializedName("product_sale_price")
        public String selling_price;
        @SerializedName("product_price")
        public String original_price;
        public String model;
        public String purchase_date;
        public String fuel_type;
        public String transmission;
        public String number_of_owners;
        public String ad_type;
        public String kmdriven;
        public String property_type;
        public String land_type;
        public String area;
        public String featured_status;
        @SerializedName("end_date")
        public String promotion_enddate;
        @SerializedName("views")
        public String ad_views;
        public String product_type;
        public String posted_date;
        public String latitude;
        public String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPosted_date() {
            return posted_date;
        }

        public void setPosted_date(String posted_date) {
            this.posted_date = posted_date;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getKmdriven() {
            return kmdriven;
        }

        public void setKmdriven(String kmdriven) {
            this.kmdriven = kmdriven;
        }

        public String getAd_views() {
            return ad_views;
        }

        public void setAd_views(String ad_views) {
            this.ad_views = ad_views;
        }

        public String getPost_by() {
            return post_by;
        }

        public void setPost_by(String post_by) {
            this.post_by = post_by;
        }

        public String getPost_by_image() {
            return post_by_image;
        }

        public void setPost_by_image(String post_by_image) {
            this.post_by_image = post_by_image;
        }

        public String getFeatured_status() {
            return featured_status;
        }

        public void setFeatured_status(String featured_status) {
            this.featured_status = featured_status;
        }

        public String getPromotion_enddate() {
            return promotion_enddate;
        }

        public void setPromotion_enddate(String promotion_enddate) {
            this.promotion_enddate = promotion_enddate;
        }

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAd_category_id() {
            return ad_category_id;
        }

        public void setAd_category_id(String ad_category_id) {
            this.ad_category_id = ad_category_id;
        }

        public String getAd_category_name() {
            return ad_category_name;
        }

        public void setAd_category_name(String ad_category_name) {
            this.ad_category_name = ad_category_name;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<imagesres> getImages() {
            return images;
        }

        public void setImages(List<imagesres> images) {
            this.images = images;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getIn_warranty() {
            return in_warranty;
        }

        public void setIn_warranty(String in_warranty) {
            this.in_warranty = in_warranty;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPurchase_date() {
            return purchase_date;
        }

        public void setPurchase_date(String purchase_date) {
            this.purchase_date = purchase_date;
        }

        public String getFuel_type() {
            return fuel_type;
        }

        public void setFuel_type(String fuel_type) {
            this.fuel_type = fuel_type;
        }

        public String getTransmission() {
            return transmission;
        }

        public void setTransmission(String transmission) {
            this.transmission = transmission;
        }

        public String getNumber_of_owners() {
            return number_of_owners;
        }

        public void setNumber_of_owners(String number_of_owners) {
            this.number_of_owners = number_of_owners;
        }

        public String getAd_type() {
            return ad_type;
        }

        public void setAd_type(String ad_type) {
            this.ad_type = ad_type;
        }

        public String getProperty_type() {
            return property_type;
        }

        public void setProperty_type(String property_type) {
            this.property_type = property_type;
        }

        public String getLand_type() {
            return land_type;
        }

        public void setLand_type(String land_type) {
            this.land_type = land_type;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }

    public class imagesres {
        public String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public class postadsResp {
        public String success;
        public postResult result;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public postResult getResult() {
            return result;
        }

        public void setResult(postResult result) {
            this.result = result;
        }
    }

    public class postResult {
        public String product_id;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }
    }
}
