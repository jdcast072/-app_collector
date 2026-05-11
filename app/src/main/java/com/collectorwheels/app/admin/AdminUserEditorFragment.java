package com.collectorwheels.app.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.R;
import com.collectorwheels.app.Role;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentAdminUserEditorBinding;
import com.collectorwheels.app.model.UserAccount;

public class AdminUserEditorFragment extends Fragment {

    private static final String ARG_USER = "userId";

    private FragmentAdminUserEditorBinding binding;
    private long userId;

    public static AdminUserEditorFragment newInstance(long userId) {
        AdminUserEditorFragment f = new AdminUserEditorFragment();
        Bundle b = new Bundle();
        b.putLong(ARG_USER, userId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminUserEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = getArguments() != null ? getArguments().getLong(ARG_USER, 0) : 0;
        boolean isNew = userId <= 0;
        binding.adminUserEditorTitle.setText(
                isNew ? getString(R.string.admin_user_new) : getString(R.string.admin_user_edit));
        binding.adminUserDelete.setVisibility(isNew ? View.GONE : View.VISIBLE);

        Spinner spinner = binding.adminUserRole;
        String[] labels = {
            getString(R.string.role_buyer), getString(R.string.role_seller), getString(R.string.role_admin)
        };
        spinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, labels));

        if (!isNew) {
            UserAccount u = DemoData.findUser(userId);
            if (u != null) {
                binding.adminUserName.setText(u.fullName);
                binding.adminUserEmail.setText(u.email);
                binding.adminUserActive.setChecked(u.active);
                spinner.setSelection(Math.max(0, Math.min(u.role.ordinal(), Role.values().length - 1)));
            }
        }

        binding.adminUserSave.setOnClickListener(v -> save(isNew));
        binding.adminUserDelete.setOnClickListener(
                v -> {
                    DemoData.removeUser(userId);
                    Toast.makeText(requireContext(), R.string.mock_deleted, Toast.LENGTH_SHORT).show();
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).popBackStack();
                    }
                });
    }

    private void save(boolean isNew) {
        String name =
                binding.adminUserName.getText() != null
                        ? binding.adminUserName.getText().toString().trim()
                        : "";
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), R.string.hint_full_name, Toast.LENGTH_SHORT).show();
            return;
        }
        String email =
                binding.adminUserEmail.getText() != null
                        ? binding.adminUserEmail.getText().toString().trim()
                        : "";
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), R.string.hint_email, Toast.LENGTH_SHORT).show();
            return;
        }
        int pos = binding.adminUserRole.getSelectedItemPosition();
        Role role = Role.fromOrdinal(Math.max(0, Math.min(pos, Role.values().length - 1)));
        long id = isNew ? DemoData.nextUserId() : userId;
        UserAccount u = new UserAccount(id, name, email, role, binding.adminUserActive.isChecked());
        DemoData.upsertUser(u);
        Toast.makeText(requireContext(), R.string.mock_saved, Toast.LENGTH_SHORT).show();
        if (getActivity() instanceof NavigationHost) {
            ((NavigationHost) getActivity()).popBackStack();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
