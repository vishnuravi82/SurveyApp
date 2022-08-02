package com.labournet.surveyapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.labournet.surveyapp.LoginActivity;
import com.labournet.surveyapp.R;
import com.labournet.surveyapp.model.BasicFarmer;
import com.labournet.surveyapp.model.PincodeDetailsResponse;
import com.labournet.surveyapp.util.CustomTypefaceSpan;
import com.labournet.surveyapp.util.ImageUtils;
import com.labournet.surveyapp.util.InternetStatus;
import com.labournet.surveyapp.util.Keys;
import com.labournet.surveyapp.util.Preferences;
import com.labournet.surveyapp.util.RetrofitClient;
import com.labournet.surveyapp.util.RetrofitConfigInterface;
import com.labournet.surveyapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BasicInformationActivity extends AppCompatActivity implements View.OnClickListener {
    public boolean fromAdp = false;
    private AppCompatButton submit;
    private Uri uri = null;
    Spinner sp1,sp2,sp3,sp4,sp5;
    String[] cast = {"OC", "BC", "SC","ST"};
    String[] gender = {"Male", "Female"};
    String[] qualification = {"1 to 10", "10 to 12","Degree"};
    String[] disability = {"yes", "no"};
    String[] residence = {"Residence in village", "no_residence"};
    String[] income = {"Less than 30,0000", ">30000 to <50000",">50000 to <100000",">100000 to<2000000",">200000 to<300000",">300000 to >500000"};
    private AppCompatImageView clickCandPic, imgCandPic;
    private Preferences pref;
    private String imagePath = "";
    private ArrayList<File> imageFiles = new ArrayList<>();
    private EditText house_code, fName, lName, H_Number,village_,pincode_et,et_district,state_et,country_et,annual_income;
String H_code,F_name,L_name,house_number,strVillage,strPinCode,strDistrict,strState,strCountry,strAnnualIncome,strCast,strResidence,strfamilyIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_nformation);
        pref = new Preferences(this);
        setupActionBar();
        Assign();

    }

    private void Assign() {
        sp2=findViewById(R.id.sp_income);
        sp3=findViewById(R.id.sp_cast);
        sp4=findViewById(R.id.sp_residence);
        house_code=findViewById(R.id.et_house_code);
        fName=findViewById(R.id.et_fname);
        lName=findViewById(R.id.et_lname);
        H_Number=findViewById(R.id.et_h_number);
        clickCandPic = findViewById(R.id.click);
        imgCandPic = findViewById(R.id.image);
        submit=findViewById(R.id.btn_submit);
        village_=findViewById(R.id.et_village);
        pincode_et=findViewById(R.id.et_pincode);
        et_district=findViewById(R.id.et_district);
        state_et=findViewById(R.id.et_state);
        country_et=findViewById(R.id.et_country);
        annual_income=findViewById(R.id.et_income);
       // pincode_et.addTextChangedListener(presTextWatcher);
        clickCandPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooser();
            }
        });
        TextWatcher presTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() != 6) {
                    pincode_et.setError("Invalid Entry");
                    et_district.setText("");
                    country_et.setText("");
                    state_et.setText("");
                } else if (!InternetStatus.getInstance().isConnectedToInternet(getApplicationContext())) {
                    pincode_et.setError("Poor Internet Connection");
                    et_district.setText("");
                    country_et.setText("");
                    state_et.setText("");
                } else {
                    //call api
                    callApi(0);
//                    tlPresPincode.setErrorEnabled(false);
                }

            }
        };
        pincode_et.addTextChangedListener(presTextWatcher);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, income);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adp2);
        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cast);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adp3);
        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, residence);
        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adp4);
       sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               strfamilyIncome=income[position];
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               strCast=cast[position];
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strResidence=residence[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(this);
    }

    private void callApi(int i) {
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }
        try {


            Retrofit retrofit = RetrofitClient.getPincodeClient();
            RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
            Call<List<PincodeDetailsResponse>> call = retrofitConfigInterface.getPcDetails( Integer.valueOf(pincode_et.getText().toString().trim() ));
            Log.e("TAG", call.request().url().toString());
            call.enqueue(new Callback<List<PincodeDetailsResponse>>() {
                @Override
                public void onResponse(Call<List<PincodeDetailsResponse>> call, Response<List<PincodeDetailsResponse>> response) {
                    final List<PincodeDetailsResponse> response1 = response.body();
                    Log.e("TAG", response.body().toString());
                    if (response1 != null) {
                        Log.e("TAG", response1.get(0).getStatus());
                        if (response1.get(0).getStatus().equals("Success")) {


                            et_district.setText(response1.get(0).getPincodeModels().get(0).getDistrict());
                            state_et.setText(response1.get(0).getPincodeModels().get(0).getState());
                             country_et.setText(response1.get(0).getPincodeModels().get(0).getCountry());
                            //pincode_et.setErrorEnabled(false);

                        }
                    }
                }
                @Override
                public void onFailure(Call<List<PincodeDetailsResponse>> call, Throwable t) {
                    Log.e("TAG", t.getMessage());

                    try {
                        if (t!=null)
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
    }

    private void showChooser() {

        final android.app.AlertDialog dialogBuilder = new AlertDialog.Builder(BasicInformationActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_click_attach, null);

        final AppCompatTextView click = dialogView.findViewById(R.id.click);
        final AppCompatTextView attach = dialogView.findViewById(R.id.attach);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
                dialogBuilder.dismiss();

            }
        });
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFilePicker();
                dialogBuilder.dismiss();

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        dialogBuilder.show();
    }

    private void launchFilePicker() {
        if ((ContextCompat.checkSelfPermission(BasicInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(BasicInformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(BasicInformationActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile.putExtra(Intent.EXTRA_MIME_TYPES, Utils.mimeTypes);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, Utils.GALLERY_PIC_REQUEST);
        }
    }

    private void launchCamera() {
        if ((ContextCompat.checkSelfPermission(BasicInformationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(BasicInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(BasicInformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(BasicInformationActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        } else {

           imagePath = Utils.getImageName(String.valueOf(pref.getInt(Keys.PREFS_USER_ID)), String.valueOf(pref.getInt(Keys.PREFS_CAND_ID)), "candImg");
            uri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", new File(Utils.getImagePath(), imagePath));
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, uri).putExtra("return-data", true), Utils.CAMERA_PIC_REQUEST);

        }
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
               // startActivity(new Intent(BasicInformationActivity.this, FamilyDetailActivity.class));
                //callApi(1);
               // SubmitData();

                break;

            default:
                break;
        }


    }

    private void SubmitData() {

        //String H_code,F_name,L_name,H_number,strVillage,strPinCode,strDistrict,strState,strCountry,strAnnualIncome,strCast,strResidence,strfamilyIncome;
        H_code=house_code.getText().toString().trim();
        F_name=fName.getText().toString().trim();
        L_name=lName.getText().toString().trim();
        strVillage=village_.getText().toString().trim();
        strPinCode=pincode_et.getText().toString().trim();
        strDistrict=et_district.getText().toString().trim();
        strState=state_et.getText().toString().trim();
        strCountry=country_et.getText().toString().trim();
        strAnnualIncome=annual_income.getText().toString().trim();
        house_number=H_Number.getText().toString().trim();
        uploadResponse();


    }

    private void login() {
        startActivity(new Intent(BasicInformationActivity.this, FamilyDetailActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case Utils.CAMERA_PIC_REQUEST:
                    if (!imagePath.equals("")) {
                        File image = null;
                        try {
                            image = new File(ImageUtils.compressImage(Utils.getImagePath() + imagePath));
                            imageFiles.clear();
                            imageFiles.add(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Picasso.with(getApplicationContext()).load(image).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imgCandPic);
                    }
                    break;

                case Utils.GALLERY_PIC_REQUEST:
                    if (data.getData() != null) {
                        uri = data.getData();
                        Log.e("type", getContentResolver().getType(uri));
                        imagePath = Utils.getFileName(String.valueOf(pref.getInt(Keys.PREFS_USER_ID)), String.valueOf(pref.getInt(Keys.PREFS_CAND_ID)), "candImg", Utils.getMimeType(getApplicationContext(), uri));
                        Log.e("img path", imagePath);

                        File tempFile = Utils.copyToTempFile(getApplicationContext(), uri, new File(Utils.getImagePath() + imagePath));
                        imageFiles.clear();
                        imageFiles.add(tempFile);
                        Utils.setFileThumbnail(getApplicationContext(), tempFile, uri, imgCandPic);

                    }
                    break;

                default:
                    break;
            }
        } else {
            imagePath = "";
            imageFiles.clear();
            imgCandPic.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void uploadResponse() {

        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }
//        if (cl.isShowing()) cl.dismiss();

        //int stage = pref.getInt(Keys.PREFS_ACTION);
       /* if (stage == Keys.ACTION_RE_ENROLMENT) {
            stage = Keys.ACTION_ENROLMENT;
        }*/
        Retrofit retrofit = RetrofitClient.getDemoClient();;
        RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
        //Call<SubmitCandResponse> call = retrofitConfigInterface.submitCandResponse(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY, prefs.getInt(Keys.PREFS_USER_ID), prefs.getInt(Keys.PREFS_ROLE_ID), prefs.getString(Keys.PREFS_LATITUDE), prefs.getString(Keys.PREFS_LONGITUDE), Utils.getCurrentDate(Keys.TIMESTAMP_FORMAT), stage, path, Utils.getVersionCode(this), Build.MODEL, Utils.getDeviceId(this), Build.VERSION.RELEASE);
        Call<BasicFarmer> call = retrofitConfigInterface.Basic_farmer(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY, 0, H_code,F_name , house_number, strPinCode, strDistrict, strState, strCountry, strCast, strResidence, strfamilyIncome,strAnnualIncome,imagePath,"1","1");
        //   Toast.makeText(getApplicationContext(),call.request().toString(),Toast.LENGTH_LONG).show();
        Log.e("TAG", "XXX " + call.request().toString());
        call.enqueue(new Callback<BasicFarmer>() {
            @Override
            public void onResponse(Call<BasicFarmer> call, Response<BasicFarmer> response) {
                BasicFarmer response1 = response.body();
                Log.e("response",""+response1.getDescription());
                if (response1 != null) {


                    if (response1.isApp_status()) {

                        if (response1.isSuccess()) {
                            //delete uploaded rows


                            String msg = "Upload Success!";
                            Utils.showToast(BasicInformationActivity.this, msg, false);
                            pref.setString(Keys.PREFS_HOUSEHOLD_ID,response1.getHouse_hold_id());


                          // startActivity(new Intent(BasicInformationActivity.this, FamilyDetailActivity.class));
                            //Utils.showToast(BasicInformationActivity.this, msg, false);

                            showResponseResult(true, msg);
                        }
                    } else {

                        if (response1.isValidation_error()) {
//                                Log.e(TAG,"error_message "+response1.getValidationErrorModels().toString());
                           // ArrayList<ValidationErrorModel> validationErrorModels = new ArrayList<>(response1.getValidationErrorModels());
//                               ArrayList<Integer> invalidIds=new ArrayList<>();
//                                for (int i=0; i<validationErrorModels.size();i++){
//                                    ValidationErrorModel vem=validationErrorModels.get(i);
//                                    invalidIds.add(vem.getRow_id());
//                                }

                            Log.e("response", "" + response.body());
                            Utils.showToast(BasicInformationActivity.this, "No response from server", false);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<BasicFarmer> call, Throwable t) {
                Log.e("error",""+t.getMessage());
                Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(pref.getInt(Keys.PREFS_ACTION)), String.valueOf(pref.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "Submit Response", t.getMessage());
                try {
                    if (t != null)
                        if (t.getMessage().contains("Unable to resolve host") || t instanceof IOException)
                            Toast.makeText(getApplicationContext(), "Poor internet connection", Toast.LENGTH_LONG).show();
                        else {
                            Log.e("TAG", t.getMessage());
                            Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                        }
                } catch (Exception e) {
                    Log.e("TAG", "ex: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void showResponseResult(boolean success, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BasicInformationActivity.this);
        builder.setMessage(msg);
        if (success )
            builder.setTitle("Upload Success!");
        else if (!success) builder.setTitle("Upload Failed!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                if (success)
                     onResume();
                    else onBackPressed();

            }
        });
    }

}
