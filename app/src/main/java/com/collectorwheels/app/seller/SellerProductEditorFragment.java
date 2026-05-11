package com.collectorwheels.app.seller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.NavigationHost;
import com.collectorwheels.app.R;
import com.collectorwheels.app.data.DemoData;
import com.collectorwheels.app.databinding.FragmentSellerProductEditorBinding;
import com.collectorwheels.app.model.Product;

public class SellerProductEditorFragment extends Fragment {

    private static final String ARG_ID = "productId";

    private FragmentSellerProductEditorBinding binding;
    @Nullable private String productId;

    public static SellerProductEditorFragment newInstance(@Nullable String productId) {
        SellerProductEditorFragment f = new SellerProductEditorFragment();
        Bundle b = new Bundle();
        b.putString(ARG_ID, productId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSellerProductEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productId = getArguments() != null ? getArguments().getString(ARG_ID) : null;
        boolean isNew = productId == null || productId.isEmpty();
        binding.editorTitle.setText(isNew ? getString(R.string.new_product) : getString(R.string.edit_product));
        binding.editorDelete.setVisibility(isNew ? View.GONE : View.VISIBLE);

        if (!isNew) {
            Product p = DemoData.findProduct(productId);
            binding.editorName.setText(p.name);
            binding.editorCategory.setText(p.category);
            binding.editorPrice.setText(String.valueOf((long) p.price));
            binding.editorStock.setText(String.valueOf(p.stock));
            binding.editorPremium.setChecked(p.premium);
            binding.editorDescription.setText(p.description);
        }

        binding.editorSave.setOnClickListener(v -> save(isNew));
        binding.editorDelete.setOnClickListener(
                v -> {
                    if (productId != null) DemoData.removeProduct(productId);
                    Toast.makeText(requireContext(), R.string.mock_deleted, Toast.LENGTH_SHORT).show();
                    if (getActivity() instanceof NavigationHost) {
                        ((NavigationHost) getActivity()).popBackStack();
                    }
                });
    }

    private void save(boolean isNew) {
        String name =
                binding.editorName.getText() != null ? binding.editorName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), R.string.product_name, Toast.LENGTH_SHORT).show();
            return;
        }
        String cat =
                binding.editorCategory.getText() != null
                        ? binding.editorCategory.getText().toString().trim()
                        : "General";
        String desc =
                binding.editorDescription.getText() != null
                        ? binding.editorDescription.getText().toString().trim()
                        : "";
        double price = 0;
        int stock = 0;
        try {
            price =
                    Double.parseDouble(
                            binding.editorPrice.getText() != null
                                    ? binding.editorPrice.getText().toString()
                                    : "0");
            stock =
                    Integer.parseInt(
                            binding.editorStock.getText() != null
                                    ? binding.editorStock.getText().toString()
                                    : "0");
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Precio o stock inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        String id = isNew ? DemoData.nextProductId() : productId;
        Product p =
                new Product(
                        id,
                        name,
                        desc,
                        cat,
                        price,
                        stock,
                        binding.editorPremium.isChecked(),
                        4.5f);
        DemoData.upsertProduct(p);
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
