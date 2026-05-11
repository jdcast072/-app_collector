package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemProductCardBinding;
import com.collectorwheels.app.model.Product;
import com.collectorwheels.app.util.MoneyFormat;

import java.util.ArrayList;
import java.util.List;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.VH> {

    public interface Listener {
        void onProductClick(Product product);
    }

    private final List<Product> data = new ArrayList<>();
    private final Listener listener;

    public ProductGridAdapter(Listener listener) {
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
        ItemProductCardBinding b =
                ItemProductCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Product p = data.get(position);
        holder.binding.productCardName.setText(p.name);
        holder.binding.productCardPrice.setText(MoneyFormat.cop(p.price));
        holder.binding.productCardBadge.setVisibility(p.premium ? android.view.View.VISIBLE : android.view.View.GONE);
        holder.itemView.setOnClickListener(v -> listener.onProductClick(p));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemProductCardBinding binding;

        VH(ItemProductCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
