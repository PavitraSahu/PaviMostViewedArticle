package com.example.pavimostviewedarticles.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pavimostviewedarticles.R;
import com.example.pavimostviewedarticles.model.MediaMetadatum;
import com.example.pavimostviewedarticles.model.MostViewedArticlesJsonResponse;
import com.example.pavimostviewedarticles.model.Result;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private ArrayList<Result> listdata;
    private Context context;
    private ListItemClickListener listItemClickListener;

    // RecyclerView recyclerView;
    public DataListAdapter(Context context, List<Result> listdata, ListItemClickListener listItemClickListener) {
        this.listdata = (ArrayList<Result>) listdata;
        this.context = context;
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.data_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result myListData = listdata.get(position);

        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.titleTxtView.setText(myListData.title);
        holder.byLineTxtView.setText(myListData.byline);
        holder.dateTxtView.setText(myListData.publishedDate);

        if(myListData.thumbNailUrl!=null && !myListData.thumbNailUrl.trim().isEmpty()) {
            Glide.with(this.context)
                    .load(myListData.thumbNailUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }

        holder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listItemClickListener!=null){
                    listItemClickListener.onItemClick(myListData);
                }
//                Toast.makeText(view.getContext(), "click on item: " + myListData._abstract, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout listItemLayout;
        ImageView imageView;
        TextView titleTxtView, byLineTxtView, dateTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.listItemLayout = itemView.findViewById(R.id.listItem);
            this.imageView = itemView.findViewById(R.id.thumbnailimg);
            this.titleTxtView = itemView.findViewById(R.id.titleTxt);
            this.byLineTxtView = itemView.findViewById(R.id.byLineTxt);
            this.dateTxtView = itemView.findViewById(R.id.dateTxt);
        }
    }
}
