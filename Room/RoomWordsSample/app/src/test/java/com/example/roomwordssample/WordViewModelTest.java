package com.example.roomwordssample;



import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mock.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import static java.util.Arrays.asList;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@RunWith(MockitoJUnitRunner.class)
public class WordViewModelTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    WordRepository mRepository;

    @Mock
    WordViewModel mViewModel;

    @Mock
    Application application;



    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getAllWords() throws Exception {

        List<Word> word = asList(
                new Word("Calm"),
                new Word("Clean"));
        MutableLiveData<List<Word>> list = new MutableLiveData<>();
        list.setValue(word);

       // LiveData<List<Word>> newList = null;

        //given
        given(mViewModel.getAllWords()).willReturn(list);
        //when
        LiveData<List<Word>> words = mViewModel.getAllWords();
        //then
        assertEquals(list, words);


    }


    @Test
    public void insert() {
        mViewModel = new WordViewModel(application, mRepository);
        Word word = new Word("Verify");
        mViewModel.insert(word);
        verify(mRepository, times(1)).insert(word);
    }

    @Test
    public void deleteAll() {
        mViewModel = new WordViewModel(application, mRepository);
        mViewModel.deleteAll();
        verify(mRepository, times(1)).deleteAll();
    }

    @Test
    public void delete() {
        mViewModel = new WordViewModel(application, mRepository);
        Word word = new Word("Verify");
        mViewModel.delete(word);
        verify(mRepository, times(1)).delete(word);
    }

    @Test
    public void update() {
        mViewModel = new WordViewModel(application, mRepository);
        Word word = new Word("Verify");
        mViewModel.update(word);
        verify(mRepository, times(1)).update(word);
    }
}