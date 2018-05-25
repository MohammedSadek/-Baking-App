package com.example.elaraby.baking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;


public class WidgetServices extends RemoteViewsService {
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new factory();
    }

    class factory implements RemoteViewsFactory {

        @Override
        public void onCreate() {
            loadArray();
        }

        @Override
        public void onDataSetChanged() {
            loadArray();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d("POSITION", "" + position);
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.stepsactivity_list_content);
            views.setTextViewText(R.id.id_text, "" + (position) + 1);
            views.setTextViewText(R.id.content, arrayList.get(position));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    public void loadArray() {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        arrayList.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for (int i = 0; i < size; i++) {
            arrayList.add(mSharedPreference1.getString("Status_" + i, null));
        }
    }
}
