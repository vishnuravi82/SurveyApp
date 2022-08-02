package com.labournet.surveyapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.labournet.surveyapp.activity.BasicInformationActivity;
import com.labournet.surveyapp.model.SignInResponse;
import com.labournet.surveyapp.util.CustomFonts;
import com.labournet.surveyapp.util.CustomTypefaceSpan;
import com.labournet.surveyapp.util.InternetStatus;
import com.labournet.surveyapp.util.Keys;
import com.labournet.surveyapp.util.Preferences;
import com.labournet.surveyapp.util.RetrofitClient;
import com.labournet.surveyapp.util.RetrofitConfigInterface;
import com.labournet.surveyapp.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton signIn;
    private AppCompatTextView title, forgotPassword;
    private AppCompatEditText username, password;

    private Preferences preferences;

    private final String TAG = LoginActivity.class.getSimpleName();

    private View server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        preferences=new Preferences(this);
        setupActionBar();
        Assign();

    }

    private void Assign() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) checkPermission();
        signIn = findViewById(R.id.sign_in);
        title = findViewById(R.id.title);
        signIn = findViewById(R.id.sign_in);
        username = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgot);
        CustomFonts.setFontExpanded(this, title);
        CustomFonts.setFontExpanded(this, signIn);
        signIn.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        Typeface font_brandon = Typeface.createFromAsset(getAssets(), "fonts/BrandonText_Bold.otf");
        SpannableStringBuilder ss = new SpannableStringBuilder(getResources().getString(R.string.sign_in));
        ss.setSpan(new CustomTypefaceSpan("", font_brandon), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(ss);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(LoginActivity.this, R.string.no_permission, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
              //  startActivity(new Intent(LoginActivity.this, BasicInformationActivity.class));

                login();
                break;

            default:
                break;
        }
    }
    public void checkPermission() {
        if ((ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(LoginActivity.this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.RECORD_AUDIO}, 0);

        }

    }

    private void login() {
        Utils.dismissTooltip();
        String uname = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        if ("".equals(uname)) {
            Utils.showTooltip("Invalid Username", username);
            return;
        }

        if ("".equals(pass)) {
            Utils.showTooltip("Invalid Password", password);
            return;
        }
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        try {

            Retrofit retrofit = RetrofitClient.getDemoClient();;
            RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
            Call<SignInResponse> call = retrofitConfigInterface.userLogin(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY, uname, pass);
            Log.e(TAG, call.request().url().toString());
//            Toast.makeText(getApplicationContext(),call.request().toString(),Toast.LENGTH_LONG).show();
            call.enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    SignInResponse response1 = response.body();

                    Log.e("response",""+ response.body());

                    if (response1 != null) {
                        if (response1.isApp_status()) {
                            Log.e(TAG,"find"+ response1.getDescription());

                            if (response1.isSuccess()) {
                                preferences.setBoolean(Keys.PREFS_LOGGED_IN, true);
                                preferences.setInt(Keys.PREFS_USER_ID,Integer.valueOf(response1.getUser_id()));
                                Utils.showToast(getApplicationContext(), response1.getDescription(), false);
                                startActivity(new Intent(LoginActivity.this, BasicInformationActivity.class));

                            }
                            }
                               else {


                                Utils.showToast(getApplicationContext(), response1.getDescription(), false);
                            }
                        } else {
                            //app update

                        }
                    }


                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    Log.e("TAG", t.getMessage());


                    try {
                        if (t != null)
                            if (t.getMessage().contains("Unable to resolve host") || t instanceof IOException)
                                Toast.makeText(getApplicationContext(), "Poor internet connection", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
      //  startActivity(new Intent(LoginActivity.this, BasicInformationActivity.class));

    }
}
