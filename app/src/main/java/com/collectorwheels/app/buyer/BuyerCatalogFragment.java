package com.collectorwheels.app.buyer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentBuyerCatalogBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.ProductGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuyerCatalogFragment extends Fragment {

    private FragmentBuyerCatalogBinding binding;
    private ProductGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerCatalogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProductGridAdapter(this::openProduct);
        binding.buyerCatalogGrid.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.buyerCatalogGrid.setAdapter(adapter);
        adapter.submit(DemoData.products());

        binding.buyerCatalogSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        applyFilter();
                    }
                });

        binding.buyerCatalogFilter.setOnClickListener(
                v -> Toast.makeText(requireContext(), "Filtros (demo)", Toast.LENGTH_SHORT).show());
        binding.buyerCatalogSort.setOnClickListener(
                v -> Toast.makeText(requireContext(), "Ordenar (demo)", Toast.LENGTH_SHORT).show());
    }

    private void applyFilter() {
        String q =
                binding.buyerCatalogSearch.getText() != null
                        ? binding.buyerCatalogSearch.getText().toString().trim().toLowerCase()
                        : "";
        List<Product> out = new ArrayList<>();
        for (Product p : DemoData.products()) {
            if (!q.isEmpty()
                    && !p.name.toLowerCase().contains(q)
                    && !p.category.toLowerCase().contains(q)) continue;
            out.add(p);
        }
        adapter.submit(out);
    }

    private void openProduct(Product product) {
        if (getActivity() instanceof NavigationHost) {
            ((NavigationHost) getActivity()).openBuyerProductDetail(product.id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
