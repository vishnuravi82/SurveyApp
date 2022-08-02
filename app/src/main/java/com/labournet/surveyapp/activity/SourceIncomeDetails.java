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

public class SourceIncomeDetails extends AppCompatActivity implements View.OnClickListener {
    Spinner sp2,sp3,sp4,sp5;
    private AppCompatButton submit;

    String[] gender = {"Male", "Female"};
    String[] qualification = {"1 to 10", "10 to 12","Degree"};
    String[] disability = {"yes", "no"};
    String[] residence = {"Residence in village", "no_residence"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_income_details);

        setupActionBar();
        Assign();


    }

    private void Assign() {
        //sp1=findViewById(R.id.sp_cast);
        ; submit=findViewById(R.id.btn_submit);;
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
        startActivity(new Intent(SourceIncomeDetails.this, LoanActivity.class));

    }
}

