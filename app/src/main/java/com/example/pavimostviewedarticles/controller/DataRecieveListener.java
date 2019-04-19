package com.example.pavimostviewedarticles.controller;

import com.example.pavimostviewedarticles.model.MostViewedArticlesJsonResponse;

public interface DataRecieveListener {

    void onDataReceive(MostViewedArticlesJsonResponse mostViewedArticlesJsonResponse);

    void onDataReceiveError();
}
