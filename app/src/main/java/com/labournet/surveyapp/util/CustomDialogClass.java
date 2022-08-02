package com.labournet.surveyapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.labournet.surveyapp.R;


public class CustomDialogClass extends AlertDialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    Context context;

    public CustomDialogClass(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_box);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                Intent i = new
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(i);
                //c.finish
                break;
            case R.id.btn_no:

                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


}