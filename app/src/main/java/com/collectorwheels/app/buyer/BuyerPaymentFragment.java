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
import com.collectorwheels.app.databinding.FragmentBuyerPaymentBinding;
import com.collectorwheels.app.util.MoneyFormat;

public class BuyerPaymentFragment extends Fragment {

    private FragmentBuyerPaymentBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        double sub = CartRepository.subtotal();
        double ship = sub > 0 ? 8000 : 0;
        binding.paymentTotal.setText(MoneyFormat.cop(sub + ship));
        binding.paymentConfirm.setOnClickListener(
                v -> {
                    Toast.makeText(requireContext(), R.string.mock_payment, Toast.LENGTH_SHORT).show();
                    CartRepository.clear();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
