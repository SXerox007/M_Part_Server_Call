package com.skeleton.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.skeleton.R;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;

import java.util.HashMap;

/**
 * Login Fragment
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private ValidateEditText validateEditText;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        init(view);
        btnSignIn.setOnClickListener(this);
        return view;
    }

    /**
     * @param view view
     */
    private void init(final View view) {
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnSignIn = (Button) view.findViewById(R.id.btnSignin);
        validateEditText = new ValidateEditText();
    }


    /**
     * @param v view/this/context
     */

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                if (isValidate()) {
                    retrivedata();
                } else {
                    Log.e("debug", "error Login");
                }
                break;
            default:
                Log.e("debug", "default run");
                break;
        }
    }

    /**
     * @return true or false value
     */
    public boolean isValidate() {
        return validateEditText.checkEmail(etEmail)
                && validateEditText.checkPassword(etPassword, false);
    }

    /**
     * retrive data from the server and validate the User
     */
    public void retrivedata() {
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add(KEY_FRAGMENT_EMAIL, etEmail.getText().toString().trim())
                .add(KEY_FRAGMENT_PASSWORD, etPassword.getText().toString().trim())
                .add(KEY_FRAGMENT_DEVICE_TYPE, VALUE_FRAGMENT_DEVICE_TYPE)
                .add(KEY_FRAGMENT_LANGUAGE, VALUE_FRAGMENT_LANGUAGE)
                .add(KEY_FRAGMENT_DEVICE_TOKEN, VALUE_RAGMENT_DEVICE_TOKEN)
                .add(KEY_FRAGMENT_FLUSH_TOKENS, true)
                .add(KEY_FRAGMENT_APP_VERSION, VALUE_FRAGMENT_APP_VERSION).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userLogin(null, hashMap).enqueue(new ResponseResolver<CommonResponse>(getContext(), true, true) {

            @Override
            public void success(final CommonResponse commonResponse) {
                if ("200".equals(commonResponse.getStatusCode())) {
                    Log.e("debug", "sucess signIn");
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.e("debug", "failure SignIn");
            }
        });

    }


}
