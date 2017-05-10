package com.skeleton.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.skeleton.R;

/**
 * Created by SUMIT_THAKUR on 09/05/17.
 */

public class LoginFragment extends BaseFragment {
    private EditText etEmail,etPassword;
    private Button btnSignIn;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        init(view);


        return view;
    }

    /**
     *
     */
    private void init(View view){
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnSignIn = (Button) view.findViewById(R.id.btnSignin);
    }


}
