package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Table {
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleStringProperty qty;
    private SimpleDoubleProperty totalPrice;

    public Table(String name, Double price, int qty) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.qty = new SimpleStringProperty(String.valueOf(qty));
        this.totalPrice = new SimpleDoubleProperty(price * qty);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public String getQty() {
        return qty.get();
    }

    public void setQty(int qty) {
        this.qty.set(String.valueOf(qty));
    }

    public Double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice.set(totalPrice);
    }


    public Double getcolPrice() {
        return price.get();
    }

    public Double getcolTotalPrice() {
        return totalPrice.get();
    }


}
