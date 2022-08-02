package com.labournet.surveyapp.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.labournet.surveyapp.util.Utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Athira on 1/6/20.
 */
@SuppressLint("ValidFragment")
public class DatePickerFragment4 extends DialogFragment implements OnDateSetListener {

    private View et;
    private EditText age;

    public DatePickerFragment4() {

    }

    public DatePickerFragment4(View et, EditText age) {
        this.et = et;
        this.age=age;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -15);
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDPD=new DatePickerDialog(getActivity(), this, yy, mm, dd);
//        mDPD.getDatePicker().setMaxDate(System.currentTimeMillis());

        mDPD.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        return mDPD;
    }

    public void onDateSet(DatePicker arg0, int year, int month, int day) {
        GregorianCalendar date2 = new GregorianCalendar(year, month, day);
        if (et != null) {
            if (et instanceof EditText)
                ((EditText) et).setText(DateFormat.format("dd MMM yyyy", date2));

            if (et instanceof TextView)
                ((TextView) et).setText(DateFormat.format("dd MMM yyyy", date2));

            if (et instanceof Button)
                ((Button) et).setText(DateFormat.format("dd MMM yyyy", date2));

            if (et instanceof AppCompatButton)
                ((AppCompatButton) et).setText(DateFormat.format("dd MMM yyyy", date2));

            if (et instanceof AppCompatEditText)
                ((AppCompatEditText) et).setText(DateFormat.format("dd MMM yyyy", date2));

            if (et instanceof AppCompatTextView)
                ((AppCompatTextView) et).setText(DateFormat.format("dd MMM yyyy", date2));

            try {
                //calc age
                age.setText(Utils.calculateAge(year, month, day));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}