package com.google.codelabs.mdc.java.shrine;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {

    private MaterialButton mButtonNext;
    private TextInputLayout mPasswordTextInput;
    private TextInputEditText mPasswordEditText;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_login_fragment, container, false);

        // Snippet from "Navigate to the next Fragment" section goes here.
        mButtonNext = view.findViewById(R.id.next_button);
        mPasswordTextInput = view.findViewById(R.id.password_text_input);
        mPasswordEditText = view.findViewById(R.id.password_edit_text);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(mPasswordEditText.getText())){
                    mPasswordTextInput.setError(getString(R.string.shr_error_password));
                }else {
                    mPasswordTextInput.setError(null);
                    ((NavigationHost)getActivity()).navigateTo(new ProductGridFragment(), false);
                }
            }
        });

        mPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (isPasswordValid(mPasswordEditText.getText())){
                    mPasswordTextInput.setError(null);
                }
                return false;
            }
        });

        return view;
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    private boolean isPasswordValid(@Nullable Editable text){
        return text != null && text.length() >= 8;
    }
}
