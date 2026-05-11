package com.collectorwheels.app;

public enum Role {
    BUYER,
    SELLER,
    ADMIN;

    public static Role fromOrdinal(int i) {
        Role[] v = values();
        if (i < 0 || i >= v.length) return BUYER;
        return v[i];
    }
}
