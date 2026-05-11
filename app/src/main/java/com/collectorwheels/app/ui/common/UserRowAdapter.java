package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.R;
import com.collectorwheels.app.Role;
import com.collectorwheels.app.databinding.ItemUserRowBinding;
import com.collectorwheels.app.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserRowAdapter extends RecyclerView.Adapter<UserRowAdapter.VH> {

    public interface Listener {
        void onEdit(UserAccount user);

        void onDelete(UserAccount user);
    }

    private final List<UserAccount> data = new ArrayList<>();
    private final Listener listener;

    public UserRowAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<UserAccount> users) {
        data.clear();
        if (users != null) data.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserRowBinding b =
                ItemUserRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        UserAccount u = data.get(position);
        holder.binding.userRowName.setText(u.fullName);
        holder.binding.userRowEmail.setText(u.email);
        String roleLabel =
                u.role == Role.ADMIN
                        ? holder.itemView.getContext().getString(R.string.role_admin)
                        : u.role == Role.SELLER
                                ? holder.itemView.getContext().getString(R.string.role_seller)
                                : holder.itemView.getContext().getString(R.string.role_buyer);
        holder.binding.userRowRole.setText(roleLabel + (u.active ? "" : " · Inactivo"));
        holder.binding.userRowEdit.setOnClickListener(v -> listener.onEdit(u));
        holder.binding.userRowDelete.setOnClickListener(v -> listener.onDelete(u));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemUserRowBinding binding;

        VH(ItemUserRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
