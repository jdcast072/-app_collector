package com.collectorwheels.app.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.R;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentAdminDashboardBinding;
import com.collectorwheels.app.util.MoneyFormat;

import java.text.NumberFormat;
import java.util.Locale;

public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CO"));
        binding.adminStatUsers.setText(nf.format(1248));
        binding.adminStatProducts.setText(nf.format(DemoData.products().size()));
        binding.adminStatOrders.setText(nf.format(789));
        binding.adminStatSales.setText(MoneyFormat.cop(32_450_000));

        binding.adminQuickUsers.setOnClickListener(
                v -> {
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).openAdminUsers();
                    }
                });
        binding.adminQuickProducts.setOnClickListener(
                v -> {
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).openAdminProducts();
                    }
                });
        binding.adminQuickSales.setOnClickListener(
                v -> {
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).openAdminSalesReport();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
