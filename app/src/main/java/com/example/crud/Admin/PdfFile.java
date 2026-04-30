package com.example.crud.Admin;

public class PdfFile {

    private String title, downloadUrl, key;

    public PdfFile() {

    }

    public PdfFile(String title, String downloadUrl, String key) {
        this.title = title;
        this.downloadUrl = downloadUrl;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

