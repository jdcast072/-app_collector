package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemDetailImageBinding;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.VH> {

    private final int count;

    public DetailImageAdapter(int count) {
        this.count = Math.max(1, count);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailImageBinding b =
                ItemDetailImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        // Placeholder images only
    }

    @Override
    public int getItemCount() {
        return count;
    }

    static final class VH extends RecyclerView.ViewHolder {
        VH(ItemDetailImageBinding binding) {
            super(binding.getRoot());
        }
    }
}
