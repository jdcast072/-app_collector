package com.collectorwheels.app.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.MainActivity;
import com.collectorwheels.app.R;
import com.collectorwheels.app.Role;
import com.collectorwheels.app.SessionManager;
import com.collectorwheels.app.databinding.FragmentLoginBinding;
import com.collectorwheels.app.util.BiometricHelper;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] labels = {
            getString(R.string.role_buyer),
            getString(R.string.role_seller),
            getString(R.string.role_admin)
        };
        binding.loginRoleSpinner.setAdapter(
                new ArrayAdapter<>(
                        requireContext(), android.R.layout.simple_spinner_dropdown_item, labels));

        binding.loginForgot.setOnClickListener(
                v ->
                        requireActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.auth_container, new ForgotPasswordFragment())
                                .addToBackStack(null)
                                .commit());

        binding.loginRegister.setOnClickListener(
                v ->
                        requireActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.auth_container, new RegisterFragment())
                                .addToBackStack(null)
                                .commit());

        binding.loginSubmit.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email =
                binding.loginEmail.getText() != null
                        ? binding.loginEmail.getText().toString().trim()
                        : "";
        String pass =
                binding.loginPassword.getText() != null
                        ? binding.loginPassword.getText().toString()
                        : "";
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(requireContext(), R.string.hint_email, Toast.LENGTH_SHORT).show();
            return;
        }
        int pos = binding.loginRoleSpinner.getSelectedItemPosition();
        Role role = Role.fromOrdinal(Math.max(0, Math.min(pos, Role.values().length - 1)));
        boolean bio = binding.loginBiometric.isChecked();
        if (bio && !BiometricHelper.canAuthenticate(requireContext())) {
            Toast.makeText(requireContext(), "Biometría no disponible en este dispositivo", Toast.LENGTH_LONG)
                    .show();
            bio = false;
        }
        SessionManager sm = new SessionManager(requireContext());
        String display = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;
        sm.login(role, display, email, bio);
        startActivity(new Intent(requireContext(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
