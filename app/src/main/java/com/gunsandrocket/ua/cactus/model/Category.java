package com.gunsandrocket.ua.cactus.model;

public class Category {

    public Category(String name, String imageUrl, int eventsCount) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.eventsCount = eventsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(int eventsCount) {
        this.eventsCount = eventsCount;
    }

    private String name;
    private String imageUrl;
    private int eventsCount;



}
