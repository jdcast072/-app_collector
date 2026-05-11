package com.collectorwheels.app.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyFormat {
    private MoneyFormat() {}

    public static String cop(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return nf.format(value);
    }
}
