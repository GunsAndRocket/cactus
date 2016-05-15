package com.gunsandrocket.ua.cactus.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dnt on 5/14/16.
 */
public class Event implements Serializable {
    public static String EVENT = "event";

    private String tag;
    private String organiser;
    private String name;
    private String description;
    private Date startDate;
    private String vkLink;
    private String place;
    private Integer followers;
    private Date createDate;
    private String imageUrl;

    public Event(String tag, String organiser, String name,
                 String description, Date startDate,
                 String vkLink, String place, Integer followers,
                 Date createDate, String imageUrl) {
        this.tag = tag;
        this.organiser = organiser;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.vkLink = vkLink;
        this.place = place;
        this.followers = followers;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
