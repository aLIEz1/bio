package com.example.bio.biography.domain.model;

public enum BiographyStatus {
    DRAFT(0), PUBLISHED(1);

    private final int code;

    BiographyStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static BiographyStatus of(Integer code) {
        if (code == null) return DRAFT;
        for (BiographyStatus v : values()) {
            if (v.code == code) return v;
        }
        return DRAFT;
    }
}
