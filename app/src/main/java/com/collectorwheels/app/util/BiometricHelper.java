package com.collectorwheels.app.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.collectorwheels.app.R;

import java.util.concurrent.Executor;

public final class BiometricHelper {
    private static final int AUTHENTICATORS = BiometricManager.Authenticators.BIOMETRIC_WEAK;

    private BiometricHelper() {}

    public static boolean canAuthenticate(@NonNull Context context) {
        BiometricManager manager = BiometricManager.from(context);
        return manager.canAuthenticate(AUTHENTICATORS) == BiometricManager.BIOMETRIC_SUCCESS;
    }

    public static void showUnlockPrompt(
            @NonNull FragmentActivity activity,
            @NonNull Runnable onSuccess,
            @NonNull Runnable onFallback,
            @NonNull Runnable onCancelled) {
        Executor executor = ContextCompat.getMainExecutor(activity);
        BiometricPrompt prompt =
                new BiometricPrompt(
                        activity,
                        executor,
                        new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationSucceeded(
                                    @NonNull BiometricPrompt.AuthenticationResult result) {
                                onSuccess.run();
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                                    onFallback.run();
                                } else {
                                    onCancelled.run();
                                }
                            }
                        });

        BiometricPrompt.PromptInfo info =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle(activity.getString(R.string.app_name))
                        .setSubtitle(activity.getString(R.string.biometric_unlock_subtitle))
                        .setNegativeButtonText(activity.getString(R.string.biometric_continue_without))
                        .setAllowedAuthenticators(AUTHENTICATORS)
                        .build();
        prompt.authenticate(info);
    }
}
