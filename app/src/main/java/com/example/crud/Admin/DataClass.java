package com.example.crud.Admin;

public class DataClass {
    private String key;
    private String imageURL;
    private String caption;

    public DataClass() {
    }

    public DataClass(String key, String imageURL, String caption) {
        this.key = key;
        this.imageURL = imageURL;
        this.caption = caption;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
