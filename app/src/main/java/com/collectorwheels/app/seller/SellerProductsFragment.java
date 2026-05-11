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
import com.collectorwheels.app.databinding.FragmentSellerProductsBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.ProductManageAdapter;

public class SellerProductsFragment extends Fragment {

    private FragmentSellerProductsBinding binding;
    private ProductManageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSellerProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter =
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
                                refresh();
                            }
                        });
        binding.sellerProductsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sellerProductsList.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (adapter != null) adapter.submit(DemoData.products());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
