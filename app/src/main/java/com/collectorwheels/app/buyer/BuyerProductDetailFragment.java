package com.collectorwheels.app.buyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.R;
import com.collectorwheels.app.data.CartRepository;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentBuyerProductDetailBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.ui.common.DetailImageAdapter;
import com.collectorwheels.app.util.MoneyFormat;

public class BuyerProductDetailFragment extends Fragment {

    private static final String ARG_ID = "productId";

    private FragmentBuyerProductDetailBinding binding;
    private Product product;
    private int qty = 1;

    public static BuyerProductDetailFragment newInstance(String productId) {
        BuyerProductDetailFragment f = new BuyerProductDetailFragment();
        Bundle b = new Bundle();
        b.putString(ARG_ID, productId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = getArguments() != null ? getArguments().getString(ARG_ID) : null;
        product = DemoData.findProduct(id != null ? id : "hw-001");
        binding.detailImages.setAdapter(new DetailImageAdapter(3));
        binding.detailTitle.setText(product.name);
        binding.detailPrice.setText(MoneyFormat.cop(product.price));
        binding.detailPremium.setVisibility(product.premium ? View.VISIBLE : View.GONE);
        binding.detailRating.setText("★ " + product.rating);
        binding.detailDescription.setText(product.description);
        binding.detailStock.setText(getString(R.string.stock, product.stock));
        binding.detailQtyValue.setText(String.valueOf(qty));

        binding.detailQtyMinus.setOnClickListener(
                v -> {
                    qty = Math.max(1, qty - 1);
                    binding.detailQtyValue.setText(String.valueOf(qty));
                });
        binding.detailQtyPlus.setOnClickListener(
                v -> {
                    qty = Math.min(product.stock, qty + 1);
                    binding.detailQtyValue.setText(String.valueOf(qty));
                });
        binding.detailAddCart.setOnClickListener(
                v -> {
                    CartRepository.add(product, qty);
                    Toast.makeText(requireContext(), R.string.add_to_cart, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
