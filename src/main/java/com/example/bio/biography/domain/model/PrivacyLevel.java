package com.example.bio.biography.domain.model;

public enum PrivacyLevel {
    PUBLIC(0), PRIVATE(1);

    private final int code;

    PrivacyLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PrivacyLevel of(Integer code) {
        if (code == null) return PUBLIC;
        for (PrivacyLevel v : values()) {
            if (v.code == code) return v;
        }
        return PUBLIC;
    }
}
