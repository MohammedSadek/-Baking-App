package com.example.elaraby.baking.steps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elaraby.baking.R;
import com.example.elaraby.baking.main.MainActivity;
import com.squareup.picasso.Picasso;

public class StepsActivityListActivity extends AppCompatActivity {


    public int index;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepsactivity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        index = getIntent().getIntExtra("index", 0);
        if (findViewById(R.id.ingredientactivity_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.ingredientactivity_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        saveArray();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mTwoPane));
    }

    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", (MainActivity.modelArrayList.get(index).getIngredients().size()));
        for (int i = 0; i < MainActivity.modelArrayList.get(index).getIngredients().size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, MainActivity.modelArrayList.get(index).getIngredients().get(i).getIngredient());
        }
        return mEdit1.commit();
    }

    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(StepsActivityListActivity parent,
                                      boolean twoPane) {
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stepsactivity_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mIdView.setText("" + (position + 1));
            try {
                Picasso.with(getApplicationContext()).load(MainActivity.modelArrayList.get(index).getSteps().get(position).getThumbnailURL()).into(holder.mImageView);
            } catch (IllegalArgumentException e) {
                Picasso.with(getApplicationContext()).load("https://i.pinimg.com/736x/83/aa/11/83aa11042e31db90a14e9ba7e6615780--statue-of-toffee-popcorn.jpg").into(holder.mImageView);
            }
            holder.mContentView.setText(MainActivity.modelArrayList.get(index).getSteps().get(position).getDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(StepsActivityDetailFragment.ARG_ITEM_INDEX, index);
                        arguments.putInt(StepsActivityDetailFragment.ARG_ITEM_POSITION, position);
                        StepsActivityDetailFragment fragment = new StepsActivityDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.ingredientactivity_detail_container, fragment)
                                .commit();
                    } else {

                        Intent intent = new Intent(getApplicationContext(), StepsActivityDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(StepsActivityDetailFragment.ARG_ITEM_INDEX, index);
                        intent.putExtra(StepsActivityDetailFragment.ARG_ITEM_POSITION, position);
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return MainActivity.modelArrayList.get(index).getSteps().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mIdView;
            TextView mContentView;
            ImageView mImageView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mImageView = view.findViewById(R.id.step_image);
            }
        }
    }
}
