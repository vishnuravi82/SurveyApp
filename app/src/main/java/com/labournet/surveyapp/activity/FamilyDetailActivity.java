package com.labournet.surveyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.labournet.surveyapp.R;
import com.labournet.surveyapp.fragment.DatePickerFragment4;
import com.labournet.surveyapp.model.CandStatusHistoryModel;
import com.labournet.surveyapp.model.DefaultResponse;
import com.labournet.surveyapp.model.FamilyMemberModel;
import com.labournet.surveyapp.model.FileModel;
import com.labournet.surveyapp.model.SubmitFamily;
import com.labournet.surveyapp.util.CustomTypefaceSpan;
import com.labournet.surveyapp.util.Database;
import com.labournet.surveyapp.util.InternetStatus;
import com.labournet.surveyapp.util.Keys;
import com.labournet.surveyapp.util.Preferences;
import com.labournet.surveyapp.util.RetrofitClient;
import com.labournet.surveyapp.util.RetrofitConfigInterface;
import com.labournet.surveyapp.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FamilyDetailActivity  extends AppCompatActivity implements View.OnClickListener {
    Spinner sp1,sp2,sp3,sp4,sp5,sp6;
    private AppCompatButton submit;
    private String xmlPath = "";
    private AppCompatTextView add,Date;
    EditText Age_et,name_et,relation_et,carno_et;
    String[] gender = {"Male", "Female"};
    String[] qualification = {"SSC", "Inter","Degree","Technica"};
    String[] disability = {"yes", "no"};
    String[] Mnscard = {"yes", "no"};
    String[] ocupation = {"Agriculture", "Agriculture Labour","Wage Labour"};
    String[] income = {"Less than 30,0000", ">30000 to <50000",">50000 to <100000",">100000 to<2000000",">200000 to<300000",">300000 to >500000"};
    ArrayList<EditText> etMemberNames = new ArrayList<>();
    //ArrayList<EditText> etIncome = new ArrayList<>();
    ArrayList<EditText> etAges = new ArrayList<>();
    ArrayList<EditText> etRelation = new ArrayList<>();
    ArrayList<EditText> etCard = new ArrayList<>();
   // ArrayList<EditText> etOccupatns = new ArrayList<>();
    ArrayList<Spinner> spOcuupation = new ArrayList<>();
    ArrayList<Spinner> spGenders = new ArrayList<>();
    ArrayList<Spinner> spEdnQualis = new ArrayList<>();
    ArrayList<Spinner> spdisabity = new ArrayList<>();
    ArrayList<Spinner> spcard = new ArrayList<>();
    ArrayList<TextInputLayout> tlPrimaryConts = new ArrayList<>();
    ArrayList<TextInputLayout> tlEmails = new ArrayList<>();
    ArrayList<AppCompatTextView> dateDobs = new ArrayList<>();
    ArrayList<AppCompatTextView> names = new ArrayList<>();
    ArrayAdapter<String> MnscardAdapter,disAdt,occupationadt,qualificationAdt,genderAdt;
    private LinearLayout ll1, container;
    ArrayList<FamilyMemberModel> familyModels = new ArrayList<>();
    private Database db;
    private DatePickerFragment4 dpf;
    private Preferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_details);
        prefs = new Preferences(this);
        setupActionBar();
        Assign();


    }

    private void Assign() {
        db = new Database(this);
        sp1=findViewById(R.id.sp_Mnrg);

       ;submit=findViewById(R.id.btn_submit);;
        sp3=findViewById(R.id.sp_gender);
        sp4=findViewById(R.id.sp_disablity);
        sp5=findViewById(R.id.sp_qualification);
        sp6=findViewById(R.id.sp_ocupation);
        name_et=findViewById(R.id.et_name);
        relation_et=findViewById(R.id.et_relation);
        add = findViewById(R.id.add);
        container = findViewById(R.id.container);
        submit.setOnClickListener(this);
        add.setOnClickListener(this);
        Date=findViewById(R.id.date4);
        Age_et=findViewById(R.id.et_age);
        carno_et=findViewById(R.id.et_card12);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dpf = new DatePickerFragment4(Date, Age_et);
                    dpf.show(getSupportFragmentManager(), "DatePicker");
                } catch (Exception e) {

                }
            }
        });
        MnscardAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Mnscard);
        MnscardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(MnscardAdapter);

        genderAdt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, gender);
        genderAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(genderAdt);
        disAdt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, disability);
        disAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(disAdt);
        qualificationAdt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, qualification);
        qualificationAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp5.setAdapter(qualificationAdt);
        occupationadt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ocupation);
        occupationadt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp6.setAdapter(occupationadt);


    }

    private void uploadStatusHistory(String xmlPath, ArrayList<FamilyMemberModel> familyModels) {
        ArrayList<CandStatusHistoryModel> temp = db.getCandStatusHistoryToUpload(prefs.getInt(Keys.PREFS_USER_ID));
        if (temp.size() > 0) {
            List<String> jsonArray = new ArrayList<>();
            for (int i = 0; i < temp.size(); i++) {
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("candidate_id", temp.get(i).getCandidateId());
                    jsonData.put("activity_status_id", temp.get(i).getStatusId());
                    jsonData.put("reason", temp.get(i).getReason());
                    jsonData.put("date", temp.get(i).getDateTime());
                    jsonData.put("remarks", temp.get(i).getRemarks());
                    jsonData.put("latitude", temp.get(i).getLatitude());
                    jsonData.put("longitude", temp.get(i).getLongitude());
                    jsonData.put("device_date", temp.get(i).getTimestamp());
                    jsonArray.add(jsonData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        prepareXml(xmlPath,familyModels);
    }
    public void prepareXml(String path, ArrayList<FamilyMemberModel> candArray) {
//        if (cl.isShowing()) cl.dismiss();
       
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Utils.getImagePath() + path);
            bw = new BufferedWriter(fw);

            bw.write(writeUsingXMLSerializer(candArray));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("xml", e.getMessage() + "");
        } finally {
            try {
                if (bw != null) bw.close();

                if (fw != null) fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
      
        uploadFiles(path, candArray);
    }

    private void uploadFiles(String path, ArrayList<FamilyMemberModel> candArray) {
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }
//        if (cl.isShowing()) cl.dismiss();


        ArrayList<FileModel> fileModels = new ArrayList<>();
        //to upload xml file
        FileModel fileModel = new FileModel();
        File file = new File(Utils.getImagePath() + path);

        RequestBody xmlFile = RequestBody.create(MediaType.parse("text/xml"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), xmlFile);
        fileModel.setFileName(file.getName());
        fileModel.setFilePart(body);
        fileModel.setFilePath(Keys.S3_PATH_MRE_XML_ENR);
        fileModel.setFileType(Keys.CONTENT_TYPE_XML);
        fileModels.add(fileModel);

        //img files
        ArrayList<File> imgFiles = new ArrayList<>();
        for (int i = 0; i < candArray.size(); i++) {
         
        }
        if (imgFiles.size() == 0) {
        
            getS3Signature(fileModels, path, candArray);
        } else {
            for (int i = 0; i < imgFiles.size(); i++) {
                Log.e("img size", imgFiles.size() + "");
                FileModel fileModel1 = new FileModel();
                RequestBody fileReqBody;
                switch (Utils.getMimeType(getApplicationContext(), Uri.fromFile(imgFiles.get(i)))) {
                    case Keys.MIME_TYPE_JPG:
                        fileReqBody = RequestBody.create(MediaType.parse(Keys.CONTENT_TYPE_JPG), imgFiles.get(i));
                        fileModel1.setFileType(Keys.CONTENT_TYPE_JPG);
                        fileModel1.setFilePath(Keys.S3_PATH_MRE_IMGS);
                        break;
                    case Keys.MIME_TYPE_PNG:
                        fileReqBody = RequestBody.create(MediaType.parse(Keys.CONTENT_TYPE_PNG), imgFiles.get(i));
                        fileModel1.setFileType(Keys.CONTENT_TYPE_PNG);
                        fileModel1.setFilePath(Keys.S3_PATH_MRE_IMGS);
                        break;
                    case Keys.MIME_TYPE_PDF:
                        fileReqBody = RequestBody.create(MediaType.parse(Keys.CONTENT_TYPE_PDF), imgFiles.get(i));
                        fileModel1.setFileType(Keys.CONTENT_TYPE_PDF);
                        fileModel1.setFilePath(Keys.S3_PATH_MRE_DOCS);
                        break;
                    case Keys.MIME_TYPE_DOC:
                        fileReqBody = RequestBody.create(MediaType.parse(Keys.CONTENT_TYPE_WORD), imgFiles.get(i));
                        fileModel1.setFileType(Keys.CONTENT_TYPE_WORD);
                        fileModel1.setFilePath(Keys.S3_PATH_MRE_DOCS);
                        break;
                    default:
                        fileReqBody = RequestBody.create(MediaType.parse(Keys.CONTENT_TYPE_JPG), imgFiles.get(i));
                        fileModel1.setFileType(Keys.CONTENT_TYPE_JPG);
                        fileModel1.setFilePath(Keys.S3_PATH_MRE_IMGS);
                        break;

                }
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", imgFiles.get(i).getName(), fileReqBody);
                fileModel1.setFileName(imgFiles.get(i).getName());
                fileModel1.setFilePart(part);
                fileModels.add(fileModel1);
//            parts.add(part);
                if (i == imgFiles.size() - 1) {
                
                    getS3Signature(fileModels, path, candArray);
                }
            }
        }
    }

    private void getS3Signature(ArrayList<FileModel> fileModels, String path, ArrayList<FamilyMemberModel> candArray) {
       
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }
//        if (cl.isShowing()) cl.dismiss();
       
        String s3path;
        switch (prefs.getInt(Keys.PREFS_SERVER_MODE)) {
            case Keys.CONST_LIVE_MODE:
                s3path = Keys.S3_LIVE;
                break;
            case Keys.CONST_DEMO_MODE:
                s3path = Keys.S3_QA;
                break;
            case Keys.CONST_UAT_MODE:
                s3path = Keys.S3_UAT;
                break;
            default:
                s3path = Keys.S3_LIVE;
                break;
        }
        Log.e("path",""+path);
        Log.e("model_size",""+candArray.size());
        final int[] count = {0};
        for (final int[] i = {0}; i[0] < fileModels.size(); i[0]++) {
            Log.e("loop", fileModels.size() + "--" + i[0] + "--" + fileModels.get(i[0]).getFileName());
            final FileModel fm = fileModels.get(i[0]);
            final Retrofit retrofit = RetrofitClient.getS3Client();
            final RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
            Call<ResponseBody> call = retrofitConfigInterface.getS3Signature(  Keys.CONST_S3_BUCKET + "qa/" + "xml/"+fm.getFileName(), fm.getFileType());
            Log.e("TAG", call.request().url().toString());
            Log.e("s3 path", fm.getFileType() + "--" + Keys.CONST_S3_BUCKET + s3path + fm.getFilePath() + fm.getFileName());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    Log.e("responsee1234.....",""+response.body());

                    if (response.isSuccessful()) {
                        try {

                            String remoteResponse = response.body().string().trim();
//                                Log.e(TAG, remoteResponse);
                            JSONObject response1 = new JSONObject(remoteResponse);
                            JSONObject response2 = new JSONObject((response1.getJSONObject("data").getJSONObject("fields")).toString());

                            RequestBody acl = RequestBody.create(MediaType.parse("text/plain"), response2.getString("acl"));
                            RequestBody contentType = RequestBody.create(MediaType.parse("text/plain"), response2.getString("Content-Type"));
                            RequestBody key = RequestBody.create(MediaType.parse("text/plain"), response2.getString("key"));
                            RequestBody xAmzAlgorithm = RequestBody.create(MediaType.parse("text/plain"), response2.getString("x-amz-algorithm"));
                            RequestBody xAmzCredential = RequestBody.create(MediaType.parse("text/plain"), response2.getString("x-amz-credential"));
                            RequestBody xAmzDate = RequestBody.create(MediaType.parse("text/plain"), response2.getString("x-amz-date"));
                            RequestBody policy = RequestBody.create(MediaType.parse("text/plain"), response2.getString("policy"));
                            RequestBody xAmzSignature = RequestBody.create(MediaType.parse("text/plain"), response2.getString("x-amz-signature"));

                            Retrofit retrofit2 = RetrofitClient.getS3UploadClient();
                            RetrofitConfigInterface retrofitConfigInterface2 = retrofit2.create(RetrofitConfigInterface.class);

                            Call<Void> call2 = retrofitConfigInterface2.uploadToS3(acl, contentType, key, xAmzAlgorithm, xAmzCredential, xAmzDate, policy, xAmzSignature, fm.getFilePart());
                            Log.e("TAG", call2.request().toString());
                            call2.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call2, Response<Void> response2) {
                                    Log.e("code", response2.raw() + "," + response2.message());
                                    if (response2.isSuccessful()) {
//
                                        if (response2.code() == 204) {
                                            count[0]++;
                                            Log.e("s33", i[0] + "--" + count[0]);
                                            if (i[0] == count[0]) {
                                                Log.e("log","up");
                                                Toast.makeText(FamilyDetailActivity.this,"upload",Toast.LENGTH_SHORT);

                                                uploadResponse(path, candArray);
                                            }
                                        } else {
                                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Upload", response2.code() + "");

                                            Utils.showToast(FamilyDetailActivity.this, "Upload Unsuccessful", false);
                                            return;
                                        }
                                    } else {
                                        try {
                                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Upload", "Code - " + response2.code() + "\n" + response2.errorBody().string());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Log.e("reponse",""+response.body());
                                        Utils.showToast(FamilyDetailActivity.this, "No response from server", false);
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call2, Throwable t2) {
                                    Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Upload", t2.getMessage());

                                    try {
                                        if (t2 != null)
                                            if (t2.getMessage().contains("Unable to resolve host") || t2 instanceof IOException)
                                                Toast.makeText(getApplicationContext(), "Poor internet connection", Toast.LENGTH_LONG).show();
                                            else
                                                Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                                    }
                                    return;
                                }
                            });

                        } catch (Exception e) {
                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Signature", e.getMessage());

                            Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        try {
                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Signature", "Code - " + response.code() + "\n" + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("responsee.....",""+response.body());
                        Utils.showToast(FamilyDetailActivity.this, "No response from server", false);
                        return;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Signature", t.getMessage());

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
                    return;
                }
            });

        }
    }

    private void uploadResponse(String path, ArrayList<FamilyMemberModel> candArray) {
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }


        int stage = prefs.getInt(Keys.PREFS_ACTION);

        Retrofit retrofit = Utils.getClient(getApplicationContext());
        RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
        //Call<SubmitCandResponse> call = retrofitConfigInterface.submitCandResponse(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY, prefs.getInt(Keys.PREFS_USER_ID), prefs.getInt(Keys.PREFS_ROLE_ID), prefs.getString(Keys.PREFS_LATITUDE), prefs.getString(Keys.PREFS_LONGITUDE), Utils.getCurrentDate(Keys.TIMESTAMP_FORMAT), stage, path, Utils.getVersionCode(this), Build.MODEL, Utils.getDeviceId(this), Build.VERSION.RELEASE);
        Call<SubmitFamily> call = retrofitConfigInterface.submitCandResponse(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY,1,  path);
        //   Toast.makeText(getApplicationContext(),call.request().toString(),Toast.LENGTH_LONG).show();
        Log.e("TAG", call.request().toString());

        call.enqueue(new Callback<SubmitFamily>() {
            @Override
            public void onResponse(Call<SubmitFamily> call, Response<SubmitFamily> response) {
                SubmitFamily response1 = response.body();
                Log.e("res",""+response.raw() );
               // Log.e("res",""+response1.isSuccess());
                if (response1 != null) {

                    Log.e("TAG", "SubmitCandResponse " + response1.getDescription());


                    if (response1.isApp_status()) {

                        if (response1.isSuccess()) {
                            //delete uploaded rows
                            login();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SubmitFamily> call, Throwable t) {
                Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "Submit Response", t.getMessage());

                try {
                    if (t != null)
                        if (t.getMessage().contains("Unable to resolve host") || t instanceof IOException)
                            Toast.makeText(getApplicationContext(), "Poor internet connection", Toast.LENGTH_LONG).show();
                        else {

                            Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                        }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
              //  xmlPath = Utils.getFileName(String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), "Bulk");
               // createArray();
               // uploadStatusHistory(xmlPath, familyModels);
               /* login();*/
                login();

                break;
            case R.id.add:
                addfamily();
                break;
            default:
                break;
        }
    }

    private void createArray() {
        if (familyModels.size() == 0) {
           /* name.setText("Added: " + etMemberName.getText().toString().trim());
            name.setVisibility(View.VISIBLE);*/
            /* ll1.setVisibility(View.GONE);*/

            FamilyMemberModel familyMemberModel = new FamilyMemberModel();
            familyMemberModel.setMemberName(name_et.getText().toString().trim());
            familyMemberModel.setAge(Age_et.getText().toString().trim());
            familyMemberModel.setRelation(relation_et.getText().toString().trim());

            familyMemberModel.setOccupation(sp6.getSelectedItemPosition() < 1 ? "" : sp6.getSelectedItem().toString());
            Log.e("occupation",""+ sp6.getSelectedItem().toString());
            //familyMemberModel.setSalutation(spSalutation.getSelectedItemPosition() < 1 ? "" : spSalutation.getSelectedItem().toString());
            familyMemberModel.setGender(sp3.getSelectedItemPosition() < 1 ? "" : sp3.getSelectedItem().toString());
            Log.e("gender",""+ relation_et.getText().toString().trim());
            familyMemberModel.setEdnQuali(sp5.getSelectedItemPosition() < 1 ? "" : sp5.getSelectedItem().toString());
            familyMemberModel.setDis(sp4.getSelectedItemPosition() < 1 ? "" : sp4.getSelectedItem().toString());

            familyMemberModel.setCard(sp1.getSelectedItemPosition() < 1 ? "" : sp1.getSelectedItem().toString());
            familyMemberModel.setCardno(carno_et.getText().toString());
            Log.e("gender",""+ sp4.getSelectedItem().toString());
           // familyMemberModel.setDob(Age_et.getText().toString().contains("Choose") ? "" : Age_et.getText().toString());
            familyModels.add(familyMemberModel);
        } else if (etMemberNames.size()>0){
            Log.e("ee", etMemberNames.size() + etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());

            if (etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Member Name cannot be blank", Toast.LENGTH_SHORT).show();
                return;
            }


            names.get(names.size() - 1).setText("Added: " + etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());
           /* ll1s.get(ll1s.size() - 1).setVisibility(View.GONE);
            names.get(names.size() - 1).setVisibility(View.VISIBLE);*/
            FamilyMemberModel familyMemberModel = new FamilyMemberModel();
            familyMemberModel.setMemberName(etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());
            familyMemberModel.setAge(etAges.get(etAges.size() - 1).getText().toString().trim());
            // familyMemberModel.setAnualIncome(etIncome.get(etIncome.size() - 1).getText().toString().trim());
            // familyMemberModel.setPrimaryCont(etPrimaryConts.get(etPrimaryConts.size() - 1).getText().toString().trim());
            // familyMemberModel.setEmail(etEmails.get(etEmails.size() - 1).getText().toString().trim());
            familyMemberModel.setOccupation(spOcuupation.get(spOcuupation.size() - 1).getSelectedItemPosition() < 1 ? "" : spOcuupation.get(spOcuupation.size() - 1).getSelectedItem().toString());
            // familyMemberModel.setSalutation(spSalutations.get(spSalutations.size() - 1).getSelectedItemPosition() < 1 ? "" : spSalutations.get(spSalutations.size() - 1).getSelectedItem().toString());
            familyMemberModel.setGender(spGenders.get(spGenders.size() - 1).getSelectedItemPosition() < 1 ? "" : spGenders.get(spGenders.size() - 1).getSelectedItem().toString());
            familyMemberModel.setEdnQuali(spEdnQualis.get(spEdnQualis.size() - 1).getSelectedItemPosition() < 1 ? "" : spEdnQualis.get(spEdnQualis.size() - 1).getSelectedItem().toString());
            familyMemberModel.setRelation(spcard.get(spcard.size() - 1).getSelectedItemPosition() < 1 ? "" : spcard.get(spcard.size() - 1).getSelectedItem().toString());
           // familyMemberModel.setDob(dateDobs.get(dateDobs.size() - 1).getText().toString().contains("Choose") ? "" : dateDobs.get(dateDobs.size() - 1).getText().toString());
            familyModels.add(familyMemberModel);
        }
    }

    private void addfamily() {
        String[] gender_ = {"Male", "Female"};
        String[] qualification_ = {"SSC", "Inter","Degree","Technica"};
        String[] disability_ = {"yes", "no"};
        String[] Mnscard_ = {"yes", "no"};
        String[] ocupation_ = {"Agriculture", "Agriculture Labour","Wage Labour"};
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if ((familyModels.size() == 0 && name_et.getText().toString().trim().isEmpty())) {
            Toast.makeText(getApplicationContext(), "Member Name cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((familyModels.size() == 0 && Age_et.getText().toString().trim().isEmpty() )) {
            Toast.makeText(getApplicationContext(), "Invalid Age", Toast.LENGTH_SHORT).show();
            return;
        }
       /* if ((familyModels.size() == 0 && !etEmail.getText().toString().trim().isEmpty() && !Utils.checkPattern(etEmail.getText().toString().trim()))) {
            Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
*/
        if (familyModels.size() == 0) {
           /* name.setText("Added: " + etMemberName.getText().toString().trim());
            name.setVisibility(View.VISIBLE);*/
           /* ll1.setVisibility(View.GONE);*/

            FamilyMemberModel familyMemberModel = new FamilyMemberModel();
            familyMemberModel.setMemberName(name_et.getText().toString().trim());
            familyMemberModel.setAge(Age_et.getText().toString().trim());
            familyMemberModel.setRelation(relation_et.getText().toString().trim());

            familyMemberModel.setOccupation(sp6.getSelectedItemPosition() < 1 ? "" : sp6.getSelectedItem().toString());
            //familyMemberModel.setSalutation(spSalutation.getSelectedItemPosition() < 1 ? "" : spSalutation.getSelectedItem().toString());
            familyMemberModel.setGender(sp3.getSelectedItemPosition() < 1 ? "" : sp3.getSelectedItem().toString());
            familyMemberModel.setEdnQuali(sp5.getSelectedItemPosition() < 1 ? "" : sp5.getSelectedItem().toString());
            familyMemberModel.setRelation(sp1.getSelectedItemPosition() < 1 ? "" : sp1.getSelectedItem().toString());
           // familyMemberModel.setDob(Age_et.getText().toString().contains("Choose") ? "" : Age_et.getText().toString());
            familyModels.add(familyMemberModel);
        } else if (etMemberNames.size()>0){
            Log.e("ee", etMemberNames.size() + etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());

            if (etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Member Name cannot be blank", Toast.LENGTH_SHORT).show();
                return;
            }


            names.get(names.size() - 1).setText("Added: " + etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());
           /* ll1s.get(ll1s.size() - 1).setVisibility(View.GONE);
            names.get(names.size() - 1).setVisibility(View.VISIBLE);*/
            FamilyMemberModel familyMemberModel = new FamilyMemberModel();
            familyMemberModel.setMemberName(etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());
            familyMemberModel.setAge(etAges.get(etAges.size() - 1).getText().toString().trim());
           // familyMemberModel.setAnualIncome(etIncome.get(etIncome.size() - 1).getText().toString().trim());
           // familyMemberModel.setPrimaryCont(etPrimaryConts.get(etPrimaryConts.size() - 1).getText().toString().trim());
           // familyMemberModel.setEmail(etEmails.get(etEmails.size() - 1).getText().toString().trim());
            familyMemberModel.setOccupation(spOcuupation.get(spOcuupation.size() - 1).getSelectedItemPosition() < 1 ? "" : spOcuupation.get(spOcuupation.size() - 1).getSelectedItem().toString());
           // familyMemberModel.setSalutation(spSalutations.get(spSalutations.size() - 1).getSelectedItemPosition() < 1 ? "" : spSalutations.get(spSalutations.size() - 1).getSelectedItem().toString());
            familyMemberModel.setGender(spGenders.get(spGenders.size() - 1).getSelectedItemPosition() < 1 ? "" : spGenders.get(spGenders.size() - 1).getSelectedItem().toString());
            familyMemberModel.setEdnQuali(spEdnQualis.get(spEdnQualis.size() - 1).getSelectedItemPosition() < 1 ? "" : spEdnQualis.get(spEdnQualis.size() - 1).getSelectedItem().toString());
            familyMemberModel.setRelation(spcard.get(spcard.size() - 1).getSelectedItemPosition() < 1 ? "" : spcard.get(spcard.size() - 1).getSelectedItem().toString());
           // familyMemberModel.setDob(dateDobs.get(dateDobs.size() - 1).getText().toString().contains("Choose") ? "" : dateDobs.get(dateDobs.size() - 1).getText().toString());
            familyModels.add(familyMemberModel);
        }
        final View addView = layoutInflater.inflate(R.layout.item_add_family, null);
        //container.addView(addView);
        Spinner sp_1,sp_2,sp_3,sp_4,sp_5;
        EditText date_,Age_;
        sp_1=addView.findViewById(R.id.sp_Mnrg);
        sp_2=addView.findViewById(R.id.sp_gender);
        sp_3=addView.findViewById(R.id.sp_disablity);
        sp_4=addView.findViewById(R.id.sp_qualification);
        sp_5=addView.findViewById(R.id.sp_ocupation);
        date_=findViewById(R.id.date4);
        Age_=findViewById(R.id.et_age);
        Log.e("string",""+Mnscard_);
        MnscardAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Mnscard_);
        MnscardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_1.setAdapter(MnscardAdapter);

       genderAdt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, gender_);
        genderAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_2.setAdapter(genderAdt);
        disAdt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, disability_);
        disAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_3.setAdapter(disAdt);
       qualificationAdt= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, qualification_);
        qualificationAdt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_4.setAdapter(qualificationAdt);
        occupationadt= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ocupation_);
        occupationadt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_5.setAdapter(occupationadt);
        date_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dpf = new DatePickerFragment4(date_, Age_);
                    dpf.show(getSupportFragmentManager(), "DatePicker");
                } catch (Exception e) {

                }
            }
        });
        container.addView(addView);
    }
    public String writeUsingXMLSerializer(ArrayList<FamilyMemberModel> candArray) throws Exception {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.startTag("", "farmer");
        //for loop start
        for (int i = 0; i < candArray.size(); i++) {
            FamilyMemberModel fm = candArray.get(i);


                //family details
               // ArrayList<FamilyMemberModel> fm = db.getFamilyMembers(prefs.getInt(Keys.PREFS_USER_ID), 1);
               // Log.e("fmily_db",""+fm.size());
            Log.e("cand_db",""+candArray.size());
                for (int x = 0; x < candArray.size(); x++) {
                    xmlSerializer.startTag("", "family_details");

                    xmlSerializer.attribute("", "name", String.valueOf(candArray.get(x).getMemberName()));
                    xmlSerializer.attribute("", "household_id", String.valueOf(2));

                    xmlSerializer.attribute("", "age", String.valueOf(candArray.get(x).getAge()));


                    xmlSerializer.attribute("", "gender", String.valueOf(candArray.get(x).getGender()));
                    xmlSerializer.attribute("", "relationship", String.valueOf(candArray.get(x).getRelation()));
                    xmlSerializer.attribute("", "education", String.valueOf(candArray.get(x).getEdnQuali()));
                    xmlSerializer.attribute("", "occupation", String.valueOf(candArray.get(x).getOccupation()));
                    xmlSerializer.attribute("", "disability", String.valueOf(0));
                    xmlSerializer.attribute("", "disability_details", String.valueOf(candArray.get(x).getDis()));
                    xmlSerializer.attribute("", "mnregs", String.valueOf(candArray.get(x).getCard()));
                    xmlSerializer.attribute("", "mnregs_card_no", String.valueOf(0));
                    xmlSerializer.attribute("", "mnregs_img", String.valueOf(candArray.get(x).getCardno()));
                    xmlSerializer.attribute("", "created_by", String.valueOf(1));
                    xmlSerializer.attribute("", "is_active", String.valueOf(1));


                    xmlSerializer.endTag("", "family_details");
                }
            }


            //for loop end


        xmlSerializer.endDocument();

        return writer.toString().replace("&amp;", "and");
    }

    private void login() {
        startActivity(new Intent(FamilyDetailActivity.this, LandDetailActivity.class));

    }
}

