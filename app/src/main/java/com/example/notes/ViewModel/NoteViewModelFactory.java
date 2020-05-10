package com.example.notes.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 *Whenever we create a ViewModel class and the Constructor receives a param we need to create a
 * ViewModelFactory class with it's method.
 */
public class NoteViewModelFactory implements ViewModelProvider.Factory {



    private Application mApplication;


    public NoteViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new  NoteViewModel(mApplication);
    }
}
