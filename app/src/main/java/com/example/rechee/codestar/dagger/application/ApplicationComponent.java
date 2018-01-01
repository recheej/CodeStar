package com.example.rechee.codestar.dagger.application;

import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;
import com.example.rechee.codestar.dagger.viewmodel.ViewModelComponent;

import dagger.Component;

/**
 * Created by reche on 1/1/2018.
 */

@ApplicationScope
@Component(modules={})
public interface ApplicationComponent {
    ViewModelComponent newViewModelComponent(RepositoryModule repositoryModule);
}
