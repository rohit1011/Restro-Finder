package com.nextnepalit.ecommerce.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("data")
    @Expose
    private List<SectionData> sectionData = null;
    @SerializedName("title")
    @Expose
    private String title;

    public List<SectionData> getSectionData() {
        return sectionData;
    }

    public void setSectionData(List<SectionData> sectionData) {
        this.sectionData = sectionData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}