package com.example.rechee.codestar;

import android.app.Application;

import com.example.rechee.codestar.dagger.application.ApplicationComponent;
import com.example.rechee.codestar.dagger.application.ApplicationContextModule;
import com.example.rechee.codestar.dagger.application.DaggerApplicationComponent;

/**
 * Created by reche on 1/1/2018.
 */

public class CodeStarApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
