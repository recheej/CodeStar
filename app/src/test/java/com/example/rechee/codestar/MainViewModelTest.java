package com.example.rechee.codestar;

import android.app.Application;
import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.MainScreen.User;
import com.example.rechee.codestar.MainScreen.UserNameFormError;
import com.example.rechee.codestar.MainScreen.UserRepository;
import com.example.rechee.codestar.dagger.application.ApplicationComponent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainViewModelTest {

    @Mock
    UserRepository userRepository;

    @Rule
    public TestRule taskExecutorRule = new InstantTaskExecutorRule();

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getUserReturnsExpectedData() throws Exception {

        String usernameOne = "user1";

        final User userOne = new User();
        userOne.setId(1);
        userOne.setLogin(usernameOne);
        userOne.setType("site_admin");
        userOne.setName("rechee");
        userOne.setEmail("test@example.com");

        MutableLiveData<User> userOneLiveData = new MutableLiveData<>();
        userOneLiveData.setValue(userOne);

        when(userRepository.getUser(usernameOne)).thenReturn(userOneLiveData);

        Context context = mock(Context.class);
        MainViewModel viewModel = new MainViewModel(userRepository, context);

        viewModel.getUser(usernameOne, UserNameFormError.ErrorTarget.USERNAME_ONE).observeForever(new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                assertNotNull(user);
                assertEquals(userOne.getId(), user.getId());
                assertEquals(userOne.getLogin(), user.getLogin());
                assertEquals(userOne.getType(), user.getType());
                assertEquals(userOne.getName(), user.getName());
                assertEquals(userOne.getEmail(), user.getEmail());
            }
        });
    }

    @Test
    public void getErrorReturnsProperError() throws Exception {

        final Enumerations.Error testError = Enumerations.Error.INVALID_USERNAME;

        MutableLiveData<Enumerations.Error> testLiveData = new MutableLiveData<>();
        testLiveData.setValue(testError);

        when(userRepository.getError()).thenReturn(testLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        when(context.getString(anyInt())).thenReturn("invalid username");

        MainViewModel viewModel = new MainViewModel(userRepository, context);

        viewModel.getError().observeForever(new Observer<UserNameFormError>() {
            @Override
            public void onChanged(@Nullable UserNameFormError userNameFormError) {
                assertNotNull(userNameFormError);
                assertEquals(testError, userNameFormError.getError());
            }
        });
    }
}