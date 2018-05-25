package com.example.elaraby.baking.ingredient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elaraby.baking.main.MainActivity;
import com.example.elaraby.baking.R;
import com.example.elaraby.baking.steps.StepsActivityDetailFragment;

public class Ingredniet extends AppCompatActivity {

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredniet);
        index = getIntent().getIntExtra(StepsActivityDetailFragment.ARG_ITEM_INDEX, 0);
        RecyclerView recyclerView = findViewById(R.id.ingredeint_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new Adapter());


    }

    class Adapter extends RecyclerView.Adapter<viewHolder> {
        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stepsactivity_list_content, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(final viewHolder holder, final int p) {
            holder.mIdView.setText("" + p);
            holder.mContentView.setText(MainActivity.modelArrayList.get(index).getIngredients().get(p).getIngredient());
        }

        @Override
        public int getItemCount() {
            return MainActivity.modelArrayList.get(index).getIngredients().size();
        }
    }

    class viewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        viewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }


}
