package com.example.mockingrepository.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest extends TestCase {
    @Spy
    Repository mRepository;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        //MockitoAnnotations.initMocks(Repository.class);
    }
    @Test
    public void getData() {
       // MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
       // mutableLiveData.setValue("this is login test");
       // doNothing().when(mRepository).getValues("a", "b");
       // doReturn(mRepository.getData()).when(mRepository).getData();
       // when(mRepository.getData()).thenReturn(mRepository.getData());
        when(mRepository.getLiveData("Gerald", "Isaac")).thenReturn(mRepository.mLiveData);
        System.out.println(mRepository.mLiveData.getValue());
    }
}