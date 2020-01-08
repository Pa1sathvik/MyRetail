package com.target.myretail.Model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemPrice {

    @JsonProperty("value")
    private Float price;

    @JsonProperty("currency_code")
    private String currency;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ItemPrice{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
