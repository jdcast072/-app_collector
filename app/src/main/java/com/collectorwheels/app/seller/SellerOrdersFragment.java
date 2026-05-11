package com.collectorwheels.app.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentSellerOrdersBinding;
import com.collectorwheels.app.ui.common.OrderRowAdapter;

public class SellerOrdersFragment extends Fragment {

    private FragmentSellerOrdersBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSellerOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderRowAdapter adapter = new OrderRowAdapter();
        binding.sellerOrdersList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sellerOrdersList.setAdapter(adapter);
        adapter.submit(DemoData.orders());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
