package com.labournet.surveyapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.labournet.surveyapp.LoginActivity;
import com.labournet.surveyapp.R;
import com.labournet.surveyapp.util.CustomFonts;
import com.labournet.surveyapp.util.InternetStatus;
import com.labournet.surveyapp.util.Keys;
import com.labournet.surveyapp.util.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private String TAG = SplashActivity.class.getSimpleName();
    private Handler handler;
    private Preferences prefs;


    private Runnable switchActivity = new Runnable() {
        @Override
        public void run() {

            if (prefs.getBoolean(Keys.PREFS_LOGGED_IN)) {

                startActivity(new Intent(SplashActivity.this, BasicInformationActivity.class));
            } else {
                /*TMA*/
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }


                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | +WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | +WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        ViewGroup group = getWindow().getDecorView().findViewById(android.R.id.content);
        CustomFonts.setCustomFonts(group);
        init();

    }


    private void init() {
        prefs = new Preferences(this);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        // Obtain the FirebaseAnalytics instance.

/*

        //fetch pincode from excel--------------
        InputStream in = getResources().openRawResource(R.raw.pincode);
        String pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" +
                "pincode.xlsx";

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(pathSDCard);

            byte[] buff = new byte[1024];
            int read = 0;

            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ex",e.getMessage());
        }

//----------------------

*/

        handler = new Handler();

            prefs.setBoolean(Keys.PREFS_RELEASE_FLAG, false);
            handler.postDelayed(switchActivity, SPLASH_TIME_OUT);

//        throw new RuntimeException("ss");
    }



    }

