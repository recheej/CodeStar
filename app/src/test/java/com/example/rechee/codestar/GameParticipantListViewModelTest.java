package com.example.rechee.codestar;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.rechee.codestar.GameWinner.GameParticipantListViewModel;
import com.example.rechee.codestar.GameWinner.Repo;
import com.example.rechee.codestar.GameWinner.RepoRepository;
import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.MainScreen.User;
import com.example.rechee.codestar.MainScreen.UserNameFormError;
import com.example.rechee.codestar.MainScreen.UserRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameParticipantListViewModelTest {

    @Mock
    RepoRepository repoRepository;

    @Rule
    public TestRule taskExecutorRule = new InstantTaskExecutorRule();

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getReposMatchesRepoData() throws Exception {

        Repo repoOne = new Repo();
        repoOne.setId(1);
        repoOne.setName("test repo");
        repoOne.setFullName("test repo 22");
        repoOne.setStargazersCount(22);

        Repo repoTwo = new Repo();
        repoTwo.setId(2);
        repoTwo.setName("test repo 2");
        repoTwo.setFullName("test repo 2222");
        repoTwo.setStargazersCount(50);

        final List<Repo> repos = new ArrayList<>();
        repos.add(repoOne);
        repos.add(repoTwo);

        MutableLiveData<List<Repo>> repoLiveData = new MutableLiveData<>();
        repoLiveData.setValue(repos);

        String testUsername = "username1";

        when(repoRepository.getRepos(testUsername)).thenReturn(repoLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        GameParticipantListViewModel viewModel = new GameParticipantListViewModel(repoRepository,
                context);

        viewModel.setUsername(testUsername);
        viewModel.getRepos().observeForever(new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> output) {
                assertNotNull(output);
                assertEquals(repos.size(), output.size());

                for (Repo repo : repos) {
                    for (Repo outputRepo : output) {
                        if(Objects.equals(repo.getId(), outputRepo.getId())){
                            assertEquals(repo.getName(), outputRepo.getName());
                            assertEquals(repo.getFullName(), outputRepo.getFullName());
                            assertEquals(repo.getStargazersCount(), outputRepo.getStargazersCount());
                        }
                    }
                }
            }
        });
    }

    @Test
    public void getReposSortedByStarCount() throws Exception {

        Repo repoOne = new Repo();
        repoOne.setId(1);
        repoOne.setName("test repo");
        repoOne.setFullName("test repo 22");
        repoOne.setStargazersCount(22);

        final Repo repoTwo = new Repo();
        repoTwo.setId(2);
        repoTwo.setName("test repo 2");
        repoTwo.setFullName("test repo 2222");
        repoTwo.setStargazersCount(50);

        final List<Repo> repos = new ArrayList<>();
        repos.add(repoOne);
        repos.add(repoTwo);

        MutableLiveData<List<Repo>> repoLiveData = new MutableLiveData<>();
        repoLiveData.setValue(repos);

        String testUsername = "username1";

        when(repoRepository.getRepos(testUsername)).thenReturn(repoLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        GameParticipantListViewModel viewModel = new GameParticipantListViewModel(repoRepository,
                context);

        viewModel.setUsername(testUsername);
        viewModel.getRepos().observeForever(new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> output) {
                assertNotNull(output);

                assertTrue(output.get(0).getStargazersCount() > output.get(1).getStargazersCount());
                assertEquals(repoTwo.getStargazersCount(), output.get(0).getStargazersCount());
            }
        });
    }

    @Test
    public void getToalStarCountReturnsSum() throws Exception {

        final Repo repoOne = new Repo();
        repoOne.setId(1);
        repoOne.setName("test repo");
        repoOne.setFullName("test repo 22");
        repoOne.setStargazersCount(22);

        final Repo repoTwo = new Repo();
        repoTwo.setId(2);
        repoTwo.setName("test repo 2");
        repoTwo.setFullName("test repo 2222");
        repoTwo.setStargazersCount(50);

        final List<Repo> repos = new ArrayList<>();
        repos.add(repoOne);
        repos.add(repoTwo);

        MutableLiveData<List<Repo>> repoLiveData = new MutableLiveData<>();
        repoLiveData.setValue(repos);

        String testUsername = "username1";

        when(repoRepository.getRepos(testUsername)).thenReturn(repoLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        GameParticipantListViewModel viewModel = new GameParticipantListViewModel(repoRepository,
                context);

        viewModel.setUsername(testUsername);
        viewModel.getTotalStarCount().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer outputSum) {
                assertNotNull(outputSum);

                int expectedSum = repoOne.getStargazersCount() + repoTwo.getStargazersCount();

                assertTrue(expectedSum == outputSum);
            }
        });
    }

    @Test
    public void getReposHandlesEmptyList() throws Exception {

        final List<Repo> repos = new ArrayList<>();

        MutableLiveData<List<Repo>> repoLiveData = new MutableLiveData<>();
        repoLiveData.setValue(repos);

        String testUsername = "username1";
        when(repoRepository.getRepos(testUsername)).thenReturn(repoLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        GameParticipantListViewModel viewModel = new GameParticipantListViewModel(repoRepository,
                context);

        viewModel.setUsername(testUsername);
        viewModel.getRepos().observeForever(new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> output) {
                assertNotNull(output);
                assertEquals(0, output.size());
            }
        });
    }

    @Test
    public void getReposHandlesNullList() throws Exception {

        final List<Repo> repos = null;

        MutableLiveData<List<Repo>> repoLiveData = new MutableLiveData<>();
        repoLiveData.setValue(repos);

        String testUsername = "username1";

        when(repoRepository.getRepos(testUsername)).thenReturn(repoLiveData);

        CodeStarApplication context = mock(CodeStarApplication.class);
        GameParticipantListViewModel viewModel = new GameParticipantListViewModel(repoRepository,
                context);

        viewModel.setUsername(testUsername);
        viewModel.getRepos().observeForever(new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> output) {
                assertNotNull(output);
                assertEquals(0, output.size());
            }
        });
    }
}