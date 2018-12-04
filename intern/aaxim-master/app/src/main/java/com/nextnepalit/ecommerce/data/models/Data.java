package com.nextnepalit.ecommerce.data.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("site_logo")
    @Expose
    private String siteLogo;
    @SerializedName("slideshows")
    @Expose
    private List<SlideShow> slideshows = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("sectionone")
    @Expose
    private Section sectionone;
    @SerializedName("sectionthree")
    @Expose
    private Section sectionthree;
    @SerializedName("adsone")
    @Expose
    private String adsone;
    @SerializedName("adstwo")
    @Expose
    private String adstwo;
    @SerializedName("sectionfour")
    @Expose
    private Section sectionfour;
    @SerializedName("sectionfive")
    @Expose
    private Section sectionfive;
    @SerializedName("adsthree")
    @Expose
    private String adsthree;
    @SerializedName("sectionsix")
    @Expose
    private Section sectionsix;
    @SerializedName("sectionseeven")
    @Expose
    private Section sectionseeven;

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public List<SlideShow> getSlideshows() {
        return slideshows;
    }

    public void setSlideshows(List<SlideShow> slideshows) {
        this.slideshows = slideshows;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Section getSectionone() {
        return sectionone;
    }

    public void setSectionone(Section sectionone) {
        this.sectionone = sectionone;
    }

    public Section getSectionthree() {
        return sectionthree;
    }

    public void setSectionthree(Section sectionthree) {
        this.sectionthree = sectionthree;
    }

    public String getAdsone() {
        return adsone;
    }

    public void setAdsone(String adsone) {
        this.adsone = adsone;
    }

    public String getAdstwo() {
        return adstwo;
    }

    public void setAdstwo(String adstwo) {
        this.adstwo = adstwo;
    }

    public Section getSectionfour() {
        return sectionfour;
    }

    public void setSectionfour(Section sectionfour) {
        this.sectionfour = sectionfour;
    }

    public Section getSectionfive() {
        return sectionfive;
    }

    public void setSectionfive(Section sectionfive) {
        this.sectionfive = sectionfive;
    }

    public String getAdsthree() {
        return adsthree;
    }

    public void setAdsthree(String adsthree) {
        this.adsthree = adsthree;
    }

    public Section getSectionsix() {
        return sectionsix;
    }

    public void setSectionsix(Section sectionsix) {
        this.sectionsix = sectionsix;
    }

    public Section getSectionseeven() {
        return sectionseeven;
    }

    public void setSectionseeven(Section sectionseeven) {
        this.sectionseeven = sectionseeven;
    }

}