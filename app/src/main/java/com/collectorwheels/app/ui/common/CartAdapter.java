package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemCartLineBinding;
import com.collectorwheels.app.model.CartLine;
import com.collectorwheels.app.util.MoneyFormat;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    public interface Listener {
        void onQuantityChanged(CartLine line, int newQty);
    }

    private final List<CartLine> data = new ArrayList<>();
    private final Listener listener;

    public CartAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<CartLine> lines) {
        data.clear();
        if (lines != null) data.addAll(lines);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartLineBinding b =
                ItemCartLineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CartLine line = data.get(position);
        holder.binding.cartLineName.setText(line.product.name);
        holder.binding.cartLinePrice.setText(MoneyFormat.cop(line.product.price));
        holder.binding.cartLineQty.setText(String.valueOf(line.quantity));
        holder.binding.cartLineMinus.setOnClickListener(
                v -> listener.onQuantityChanged(line, line.quantity - 1));
        holder.binding.cartLinePlus.setOnClickListener(
                v -> listener.onQuantityChanged(line, line.quantity + 1));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemCartLineBinding binding;

        VH(ItemCartLineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
