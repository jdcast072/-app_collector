package com.collectorwheels.app.buyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.data.CartRepository;
import com.collectorwheels.app.databinding.FragmentBuyerCartBinding;
import com.collectorwheels.app.ui.common.CartAdapter;
import com.collectorwheels.app.util.MoneyFormat;

public class BuyerCartFragment extends Fragment {

    private FragmentBuyerCartBinding binding;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter =
                new CartAdapter(
                        (line, newQty) -> {
                            CartRepository.setQuantity(line.product, newQty);
                            refresh();
                        });
        binding.buyerCartList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.buyerCartList.setAdapter(adapter);
        binding.cartCheckout.setOnClickListener(
                v -> {
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).openBuyerPayment();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        adapter.submit(CartRepository.lines());
        double sub = CartRepository.subtotal();
        double ship = sub > 0 ? 8000 : 0;
        binding.cartSubtotal.setText(MoneyFormat.cop(sub));
        binding.cartShipping.setText(MoneyFormat.cop(ship));
        binding.cartTotal.setText(MoneyFormat.cop(sub + ship));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
