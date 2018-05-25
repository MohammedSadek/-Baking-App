package com.example.elaraby.baking.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elaraby.baking.models.Model;
import com.example.elaraby.baking.R;
import com.example.elaraby.baking.steps.StepsActivityListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<mainHolder> {
    ArrayList<Model> modelArrayList;
    Context context;

    public MainRecyclerAdapter(ArrayList<Model> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @Override
    public mainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_list_item, null);
        return new mainHolder(view);
    }

    @Override
    public void onBindViewHolder(mainHolder holder, final int position) {
        holder.name.setText(modelArrayList.get(position).getName());
        if (!modelArrayList.get(position).getImage().equals("")) {
            Picasso.with(context).load(modelArrayList.get(position).getImage()).into(holder.thumb);
        } else {
            Picasso.with(context).load("https://i.pinimg.com/736x/83/aa/11/83aa11042e31db90a14e9ba7e6615780--statue-of-toffee-popcorn.jpg").into(holder.thumb);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StepsActivityListActivity.class);
                intent.putExtra("index", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}

class mainHolder extends RecyclerView.ViewHolder {

    ImageView thumb;
    TextView name;

    public mainHolder(View itemView) {
        super(itemView);
        thumb = itemView.findViewById(R.id.baking_image);
        name = itemView.findViewById(R.id.baking_name);
    }
}
