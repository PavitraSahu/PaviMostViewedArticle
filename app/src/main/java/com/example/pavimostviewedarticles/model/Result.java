
package com.example.pavimostviewedarticles.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("byline")
    @Expose
    public String byline;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("published_date")
    @Expose
    public String publishedDate;

    @SerializedName("media")
    @Expose
    public List<Medium> media = null;

    public String thumbNailUrl;

}
