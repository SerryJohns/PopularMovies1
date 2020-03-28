package com.example.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Stetho for debugging
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(
                    getApplicationContext())
                    .enableDumpapp(() -> new Stetho.DefaultDumperPluginsBuilder(
                            getApplicationContext()).finish())
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(
                            getApplicationContext())).build()
            );
        }
    }
}
