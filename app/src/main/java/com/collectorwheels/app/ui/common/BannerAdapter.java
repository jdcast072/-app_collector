package com.collectorwheels.app.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collectorwheels.app.databinding.ItemBannerPageBinding;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.VH> {

    private final String[] titles;

    public BannerAdapter(String[] titles) {
        this.titles = titles != null ? titles : new String[0];
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerPageBinding b =
                ItemBannerPageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.binding.bannerTitle.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static final class VH extends RecyclerView.ViewHolder {
        final ItemBannerPageBinding binding;

        VH(ItemBannerPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
