package com.collectorwheels.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface NavigationHost {
    void openBuyerProductDetail(@NonNull String productId);

    void openBuyerCart();

    void openBuyerPayment();

    /** @param productId null crea un producto nuevo */
    void openSellerProductEditor(@Nullable String productId);

    /** @param userId <= 0 crea un usuario nuevo */
    void openAdminUserEditor(long userId);

    void openAdminUsers();

    void openAdminProducts();

    void openAdminSalesReport();

    void popBackStack();
}
