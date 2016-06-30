package com.example.android.inventorytracker;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by jleath on 6/28/2016.
 * This is a garbage class that is only here to get Stetho to work
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
