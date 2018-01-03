package com.example.rechee.codestar.dagger.application;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rechee on 1/2/2018.
 */

@Module
public class ApplicationContextModule {

    private final Context context;

    public ApplicationContextModule(Context context){
        this.context = context;
    }

    @Provides
    @Named("applicationContext")
    @ApplicationScope
    Context context() {
        return this.context;
    }
}