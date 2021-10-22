package com.privateadvertisements.model;

public enum StatusAd {
    NEW("Новый"),
    SOLD("Продано"),
    OVERDUE("Просроченный");

    private String title;

    StatusAd(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "StatusAd{" +
                "title='" + title + '\'' +
                '}';
    }
}
