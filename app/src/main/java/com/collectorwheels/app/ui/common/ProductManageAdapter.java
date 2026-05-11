package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemProductManageRowBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.util.MoneyFormat;

import java.util.ArrayList;
import java.util.List;

public class ProductManageAdapter extends RecyclerView.Adapter<ProductManageAdapter.VH> {

    public interface Listener {
        void onEdit(Product product);

        void onDelete(Product product);
    }

    private final List<Product> data = new ArrayList<>();
    private final Listener listener;

    public ProductManageAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<Product> products) {
        data.clear();
        if (products != null) data.addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductManageRowBinding b =
                ItemProductManageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Product p = data.get(position);
        holder.binding.manageProductName.setText(p.name);
        holder.binding.manageProductMeta.setText(
                holder.itemView.getContext().getString(com.collectorwheels.app.R.string.stock, p.stock)
                        + " · "
                        + MoneyFormat.cop(p.price));
        holder.binding.manageProductEdit.setOnClickListener(v -> listener.onEdit(p));
        holder.binding.manageProductDelete.setOnClickListener(v -> listener.onDelete(p));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemProductManageRowBinding binding;

        VH(ItemProductManageRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
