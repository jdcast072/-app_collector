package com.collectorwheels.app.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.R;
import com.collectorwheels.app.databinding.FragmentForgotPasswordBinding;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.forgotSend.setOnClickListener(
                v -> {
                    String email =
                            binding.forgotEmail.getText() != null
                                    ? binding.forgotEmail.getText().toString().trim()
                                    : "";
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(requireContext(), R.string.hint_email, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(requireContext(), R.string.mock_sent, Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                });
        binding.forgotBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
