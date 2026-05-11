package com.collectorwheels.app.model;

import java.util.Objects;

public final class Product {
    public final String id;
    public final String name;
    public final String description;
    public final String category;
    public final double price;
    public final int stock;
    public final boolean premium;
    public final float rating;

    public Product(
            String id,
            String name,
            String description,
            String category,
            double price,
            int stock,
            boolean premium,
            float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.premium = premium;
        this.rating = rating;
    }

    public Product copyWithStock(int newStock) {
        return new Product(id, name, description, category, price, newStock, premium, rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
