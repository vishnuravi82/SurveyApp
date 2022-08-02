package com.labournet.surveyapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.labournet.surveyapp.R;
import com.labournet.surveyapp.fragment.DatePickerFragment4;
import com.labournet.surveyapp.model.CandStatusHistoryModel;
import com.labournet.surveyapp.model.FamilyMemberModel;
import com.labournet.surveyapp.model.FileModel;
import com.labournet.surveyapp.model.SubmitFamily;
import com.labournet.surveyapp.model.WaterSource;
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

public class SourceDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner sp;
    private AppCompatButton submit;
    private Database db;
    private DatePickerFragment4 dpf;
    private Preferences prefs;
    String[] gender = {"Male", "Female"};
    String[] sourc = {"Bore wells ", "Dug Wells "};
    String[] disability = {"yes", "no"};
    String[] residence = {"Residence in village", "no_residence"};
    EditText IrrigationText,SeasonText;
    ArrayList<EditText> etIrrigationNames = new ArrayList<>();
    //ArrayList<EditText> etIncome = new ArrayList<>();
    ArrayList<EditText> etSeason = new ArrayList<>();
    ArrayList<EditText> etnoofdaays = new ArrayList<>();
    ArrayList<Spinner> sourse_sp = new ArrayList<>();
    ArrayList<WaterSource> watersourceModels = new ArrayList<>();
    String xmlPath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whater_source_activity);
        setupActionBar();
        Assign();

    }

    private void Assign() {
        //sp1=findViewById(R.id.sp_cast);
        ; db = new Database(this);
        prefs = new Preferences(this);
        sp=findViewById(R.id.sp_source);
        IrrigationText=findViewById(R.id.et_irigation);
        SeasonText=findViewById(R.id.et_season);
        submit=findViewById(R.id.btn_submit);;


        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sourc);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
        submit.setOnClickListener(this);

    }
    private void uploadStatusHistory(String xmlPath, ArrayList<WaterSource> waterModels) {
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
        prepareXml(xmlPath,waterModels);
    }

    private void prepareXml(String xmlPath, ArrayList<WaterSource> waterModels) {
//        if (cl.isShowing()) cl.dismiss();

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Utils.getImagePath() + xmlPath);
            bw = new BufferedWriter(fw);

            bw.write(writeUsingXMLSerializer(waterModels));

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

        uploadFiles(xmlPath, waterModels);
    }

    private void uploadFiles(String xmlPath, ArrayList<WaterSource> waterModels) {
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }
//        if (cl.isShowing()) cl.dismiss();


        ArrayList<FileModel> fileModels = new ArrayList<>();
        //to upload xml file
        FileModel fileModel = new FileModel();
        File file = new File(Utils.getImagePath() + xmlPath);

        RequestBody xmlFile = RequestBody.create(MediaType.parse("text/xml"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), xmlFile);
        fileModel.setFileName(file.getName());
        fileModel.setFilePart(body);
        fileModel.setFilePath(Keys.S3_PATH_MRE_XML_ENR);
        fileModel.setFileType(Keys.CONTENT_TYPE_XML);
        fileModels.add(fileModel);

        //img files
        ArrayList<File> imgFiles = new ArrayList<>();
        for (int i = 0; i < waterModels.size(); i++) {

        }
        if (imgFiles.size() == 0) {

            getS3Signature(fileModels, xmlPath, waterModels);
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

                    getS3Signature(fileModels, xmlPath, waterModels);
                }
            }
        }
    }

    private void getS3Signature(ArrayList<FileModel> fileModels, String xmlPath, ArrayList<WaterSource> waterModels) {

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
        Log.e("path",""+xmlPath);
        Log.e("model_size",""+waterModels.size());
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
                                                Toast.makeText(SourceDetailActivity.this,"upload",Toast.LENGTH_SHORT);

                                                uploadResponse(xmlPath, waterModels);
                                            }
                                        } else {
                                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Upload", response2.code() + "");

                                            Utils.showToast(SourceDetailActivity.this, "Upload Unsuccessful", false);
                                            return;
                                        }
                                    } else {
                                        try {
                                            Utils.captureBugReport(getApplicationContext(), Utils.getFileName("BugReport", String.valueOf(prefs.getInt(Keys.PREFS_ACTION)), String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), Keys.MIME_TYPE_TXT), "S3 Upload", "Code - " + response2.code() + "\n" + response2.errorBody().string());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Log.e("reponse",""+response.body());
                                        Utils.showToast(SourceDetailActivity.this, "No response from server", false);
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
                        Utils.showToast(SourceDetailActivity.this, "No response from server", false);
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

    private void uploadResponse(String xmlPath, ArrayList<WaterSource> waterModels) {
        if (!InternetStatus.getInstance().isConnectedToInternet(this)) {
            Toast.makeText(this, "Poor internet connection", Toast.LENGTH_LONG).show();
            return;
        }




        Retrofit retrofit = Utils.getClient(getApplicationContext());
        RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
        //Call<SubmitCandResponse> call = retrofitConfigInterface.submitCandResponse(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY, prefs.getInt(Keys.PREFS_USER_ID), prefs.getInt(Keys.PREFS_ROLE_ID), prefs.getString(Keys.PREFS_LATITUDE), prefs.getString(Keys.PREFS_LONGITUDE), Utils.getCurrentDate(Keys.TIMESTAMP_FORMAT), stage, path, Utils.getVersionCode(this), Build.MODEL, Utils.getDeviceId(this), Build.VERSION.RELEASE);
        Call<SubmitFamily> call = retrofitConfigInterface.submitsourceResponse(Keys.CONST_CLIENT_ID, Keys.CONST_CLIENT_KEY,1,  xmlPath);
        //   Toast.makeText(getApplicationContext(),call.request().toString(),Toast.LENGTH_LONG).show();
        Log.e("TAG......", call.request().toString());

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
                Log.e("hai....",""+t.getMessage());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                xmlPath = Utils.getFileName(String.valueOf(prefs.getInt(Keys.PREFS_USER_ID)), "Bulk");
                createArray();
                uploadStatusHistory(xmlPath, watersourceModels);
                //login();
                break;

            default:
                break;
        }
    }
    private void createArray() {
        if (watersourceModels.size() == 0) {
           /* name.setText("Added: " + etMemberName.getText().toString().trim());
            name.setVisibility(View.VISIBLE);*/
            /* ll1.setVisibility(View.GONE);*/

            WaterSource waterModel = new WaterSource();
            waterModel.setSourceId(sp.getSelectedItemPosition() < 1 ? "" : sp.getSelectedItem().toString());
            waterModel.setHouseId("1");
            waterModel.setSeason(SeasonText.getText().toString().trim());
            waterModel.setWater_availability(IrrigationText.getText().toString().trim());
            waterModel.setWater_availability(IrrigationText.getText().toString().trim());

            watersourceModels.add(waterModel);
        } else if (etIrrigationNames.size()>0){
            Log.e("ee", etIrrigationNames.size() + etIrrigationNames.get(etIrrigationNames.size() - 1).getText().toString().trim());

            if (etIrrigationNames.get(etIrrigationNames.size() - 1).getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Member Name cannot be blank", Toast.LENGTH_SHORT).show();
                return;
            }


            //names.get(names.size() - 1).setText("Added: " + etMemberNames.get(etMemberNames.size() - 1).getText().toString().trim());
           /* ll1s.get(ll1s.size() - 1).setVisibility(View.GONE);
            names.get(names.size() - 1).setVisibility(View.VISIBLE);*/
            WaterSource waterModel = new WaterSource();
            waterModel.setSourceId(sourse_sp.get(sourse_sp.size() - 1).getSelectedItemPosition() < 1 ? "" : sourse_sp.get(sourse_sp.size() - 1).getSelectedItem().toString());
            waterModel.setIrrigation(etIrrigationNames.get(etIrrigationNames.size() - 1).getText().toString().trim());
            waterModel.setSeason(etSeason.get(etSeason.size() - 1).getText().toString().trim());
            waterModel.setWater_availability(etnoofdaays.get(etnoofdaays.size() - 1).getText().toString().trim());
            // familyMemberModel.setAnualIncome(etIncome.get(etIncome.size() - 1).getText().toString().trim());
            // familyMemberModel.setPrimaryCont(etPrimaryConts.get(etPrimaryConts.size() - 1).getText().toString().trim());

            watersourceModels.add(waterModel);
        }
    }

    public String writeUsingXMLSerializer(ArrayList<WaterSource> candArray) throws Exception {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.startTag("", "resource_detail");
        Log.e("cand_db",""+candArray.size());
        //for loop start
        for (int i = 0; i < candArray.size(); i++) {
            WaterSource fm = candArray.get(i);


            //family details
            // ArrayList<FamilyMemberModel> fm = db.getFamilyMembers(prefs.getInt(Keys.PREFS_USER_ID), 1);
            // Log.e("fmily_db",""+fm.size());
            Log.e("cand_db",""+candArray.size());
            for (int x = 0; x < candArray.size(); x++) {
                xmlSerializer.startTag("", "water_resource");

                xmlSerializer.attribute("", "household_id", String.valueOf(2));
                xmlSerializer.attribute("", "sources_id", String.valueOf(25));

                xmlSerializer.attribute("", "area_under_irrigation", String.valueOf(candArray.get(x).getAge()));
                xmlSerializer.attribute("", "season", String.valueOf(candArray.get(x).getSeason()));
                xmlSerializer.attribute("", "no_of_days", String.valueOf(candArray.get(x).getWater_availability()));

                xmlSerializer.attribute("", "created_by", String.valueOf(1));
                xmlSerializer.attribute("", "is_active", String.valueOf(1));


                xmlSerializer.endTag("", "water_resource");
            }
        }


        //for loop end


        xmlSerializer.endDocument();

        return writer.toString().replace("&amp;", "and");
    }

    private void login() {
        startActivity(new Intent(SourceDetailActivity.this, CropDetailActivity.class));

    }
}


