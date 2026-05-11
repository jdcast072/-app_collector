package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemOrderRowBinding;
import com.collectorwheels.app.model.OrderSummary;
import com.collectorwheels.app.util.MoneyFormat;

import java.util.ArrayList;
import java.util.List;

public class OrderRowAdapter extends RecyclerView.Adapter<OrderRowAdapter.VH> {

    private final List<OrderSummary> data = new ArrayList<>();

    public void submit(List<OrderSummary> orders) {
        data.clear();
        if (orders != null) data.addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderRowBinding b =
                ItemOrderRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        OrderSummary o = data.get(position);
        holder.binding.orderRowId.setText(o.id);
        holder.binding.orderRowBuyer.setText(o.buyerName);
        holder.binding.orderRowDate.setText(o.date);
        holder.binding.orderRowTotal.setText(MoneyFormat.cop(o.total));
        holder.binding.orderRowStatus.setText(o.status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemOrderRowBinding binding;

        VH(ItemOrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
