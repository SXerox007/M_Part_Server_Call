package com.skeleton.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.imagepicker.ImageChooser;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SUMIT_THAKUR on 09/05/17.
 */

public class SignUpFragment extends BaseFragment {
    private ImageView ivProfile;
    private MaterialEditText etName, etPhoneNumber, etEmail, etDateOfBirth, etPassword, etConfirmPassword;
    private File file;
    private ImageChooser imageChooser;
    private ValidateEditText validateEditText = new ValidateEditText();
    private Date date = new Date();
    private boolean flag;
    private Button btnRegister;
    private RadioGroup radioGroup;
    private String mGender, mSpace = " ";
    private RadioButton rbMale;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        init(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup group, @IdRes final int checkedId) {
                if (checkedId == R.id.radio_a) {
                    mGender = getString(R.string.radio_button_male);
                } else {
                    mGender = getString(R.string.radio_button_female);
                }
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                imageChooser = new ImageChooser(new ImageChooser.Builder(SignUpFragment.this));
                imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
                    @Override
                    public void loadImage(final List<ChosenImage> list) {
                        file = new File(list.get(0).getOriginalPath());
                        if (file != null) {
                            // Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //ivProfile.setImageBitmap(myBitmap);
                            //ivProfile.setImageURI(Uri.fromFile(file));
                            Glide.with(SignUpFragment.this)
                                    .load(list.get(0).getQueryUri())
                                    .into(ivProfile);
                            Log.d("debug", list.get(0).getQueryUri());
                        } else {
                            Log.e("debug", "Error");

                        }
                    }

                    @Override
                    public void croppedImage(final File mCroppedImage) {

                    }
                });

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                validation();
            }
        });


        return view;
    }

    /**
     * @param view view
     */
    private void init(final View view) {
        ivProfile = (ImageView) view.findViewById(R.id.iv_profile_image);
        etName = (MaterialEditText) view.findViewById(R.id.etName);
        etPhoneNumber = (MaterialEditText) view.findViewById(R.id.etPhoneNumber);
        etEmail = (MaterialEditText) view.findViewById(R.id.etEmail);
        etDateOfBirth = (MaterialEditText) view.findViewById(R.id.etDOB);
        etPassword = (MaterialEditText) view.findViewById(R.id.etPassword);
        etConfirmPassword = (MaterialEditText) view.findViewById(R.id.etConfirmPassword);
        btnRegister = (Button) view.findViewById(R.id.btnSignup);
        radioGroup = (RadioGroup) view.findViewById(R.id.genderSelection);
        mGender = " ";
        rbMale = (RadioButton) view.findViewById(R.id.radio_a);
        rbMale.isChecked();
        enableFoatingEditText(etName, etConfirmPassword, etPhoneNumber, etEmail, etDateOfBirth, etPassword);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        imageChooser.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        imageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Checks if date is in valid format
     *
     * @param editText : editTextDOB containing date
     * @return : true if valid, else returns false
     */
    private boolean checkDOB(final EditText editText) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = editText.getText().toString();
        try {
            date = df.parse(s);
            return true;

        } catch (ParseException e) {
            editText.setError(getString(R.string.error_invalid_data));
            return false;
        }
    }

    /**
     * validation
     */
    private void validation() {
        if (ValidateEditText.checkEmail(etEmail)
                && validateEditText.checkPassword(etPassword, false)
                && validateEditText.checkPassword(etConfirmPassword, true)
                && validateEditText.checkName(etName, true)
                && validateEditText.checkPhoneNumber(etPhoneNumber)
                && validateEditText.comparePassword(etPassword, etConfirmPassword)
                && validateEditText.genericEmpty(etDateOfBirth, getString(R.string.error_invalid_data))) {
            flag = checkDOB(etDateOfBirth);


            if (flag) {
                if (!mGender.equals(mSpace)) {
                    Log.e("debug", "Sucess");
                } else {
                    Log.e("debug", "Error1");
                }
            } else {
                Log.e("debug", "Error2");
            }
        } else {
            Log.e("debug", "Error3");
        }
    }

    /**
     * Enable floating label for {@link MaterialEditText}
     *
     * @param editTexts :list of editText
     */
    public static void enableFoatingEditText(final MaterialEditText... editTexts) {
        for (MaterialEditText editText : editTexts) {
            editText.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        }
    }

}
