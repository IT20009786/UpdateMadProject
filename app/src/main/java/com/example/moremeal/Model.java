package com.example.moremeal;

public class Model {

    private  String name;
    private  Integer image;
    private Integer quantity;
    private Double nowPrice;
    private String nowSize;
    private Double mediumPrice;
    private Double largePrice;
    private Double premiumPrice;
    private String myd;



    public Model() {
    }

    public Model(String name, Integer image, Integer quantity, Double nowPrice, String nowSize, Double mediumPrice, Double largePrice, Double premiumPrice, String myd) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.nowPrice = nowPrice;
        this.nowSize = nowSize;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.premiumPrice = premiumPrice;
        this.myd = myd;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getNowSize() { return nowSize; }

    public void setNowSize(String nowSize) { this.nowSize = nowSize; }

    public Double getMediumPrice() { return mediumPrice; }

    public void setMediumPrice(Double mediumPrice) { this.mediumPrice = mediumPrice; }

    public Double getLargePrice() { return largePrice; }

    public void setLargePrice(Double largePrice) { this.largePrice = largePrice; }

    public Double getPremiumPrice() { return premiumPrice; }

    public void setPremiumPrice(Double premiumPrice) { this.premiumPrice = premiumPrice; }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }
}
