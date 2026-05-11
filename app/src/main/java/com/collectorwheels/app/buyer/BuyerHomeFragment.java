package com.collectorwheels.app.buyer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentBuyerHomeBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.BannerAdapter;
import com.collectorwheels.app.ui.common.ProductGridAdapter;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BuyerHomeFragment extends Fragment {

    private FragmentBuyerHomeBinding binding;
    private ProductGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] banners = {
            "Nuevas colecciones 2026",
            "Monster Trucks destacados",
            "Pistas y lanzadores"
        };
        binding.buyerHomeBanner.setAdapter(new BannerAdapter(banners));

        Set<String> cats = new LinkedHashSet<>();
        for (Product p : DemoData.products()) cats.add(p.category);
        for (String c : cats) {
            Chip chip = new Chip(requireContext());
            chip.setId(View.generateViewId());
            chip.setText(c);
            chip.setCheckable(true);
            binding.buyerHomeCategories.addView(chip);
        }

        adapter = new ProductGridAdapter(this::openProduct);
        binding.buyerHomeGrid.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.buyerHomeGrid.setAdapter(adapter);
        adapter.submit(DemoData.products());

        binding.buyerHomeCategories.setOnCheckedChangeListener((group, checkedId) -> filterByCategory());

        if (binding.buyerHomeCategories.getChildCount() > 0) {
            ((Chip) binding.buyerHomeCategories.getChildAt(0)).setChecked(true);
        } else {
            adapter.submit(DemoData.products());
        }

        binding.buyerHomeSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        filterByCategory();
                    }
                });
    }

    private void filterByCategory() {
        if (binding == null || adapter == null) {
            return;
        }
        List<Product> all = DemoData.products();
        String q =
                binding.buyerHomeSearch.getText() != null
                        ? binding.buyerHomeSearch.getText().toString().trim().toLowerCase()
                        : "";
        List<Product> out = new ArrayList<>();
        int checked = binding.buyerHomeCategories.getCheckedChipId();
        Chip chip = checked == View.NO_ID ? null : binding.buyerHomeCategories.findViewById(checked);
        String cat = chip != null ? chip.getText().toString() : null;
        for (Product p : all) {
            if (cat != null && !p.category.equals(cat)) continue;
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
