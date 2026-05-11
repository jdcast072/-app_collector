package com.collectorwheels.app.data;

import com.collectorwheels.app.Role;
import com.collectorwheels.app.model.OrderSummary;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.model.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DemoData {
    private static final List<Product> PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(
                    "hw-001",
                    "Nissan Skyline GT-R",
                    "Modelo premium con detalle de carrocería metálica y rines real riders.",
                    "Premium",
                    89900,
                    12,
                    true,
                    4.8f),
            new Product(
                    "hw-002",
                    "Bone Shaker",
                    "Clásico de colección con llamas y chasis chromado.",
                    "Clásicos",
                    45900,
                    30,
                    false,
                    4.5f),
            new Product(
                    "hw-003",
                    "Monster Truck Tiger",
                    "Monster truck con suspensión alta y neumáticos over-sized.",
                    "Monster Trucks",
                    52900,
                    8,
                    false,
                    4.6f),
            new Product(
                    "hw-004",
                    "Loop de pista Corkscrew",
                    "Set de pista para conectar con tus lanzadores.",
                    "Pistas",
                    129900,
                    5,
                    false,
                    4.3f),
            new Product(
                    "hw-005",
                    "Honda Civic Custom",
                    "Tuning street con alerón y pintura mate.",
                    "Premium",
                    67900,
                    15,
                    true,
                    4.7f),
            new Product(
                    "hw-006",
                    "Batmobile Animated",
                    "Edición especial con empaque protector.",
                    "Clásicos",
                    99900,
                    6,
                    true,
                    4.9f)));

    private static final List<UserAccount> USERS = new ArrayList<>(Arrays.asList(
            new UserAccount(1, "Ana López", "ana@mail.com", Role.BUYER, true),
            new UserAccount(2, "Carlos Ruiz", "carlos@mail.com", Role.SELLER, true),
            new UserAccount(3, "María Admin", "admin@cw.com", Role.ADMIN, true),
            new UserAccount(4, "Luis Vendedor", "luis@mail.com", Role.SELLER, false)));

    private static final List<OrderSummary> ORDERS = new ArrayList<>(Arrays.asList(
            new OrderSummary("ORD-1001", "Ana López", "2026-05-02", 189800, "Enviado"),
            new OrderSummary("ORD-1002", "Pedro Gómez", "2026-05-04", 95900, "Preparando"),
            new OrderSummary("ORD-1003", "Lucía M.", "2026-05-07", 256700, "Entregado")));

    private DemoData() {}

    public static List<Product> products() {
        return PRODUCTS;
    }

    public static Product findProduct(String id) {
        for (Product p : PRODUCTS) {
            if (p.id.equals(id)) return p;
        }
        return PRODUCTS.get(0);
    }

    public static List<UserAccount> users() {
        return USERS;
    }

    public static UserAccount findUser(long id) {
        for (UserAccount u : USERS) {
            if (u.id == id) return u;
        }
        return null;
    }

    public static List<OrderSummary> orders() {
        return ORDERS;
    }

    public static void upsertUser(UserAccount user) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).id == user.id) {
                USERS.set(i, user);
                return;
            }
        }
        USERS.add(user);
    }

    public static void removeUser(long id) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).id == id) {
                USERS.remove(i);
                return;
            }
        }
    }

    public static long nextUserId() {
        long max = 0;
        for (UserAccount u : USERS) max = Math.max(max, u.id);
        return max + 1;
    }

    public static void upsertProduct(Product product) {
        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).id.equals(product.id)) {
                PRODUCTS.set(i, product);
                return;
            }
        }
        PRODUCTS.add(product);
    }

    public static void removeProduct(String id) {
        PRODUCTS.removeIf(p -> p.id.equals(id));
    }

    public static String nextProductId() {
        int max = 0;
        for (Product p : PRODUCTS) {
            try {
                int n = Integer.parseInt(p.id.replace("hw-", ""));
                max = Math.max(max, n);
            } catch (Exception ignored) {
            }
        }
        return "hw-" + String.format("%03d", max + 1);
    }
}
