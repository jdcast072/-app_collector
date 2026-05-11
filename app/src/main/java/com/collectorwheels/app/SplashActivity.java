package com.collectorwheels.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.collectorwheels.app.util.BiometricHelper;

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_MS = 900L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable proceed =
            () -> {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                SessionManager sm = new SessionManager(this);
                if (!sm.isLoggedIn()) {
                    goAuth();
                    return;
                }
                if (sm.isBiometricEnabled() && BiometricHelper.canAuthenticate(this)) {
                    // Tras el primer layout para evitar mostrar el prompt con ventana inestable.
                    getWindow()
                            .getDecorView()
                            .post(
                                    () -> {
                                        if (isFinishing() || isDestroyed()) {
                                            return;
                                        }
                                        BiometricHelper.showUnlockPrompt(
                                                SplashActivity.this,
                                                this::goMain,
                                                () -> {
                                                    sm.setBiometricEnabled(false);
                                                    goMain();
                                                },
                                                () -> {
                                                    sm.logout();
                                                    goAuth();
                                                });
                                    });
                } else {
                    goMain();
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_CollectorWheels_Splash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(proceed, DELAY_MS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(proceed);
    }

    private void goAuth() {
        if (isFinishing()) {
            return;
        }
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    private void goMain() {
        if (isFinishing()) {
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
