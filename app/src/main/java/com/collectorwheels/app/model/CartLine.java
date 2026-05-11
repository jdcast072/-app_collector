package com.collectorwheels.app.model;

public final class CartLine {
    public final Product product;
    public int quantity;

    public CartLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double lineTotal() {
        return product.price * quantity;
    }
}
