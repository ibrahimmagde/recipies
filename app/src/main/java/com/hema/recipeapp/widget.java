package com.hema.recipeapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hema on 9/28/2017.
 */

public class widget extends  AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.ttt, pendingIntent);
            SharedPreferences preferences = context.getSharedPreferences("SHARED", MODE_PRIVATE);
            String ingre=  preferences.getString("ingredients", "choose your Favorite Recipe ");
            views.setTextViewText(R.id.ttt ,ingre);
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();

        }
    }
}
