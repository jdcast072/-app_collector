package com.collectorwheels.app.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.R;
import com.collectorwheels.app.SessionManager;
import com.collectorwheels.app.databinding.FragmentSellerProfileBinding;

public class SellerProfileFragment extends Fragment {

    private FragmentSellerProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSellerProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sm = new SessionManager(requireContext());
        binding.sellerProfileName.setText(sm.getDisplayName());
        binding.sellerProfileEmail.setText(sm.getEmail());
        binding.sellerStoreName.setText("Mi tienda CW");
        binding.sellerPhone.setText("3001234567");
        binding.sellerProfileSave.setOnClickListener(
                v -> Toast.makeText(requireContext(), R.string.mock_saved, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
