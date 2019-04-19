
package com.example.pavimostviewedarticles.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("media-metadata")
    @Expose
    public List<MediaMetadatum> mediaMetadata = null;
}
