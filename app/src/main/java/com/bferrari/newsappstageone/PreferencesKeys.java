package com.bferrari.newsappstageone;

/**
 * Created by bferrari on 18/11/17.
 */

public enum PreferencesKeys {
    COUNTRY("brazil"),
    CATEGORY("technology");

    private String value;

    PreferencesKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
