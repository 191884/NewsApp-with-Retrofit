package com.yo.newsapp

import android.app.Application
import com.google.android.material.color.DynamicColors

class NewApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}