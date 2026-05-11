package com.collectorwheels.app.model;

public final class OrderSummary {
    public final String id;
    public final String buyerName;
    public final String date;
    public final double total;
    public final String status;

    public OrderSummary(String id, String buyerName, String date, double total, String status) {
        this.id = id;
        this.buyerName = buyerName;
        this.date = date;
        this.total = total;
        this.status = status;
    }
}
