package com.nextnepalit.ecommerce.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryData {
    @SerializedName("data")
    @Expose
    private List<Categories> data = null;

    public List<Categories> getData() {
        return data;
    }

    public void setData(List<Categories> data) {
        this.data = data;
    }

}
