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
import com.collectorwheels.app.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.registerSubmit.setOnClickListener(v -> submit());
        binding.registerBackLogin.setOnClickListener(
                v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void submit() {
        if (!binding.registerTerms.isChecked()) {
            Toast.makeText(requireContext(), R.string.terms_accept, Toast.LENGTH_SHORT).show();
            return;
        }
        String p1 =
                binding.registerPassword.getText() != null
                        ? binding.registerPassword.getText().toString()
                        : "";
        String p2 =
                binding.registerPasswordConfirm.getText() != null
                        ? binding.registerPasswordConfirm.getText().toString()
                        : "";
        if (!p1.equals(p2)) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        String name =
                binding.registerName.getText() != null
                        ? binding.registerName.getText().toString().trim()
                        : "";
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), R.string.hint_full_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(requireContext(), R.string.mock_registered, Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
