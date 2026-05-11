package com.collectorwheels.app.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentAdminUsersBinding;
import com.collectorwheels.app.model.UserAccount;
import com.collectorwheels.app.ui.common.UserRowAdapter;

public class AdminUsersFragment extends Fragment {

    private FragmentAdminUsersBinding binding;
    private UserRowAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.adminUsersAdd.setOnClickListener(
                v -> {
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).openAdminUserEditor(0);
                    }
                });
        adapter =
                new UserRowAdapter(
                        new UserRowAdapter.Listener() {
                            @Override
                            public void onEdit(UserAccount user) {
                                if (getActivity() instanceof NavigationHost) {
                                    ((NavigationHost) getActivity()).openAdminUserEditor(user.id);
                                }
                            }

                            @Override
                            public void onDelete(UserAccount user) {
                                DemoData.removeUser(user.id);
                                refresh();
                                Toast.makeText(requireContext(), com.collectorwheels.app.R.string.mock_deleted, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
        binding.adminUsersList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.adminUsersList.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (adapter != null) adapter.submit(DemoData.users());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
