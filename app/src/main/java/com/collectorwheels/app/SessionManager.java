package com.collectorwheels.app;

import android.content.Context;
import android.content.SharedPreferences;

public final class SessionManager {
    private static final String PREF = "collector_wheels_session";
    private static final String KEY_LOGGED = "logged";
    private static final String KEY_ROLE = "role";
    private static final String KEY_BIOMETRIC = "biometric";
    private static final String KEY_DISPLAY_NAME = "display_name";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    /**
     * Usa {@link SharedPreferences.Editor#commit()} para que la siguiente pantalla (p. ej. MainActivity)
     * lea ya la sesión persistida; {@code apply()} es asíncrono y puede provocar cierre al volver a Auth.
     */
    public void login(Role role, String displayName, String email, boolean enableBiometric) {
        prefs.edit()
                .putBoolean(KEY_LOGGED, true)
                .putInt(KEY_ROLE, role.ordinal())
                .putBoolean(KEY_BIOMETRIC, enableBiometric)
                .putString(KEY_DISPLAY_NAME, displayName)
                .putString(KEY_EMAIL, email)
                .commit();
    }

    public void logout() {
        prefs.edit()
                .putBoolean(KEY_LOGGED, false)
                .remove(KEY_ROLE)
                .remove(KEY_DISPLAY_NAME)
                .remove(KEY_EMAIL)
                .remove(KEY_BIOMETRIC)
                .commit();
    }

    public Role getRole() {
        return Role.fromOrdinal(prefs.getInt(KEY_ROLE, Role.BUYER.ordinal()));
    }

    public boolean isBiometricEnabled() {
        return prefs.getBoolean(KEY_BIOMETRIC, false);
    }

    public void setBiometricEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_BIOMETRIC, enabled).commit();
    }

    public String getDisplayName() {
        return prefs.getString(KEY_DISPLAY_NAME, "Usuario");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }
}
