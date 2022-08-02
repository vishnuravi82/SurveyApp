package com.labournet.surveyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.labournet.surveyapp.R;
import com.labournet.surveyapp.util.CustomTypefaceSpan;

public class MembershipActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner sp2,sp3,sp4,sp5;
    private AppCompatButton submit;
    private AppCompatTextView add;
    String[] gender = {"Male", "Female"};
    String[] qualification = {"1 to 10", "10 to 12","Degree"};
    String[] she = {"yes", "no"};
    String[] saving = {"yes", "no"};
    String[] group_name = {"name1", "name2"};
    private LinearLayout ll1, container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership_activity);

        setupActionBar();
        Assign();


    }

    private void Assign() {
        //sp1=findViewById(R.id.sp_cast);
        ;submit=findViewById(R.id.btn_submit);;
        sp3=findViewById(R.id.sp_membership);
        sp4=findViewById(R.id.sp_saving);
        sp5=findViewById(R.id.sp_group);
        add = findViewById(R.id.add);
        container = findViewById(R.id.container);
        submit.setOnClickListener(this);
        add.setOnClickListener(this);


        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, she);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adp3);
        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, saving);
        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adp4);
        ArrayAdapter<String> adp5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, group_name);
        adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp5.setAdapter(adp5);

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
            case R.id.add:
                addfamily();
                break;
            default:
                break;
        }
    }

    private void addfamily() {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(R.layout.item_add_family, null);
        container.addView(addView);

    }

    private void login() {
        startActivity(new Intent(MembershipActivity.this, LiveStockActivity.class));

    }
}

