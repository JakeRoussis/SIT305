package com.example.tourism;

public class Places {

    private int image, id;
    private String title, description;

    public Places(int id, int image, String title, String description) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.id = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String description) {
        this.description = description;
    }
}
