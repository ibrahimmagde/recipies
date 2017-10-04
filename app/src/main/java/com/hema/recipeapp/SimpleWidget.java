package com.hema.recipeapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by hema on 9/27/2017.
 */

public class SimpleWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_screen_widget);

        // Create an Intent object includes my website address
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);

        //handle click event of the TextView (launch browser and go to my website)
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}