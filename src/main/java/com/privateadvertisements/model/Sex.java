package com.privateadvertisements.model;

public enum Sex {
    MALE("Мужчина"),
    FEMALE("Женщина");

    private String title;

    Sex(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "title='" + title + '\'' +
                '}';
    }
}
