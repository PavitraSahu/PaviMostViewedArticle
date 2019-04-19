package com.example.pavimostviewedarticles.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pavimostviewedarticles.R;
import com.example.pavimostviewedarticles.controller.DataListAdapter;
import com.example.pavimostviewedarticles.controller.DataProviderAsyncTask;
import com.example.pavimostviewedarticles.controller.DataRecieveListener;
import com.example.pavimostviewedarticles.controller.ListItemClickListener;
import com.example.pavimostviewedarticles.model.Constants;
import com.example.pavimostviewedarticles.model.MediaMetadatum;
import com.example.pavimostviewedarticles.model.MostViewedArticlesJsonResponse;
import com.example.pavimostviewedarticles.model.Result;

public class MainActivity extends AppCompatActivity
        implements DataRecieveListener, ListItemClickListener {

    private ProgressBar progressBar;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorText);

        getData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * GET NEWS DATA
     */
    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        DataProviderAsyncTask dataProviderAsyncTask = new DataProviderAsyncTask(this);
        dataProviderAsyncTask.execute();
    }

    /**
     * ON NEWS DATA RECIEVED
     *
     * @param mostViewedArticlesJsonResponse NEWS DATA MODEL
     **/
    @Override
    public void onDataReceive(MostViewedArticlesJsonResponse mostViewedArticlesJsonResponse) {
        progressBar.setVisibility(View.GONE);
        String status = mostViewedArticlesJsonResponse.status;
        if (status != null && status.equalsIgnoreCase("OK")) {
            for (Result result : mostViewedArticlesJsonResponse.results) {
                for (MediaMetadatum mediaMetadatum : result.media.get(0).mediaMetadata) {
                    if (mediaMetadatum.format.equalsIgnoreCase("Standard Thumbnail")) {
                        result.thumbNailUrl = mediaMetadatum.url;
                    }
                }
            }

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            DataListAdapter adapter = new DataListAdapter(MainActivity.this, mostViewedArticlesJsonResponse.results, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        else {
            onDataReceiveError();
        }
    }

    @Override
    public void onDataReceiveError() {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    /**
     * NEWS ITEM CLICK EVENT
     *
     * @param result NEWS ITEM
     **/
    @Override
    public void onItemClick(Result result) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEW_DETAIL_URL_KEY, result.url);

        Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
