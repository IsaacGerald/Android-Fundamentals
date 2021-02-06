package com.example.sharedviewmodel.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sharedviewmodel.R;
import com.example.sharedviewmodel.ViewModel.SharedViewModel;

public class FragmentB extends Fragment {

  private SharedViewModel mSharedViewModel;
    private EditText mEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        mEditText = view.findViewById(R.id.edit_text);
        Button addButton = view.findViewById(R.id.button_ok);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = mEditText.getText();
                mSharedViewModel.setText(text);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedViewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(SharedViewModel.class);

        mSharedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                mEditText.setText(charSequence);
            }
        });
    }
}