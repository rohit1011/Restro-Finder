package com.nextnepalit.ecommerce.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageData {

    @SerializedName("relativePath")
    @Expose
    private String relativePath;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("smallUrl")
    @Expose
    private String smallUrl;
    @SerializedName("mediumUrl")
    @Expose
    private String mediumUrl;
    @SerializedName("largeUrl")
    @Expose
    private String largeUrl;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

}