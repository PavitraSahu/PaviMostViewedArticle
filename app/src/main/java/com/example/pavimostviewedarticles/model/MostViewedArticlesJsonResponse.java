
package com.example.pavimostviewedarticles.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostViewedArticlesJsonResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("copyright")
    @Expose
    public String copyright;
    @SerializedName("num_results")
    @Expose
    public int numResults;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

}
