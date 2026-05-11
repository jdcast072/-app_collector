package com.collectorwheels.app.buyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.SessionManager;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentBuyerProfileBinding;
import com.collectorwheels.app.ui.common.OrderRowAdapter;

public class BuyerProfileFragment extends Fragment {

    private FragmentBuyerProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sm = new SessionManager(requireContext());
        binding.buyerProfileName.setText(sm.getDisplayName());
        binding.buyerProfileEmail.setText(sm.getEmail());

        OrderRowAdapter orders = new OrderRowAdapter();
        binding.buyerProfileOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.buyerProfileOrders.setAdapter(orders);
        orders.submit(DemoData.orders());

        View.OnClickListener toast =
                v -> Toast.makeText(requireContext(), "Opción (demo)", Toast.LENGTH_SHORT).show();
        binding.profileOptionDirections.setOnClickListener(toast);
        binding.profileOptionPayments.setOnClickListener(toast);
        binding.profileOptionFavorites.setOnClickListener(toast);
        binding.profileOptionNotifications.setOnClickListener(toast);
        binding.profileOptionSettings.setOnClickListener(toast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
