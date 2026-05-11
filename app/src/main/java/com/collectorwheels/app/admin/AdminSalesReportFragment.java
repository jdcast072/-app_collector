package com.collectorwheels.app.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.R;
import com.collectorwheels.app.databinding.FragmentAdminSalesBinding;
import com.collectorwheels.app.util.MoneyFormat;

public class AdminSalesReportFragment extends Fragment {

    private FragmentAdminSalesBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminSalesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.adminSalesTotal.setText(MoneyFormat.cop(32_450_000));
        binding.adminSalesDetail.setText(
                "Pedidos: 789\nTicket promedio: $41.128\nCategoría top: Premium\nVendedores activos: 42");
        binding.adminSalesExport.setOnClickListener(
                v -> Toast.makeText(requireContext(), "Exportación simulada", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
