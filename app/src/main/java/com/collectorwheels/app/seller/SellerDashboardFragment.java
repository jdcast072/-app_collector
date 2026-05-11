package com.collectorwheels.app.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentSellerDashboardBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.ProductManageAdapter;
import com.collectorwheels.app.util.MoneyFormat;

public class SellerDashboardFragment extends Fragment {

    private FragmentSellerDashboardBinding binding;
    private ProductManageAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSellerDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sellerSalesValue.setText(MoneyFormat.cop(4_250_000));
        binding.sellerSalesBar.setProgress(72);

        productAdapter =
                new ProductManageAdapter(
                        new ProductManageAdapter.Listener() {
                            @Override
                            public void onEdit(Product product) {
                                if (getActivity() instanceof NavigationHost) {
                                    ((NavigationHost) getActivity()).openSellerProductEditor(product.id);
                                }
                            }

                            @Override
                            public void onDelete(Product product) {
                                DemoData.removeProduct(product.id);
                                productAdapter.submit(DemoData.products());
                            }
                        });
        binding.sellerDashboardProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sellerDashboardProducts.setAdapter(productAdapter);
        productAdapter.submit(DemoData.products());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
