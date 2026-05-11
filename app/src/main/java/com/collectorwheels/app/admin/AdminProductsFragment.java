package com.collectorwheels.app.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.R;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentAdminProductsBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.ProductManageAdapter;

public class AdminProductsFragment extends Fragment {

    private FragmentAdminProductsBinding binding;
    private ProductManageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminProductsBinding.inflate(inflater, container, false);
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
                                Toast.makeText(requireContext(), R.string.mock_deleted, Toast.LENGTH_SHORT).show();
                            }
                        });
        binding.adminProductsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.adminProductsList.setAdapter(adapter);
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
