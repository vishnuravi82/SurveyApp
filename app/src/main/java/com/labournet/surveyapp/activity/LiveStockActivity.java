package com.labournet.surveyapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.labournet.surveyapp.R;
import com.labournet.surveyapp.util.CustomTypefaceSpan;

public class LiveStockActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner sp;
    private AppCompatButton submit;


    String[] gender = {"Male", "Female"};
    String[] livestock = {"Bulls", "Cows ","Buffaloes","Sheep","Goat","Poultry"};
    String[] disability = {"yes", "no"};
    String[] residence = {"Residence in village", "no_residence"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livestock_activity);
        setupActionBar();
        Assign();

    }

    private void Assign() {
        //sp1=findViewById(R.id.sp_cast);
        ;
        sp=findViewById(R.id.sp_name);
        submit=findViewById(R.id.btn_submit);;

        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, livestock);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
        submit.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                login();
                break;

            default:
                break;
        }
    }

    private void login() {
        startActivity(new Intent(LiveStockActivity.this, ScarcityActivity.class));

    }
}


