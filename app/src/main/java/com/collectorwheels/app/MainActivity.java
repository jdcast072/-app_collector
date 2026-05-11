package com.collectorwheels.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.collectorwheels.app.admin.AdminDashboardFragment;
import com.collectorwheels.app.admin.AdminProductsFragment;
import com.collectorwheels.app.admin.AdminSalesReportFragment;
import com.collectorwheels.app.admin.AdminUserEditorFragment;
import com.collectorwheels.app.admin.AdminUsersFragment;
import com.collectorwheels.app.buyer.BuyerCartFragment;
import com.collectorwheels.app.buyer.BuyerCatalogFragment;
import com.collectorwheels.app.buyer.BuyerHomeFragment;
import com.collectorwheels.app.buyer.BuyerPaymentFragment;
import com.collectorwheels.app.buyer.BuyerProductDetailFragment;
import com.collectorwheels.app.buyer.BuyerProfileFragment;
import com.collectorwheels.app.databinding.ActivityMainBinding;
import com.collectorwheels.app.seller.SellerDashboardFragment;
import com.collectorwheels.app.seller.SellerOrdersFragment;
import com.collectorwheels.app.seller.SellerProductEditorFragment;
import com.collectorwheels.app.seller.SellerProductsFragment;
import com.collectorwheels.app.seller.SellerProfileFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    private ActivityMainBinding binding;
    private SessionManager session;
    private Role role;
    private boolean syncingNavigationSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }
        role = session.getRole();

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        binding.drawerLayout,
                        binding.toolbar,
                        R.string.nav_drawer_open,
                        R.string.nav_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.post(
                () -> {
                    try {
                        View header = binding.navigationView.getHeaderView(0);
                        TextView navSub = header.findViewById(R.id.nav_header_subtitle);
                        if (navSub != null) {
                            navSub.setText(session.getEmail());
                        }
                    } catch (RuntimeException ignored) {
                        // Cabecera aún no disponible en algunos estados de medición.
                    }
                });

        binding.toolbar.setOnMenuItemClickListener(
                item -> {
                    if (item.getItemId() == R.id.menu_notifications) {
                        Toast.makeText(this, R.string.drawer_notifications, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
        binding.toolbar.inflateMenu(R.menu.menu_toolbar_main);

        setupNavigationVisibility();
        setupNavigationBars();
        setupDrawer();
        setupFab();
        setupBack();

        if (savedInstanceState == null) {
            showDefaultRoot();
        }
    }

    private void setupNavigationVisibility() {
        boolean showBottomNav = getResources().getBoolean(R.bool.show_bottom_nav);
        boolean showNavRail = getResources().getBoolean(R.bool.show_nav_rail);
        binding.bottomNav.setVisibility(showBottomNav ? View.VISIBLE : View.GONE);
        binding.navigationRail.setVisibility(showNavRail ? View.VISIBLE : View.GONE);
    }

    private void setupBack() {
        getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback(true) {
                            @Override
                            public void handleOnBackPressed() {
                                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                                    return;
                                }
                                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                    getSupportFragmentManager().popBackStack();
                                    return;
                                }
                                setEnabled(false);
                                getOnBackPressedDispatcher().onBackPressed();
                            }
                        });
    }

    private void setupFab() {
        if (role == Role.SELLER) {
            binding.fabSellerAdd.setVisibility(View.VISIBLE);
            binding.fabSellerAdd.setOnClickListener(v -> openSellerProductEditor(null));
        } else {
            binding.fabSellerAdd.setVisibility(View.GONE);
        }
    }

    private void setupNavigationBars() {
        binding.bottomNav.getMenu().clear();
        binding.navigationRail.getMenu().clear();
        int menuRes =
                role == Role.BUYER
                        ? R.menu.menu_bottom_buyer
                        : role == Role.SELLER
                                ? R.menu.menu_bottom_seller
                                : R.menu.menu_bottom_admin;
        getMenuInflater().inflate(menuRes, binding.bottomNav.getMenu());
        getMenuInflater().inflate(menuRes, binding.navigationRail.getMenu());
        NavigationBarView.OnItemSelectedListener listener =
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return onRootNavigationItem(item.getItemId());
                    }
                };
        binding.bottomNav.setOnItemSelectedListener(listener);
        binding.navigationRail.setOnItemSelectedListener(listener);
    }

    private boolean onRootNavigationItem(int id) {
        if (syncingNavigationSelection) {
            return true;
        }
        clearBackStack();
        if (role == Role.BUYER) {
            if (id == R.id.nav_buyer_home) {
                replaceRoot(new BuyerHomeFragment(), R.string.nav_home);
            } else if (id == R.id.nav_buyer_catalog) {
                replaceRoot(new BuyerCatalogFragment(), R.string.nav_catalog);
            } else if (id == R.id.nav_buyer_cart) {
                replaceRoot(new BuyerCartFragment(), R.string.nav_cart);
            } else if (id == R.id.nav_buyer_profile) {
                replaceRoot(new BuyerProfileFragment(), R.string.nav_profile);
            } else return false;
        } else if (role == Role.SELLER) {
            if (id == R.id.nav_seller_dashboard) {
                replaceRoot(new SellerDashboardFragment(), R.string.nav_dashboard);
            } else if (id == R.id.nav_seller_products) {
                replaceRoot(new SellerProductsFragment(), R.string.nav_products);
            } else if (id == R.id.nav_seller_orders) {
                replaceRoot(new SellerOrdersFragment(), R.string.nav_orders);
            } else if (id == R.id.nav_seller_profile) {
                replaceRoot(new SellerProfileFragment(), R.string.nav_profile);
            } else return false;
        } else {
            if (id == R.id.nav_admin_dashboard) {
                replaceRoot(new AdminDashboardFragment(), R.string.nav_dashboard);
            } else if (id == R.id.nav_admin_users) {
                replaceRoot(new AdminUsersFragment(), R.string.nav_users);
            } else if (id == R.id.nav_admin_products) {
                replaceRoot(new AdminProductsFragment(), R.string.nav_products);
            } else if (id == R.id.nav_admin_reports) {
                replaceRoot(new AdminSalesReportFragment(), R.string.nav_reports);
            } else return false;
        }
        syncNavigationSelection(id);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void syncNavigationSelection(int id) {
        syncingNavigationSelection = true;
        if (binding.bottomNav.getMenu().findItem(id) != null
                && binding.bottomNav.getSelectedItemId() != id) {
            binding.bottomNav.setSelectedItemId(id);
        }
        if (binding.navigationRail.getMenu().findItem(id) != null
                && binding.navigationRail.getSelectedItemId() != id) {
            binding.navigationRail.setSelectedItemId(id);
        }
        syncingNavigationSelection = false;
    }

    private void clearBackStack() {
        getSupportFragmentManager().popBackStackImmediate(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void replaceRoot(Fragment f, int titleRes) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, f)
                .commit();
        binding.toolbar.setTitle(titleRes);
    }

    private void showDefaultRoot() {
        if (role == Role.BUYER) {
            onRootNavigationItem(R.id.nav_buyer_home);
        } else if (role == Role.SELLER) {
            onRootNavigationItem(R.id.nav_seller_dashboard);
        } else {
            onRootNavigationItem(R.id.nav_admin_dashboard);
        }
        binding.bottomNav.post(
                () -> {
                    Fragment current =
                            getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                    if (current != null) {
                        return;
                    }
                    if (role == Role.BUYER) {
                        replaceRoot(new BuyerHomeFragment(), R.string.nav_home);
                    } else if (role == Role.SELLER) {
                        replaceRoot(new SellerDashboardFragment(), R.string.nav_dashboard);
                    } else {
                        replaceRoot(new AdminDashboardFragment(), R.string.nav_dashboard);
                    }
                });
    }

    private void setupDrawer() {
        binding.navigationView.getMenu().clear();
        int menuRes =
                role == Role.BUYER
                        ? R.menu.menu_drawer_buyer
                        : role == Role.SELLER
                                ? R.menu.menu_drawer_seller
                                : R.menu.menu_drawer_admin;
        binding.navigationView.inflateMenu(menuRes);
        binding.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return onDrawerItem(item.getItemId());
                    }
                });
    }

    private boolean onDrawerItem(int id) {
        if (role == Role.BUYER) {
            if (id == R.id.drawer_buyer_logout) {
                logout();
                return true;
            }
            clearBackStack();
            if (id == R.id.drawer_buyer_home) {
                onRootNavigationItem(R.id.nav_buyer_home);
            } else if (id == R.id.drawer_buyer_catalog) {
                onRootNavigationItem(R.id.nav_buyer_catalog);
            } else if (id == R.id.drawer_buyer_cart) {
                onRootNavigationItem(R.id.nav_buyer_cart);
            } else if (id == R.id.drawer_buyer_payment) {
                replaceRoot(new BuyerPaymentFragment(), R.string.payment_title);
            } else if (id == R.id.drawer_buyer_profile) {
                onRootNavigationItem(R.id.nav_buyer_profile);
            } else {
                Toast.makeText(this, R.string.drawer_settings, Toast.LENGTH_SHORT).show();
            }
        } else if (role == Role.SELLER) {
            if (id == R.id.drawer_seller_logout) {
                logout();
                return true;
            }
            clearBackStack();
            if (id == R.id.drawer_seller_dashboard) {
                onRootNavigationItem(R.id.nav_seller_dashboard);
            } else if (id == R.id.drawer_seller_products) {
                onRootNavigationItem(R.id.nav_seller_products);
            } else if (id == R.id.drawer_seller_orders) {
                onRootNavigationItem(R.id.nav_seller_orders);
            } else if (id == R.id.drawer_seller_profile) {
                onRootNavigationItem(R.id.nav_seller_profile);
            } else {
                Toast.makeText(this, R.string.drawer_settings, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (id == R.id.drawer_admin_logout) {
                logout();
                return true;
            }
            clearBackStack();
            if (id == R.id.drawer_admin_dashboard) {
                onRootNavigationItem(R.id.nav_admin_dashboard);
            } else if (id == R.id.drawer_admin_users) {
                onRootNavigationItem(R.id.nav_admin_users);
            } else if (id == R.id.drawer_admin_products) {
                onRootNavigationItem(R.id.nav_admin_products);
            } else if (id == R.id.drawer_admin_sales) {
                onRootNavigationItem(R.id.nav_admin_reports);
            } else {
                Toast.makeText(this, R.string.drawer_settings, Toast.LENGTH_SHORT).show();
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        session.logout();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void openBuyerProductDetail(@NonNull String productId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, BuyerProductDetailFragment.newInstance(productId))
                .addToBackStack("buyer_detail")
                .commit();
        binding.toolbar.setTitle(R.string.nav_catalog);
    }

    @Override
    public void openBuyerCart() {
        onRootNavigationItem(R.id.nav_buyer_cart);
    }

    @Override
    public void openBuyerPayment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, new BuyerPaymentFragment())
                .addToBackStack("pay")
                .commit();
        binding.toolbar.setTitle(R.string.payment_title);
    }

    @Override
    public void openSellerProductEditor(@Nullable String productId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, SellerProductEditorFragment.newInstance(productId))
                .addToBackStack("seller_edit")
                .commit();
        binding.toolbar.setTitle(productId == null ? R.string.new_product : R.string.edit_product);
    }

    @Override
    public void openAdminUserEditor(long userId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, AdminUserEditorFragment.newInstance(userId))
                .addToBackStack("admin_user")
                .commit();
        binding.toolbar.setTitle(userId <= 0 ? R.string.admin_user_new : R.string.admin_user_edit);
    }

    @Override
    public void openAdminUsers() {
        onRootNavigationItem(R.id.nav_admin_users);
    }

    @Override
    public void openAdminProducts() {
        onRootNavigationItem(R.id.nav_admin_products);
    }

    @Override
    public void openAdminSalesReport() {
        onRootNavigationItem(R.id.nav_admin_reports);
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }
}
