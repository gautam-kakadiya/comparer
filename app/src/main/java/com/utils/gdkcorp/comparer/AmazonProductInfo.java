package com.utils.gdkcorp.comparer;

/**
 * Created by Gautam Kakadiya on 04-06-2016.
 */
public class AmazonProductInfo {
    private String ASIN;
    private String Title;
    private String Brand;
    private String DetailUrl;
    private String imageURL;
    private String price;

    public void setASIN(String ASIN){
        this.ASIN=ASIN;
    }

    public void setTitle(String Title){
        this.Title=Title;
    }
    public void setBrand(String Brand){
        this.Brand=Brand;
    }
    public void setPrice(String price){
        this.price=price;
    }
    public void setDetailUrl(String DetailUrl){
        this.DetailUrl=DetailUrl;
    }
    public void setImageURL(String imageURL){ this.imageURL = imageURL;}
    public String getImageURL(){return imageURL;}

    public String getASIN(){
        return ASIN;
    }
    public String getTitle(){
        return Title;
    }

    public String getBrand(){
        return Brand;
    }

    public String getDetailUrl(){
        return DetailUrl;
    }

    public String getPrice() {
        return price;
    }
}
