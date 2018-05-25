package com.example.elaraby.baking.steps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.elaraby.baking.R;
import com.example.elaraby.baking.ingredient.Ingredniet;

public class StepsActivityDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepsactivity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ingredniet.class);
                intent.putExtra(StepsActivityDetailFragment.ARG_ITEM_INDEX, getIntent().getIntExtra(StepsActivityDetailFragment.ARG_ITEM_INDEX, 0));
                startActivity(intent);
            }
        });


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putInt(StepsActivityDetailFragment.ARG_ITEM_INDEX,
                    getIntent().getIntExtra(StepsActivityDetailFragment.ARG_ITEM_INDEX, 0));
            arguments.putInt(StepsActivityDetailFragment.ARG_ITEM_POSITION,
                    getIntent().getIntExtra(StepsActivityDetailFragment.ARG_ITEM_POSITION, 0));
            StepsActivityDetailFragment fragment = new StepsActivityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredientactivity_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, StepsActivityListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
