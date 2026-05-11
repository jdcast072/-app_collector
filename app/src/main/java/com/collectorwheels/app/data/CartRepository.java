package com.collectorwheels.app.data;

import com.collectorwheels.app.model.CartLine;
import com.collectorwheels.app.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {
    private static final List<CartLine> LINES = new ArrayList<>();

    private CartRepository() {}

    public static void clear() {
        LINES.clear();
    }

    public static List<CartLine> lines() {
        return LINES;
    }

    public static void add(Product product, int qty) {
        for (CartLine line : LINES) {
            if (line.product.id.equals(product.id)) {
                line.quantity += qty;
                return;
            }
        }
        LINES.add(new CartLine(product, qty));
    }

    public static void setQuantity(Product product, int qty) {
        for (int i = 0; i < LINES.size(); i++) {
            CartLine line = LINES.get(i);
            if (line.product.id.equals(product.id)) {
                if (qty <= 0) LINES.remove(i);
                else line.quantity = qty;
                return;
            }
        }
        if (qty > 0) LINES.add(new CartLine(product, qty));
    }

    public static double subtotal() {
        double t = 0;
        for (CartLine line : LINES) t += line.lineTotal();
        return t;
    }

    public static int totalItems() {
        int n = 0;
        for (CartLine line : LINES) n += line.quantity;
        return n;
    }
}
