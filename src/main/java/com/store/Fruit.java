package com.store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class Fruit {
    private TypeFruit typeFruit;
    private int shelfLife;
    private String deliveryDate;
    private int price;

    public Fruit(TypeFruit typeFruit, int shelfLife, int price) {
        this.typeFruit = typeFruit;
        this.shelfLife = shelfLife;
        this.price = price;
        this.deliveryDate = setDeliveryDate();
    }

    private String setDeliveryDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public TypeFruit getTypeFruit() {
        return typeFruit;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public int getPrice() {
        return price;
    }
}
