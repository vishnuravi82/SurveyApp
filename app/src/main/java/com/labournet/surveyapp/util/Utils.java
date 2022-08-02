package com.labournet.surveyapp.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.labournet.surveyapp.R;
import com.squareup.picasso.Picasso;
import com.tooltip.Tooltip;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Athira on 11/6/19.
 */
public class Utils {
    public static final int CAMERA_PIC_REQUEST = 0;
    public static final int GALLERY_PIC_REQUEST = 1;
    public static final int MULTI_CAM_PIC_REQUEST = 10;
    public static final int MULTI_GALLERY_PIC_REQUEST = 11;
//    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//    "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
//    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
    private static Tooltip mTooltip;
    private Context context;
    public static  final  String[] mimeTypes = {"image/jpeg", "image/png", "application/pdf", "application/msword"};

    public Utils(Context context) {
        this.context = context;
    }

    public static void setUrlThumbnail(Context context,String filename,AppCompatImageView imageView){
        Preferences pref=new Preferences(context);
        String[] parts = filename.split(".");
        switch (parts[parts.length - 1]) {
            case Keys.MIME_TYPE_JPG:
            case Keys.MIME_TYPE_PNG:
//                Picasso.with(context).load((pref.getInt(Keys.PREFS_SERVER_MODE) == Keys.CONST_LIVE_MODE ? Keys.CONST_PATH_LIVE_URL_IMAGE : Keys.CONST_PATH_QA_URL_IMAGE) + filename)
//                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                String s3path;
                switch (pref.getInt(Keys.PREFS_SERVER_MODE)){
                    case Keys.CONST_LIVE_MODE:s3path=Keys.S3_LIVE;break;
                    case Keys.CONST_DEMO_MODE: s3path=Keys.S3_QA;break;
                    case Keys.CONST_UAT_MODE: s3path=Keys.S3_UAT;break;
                    default:s3path=Keys.S3_LIVE;break;
                }
                Picasso.with(context).load(
                        RetrofitClient.BASE_S3_UPLOAD_URL+Keys.CONST_S3_BUCKET+
                               s3path+
                                Keys.S3_PATH_MRE_IMGS+filename)

                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
            case Keys.MIME_TYPE_PDF:
                Picasso.with(context).load(R.drawable.pdf_thumbnail).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
            case Keys.MIME_TYPE_DOC:
            default:
                Picasso.with(context).load(R.drawable.file_thumbnail).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;

        }
    }

    public static void setFileThumbnail(Context context,File file, Uri uri, AppCompatImageView imageView) {

        switch (Utils.getMimeType(context, uri)) {
            case Keys.MIME_TYPE_JPG:
            case Keys.MIME_TYPE_PNG:
                Picasso.with(context).load(file).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
            case Keys.MIME_TYPE_PDF:
                Picasso.with(context).load(R.drawable.pdf_thumbnail).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
            case Keys.MIME_TYPE_DOC:
                Picasso.with(context).load(R.drawable.file_thumbnail).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
            default:
                Picasso.with(context).load(R.drawable.file_thumbnail).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
                break;
        }
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }
//        Log.e("ext", extension);
        return extension;
    }

    public static File copyToTempFile(Context context, Uri uri, File tempFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            Log.e("pr", e.getMessage());
        }

        return tempFile;
    }
    public static void askPermission(Activity activity, String string) {
        switch (string) {
            case "CALL_PHONE":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0);
                break;
            case "CONTACTS":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 2);
                break;
            case "CAMERA":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                break;
            case "STORAGE":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                break;
            case "LOCATION":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
                break;
            case "READ_PHONE":
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 6);
                break;
//            case "SMS":
//                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS}, 7);
//                break;
        }

    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1) break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            Log.e("inside Utils", "copyStream method");
        }
    }

    public static boolean checkPattern(String email) {
        return  EMAIL_ADDRESS_PATTERN.matcher(email).matches() &&
                !email.contains("--") && !email.contains("__") && !email.contains("-@") && !email.contains("_@") &&
                !email.contains("-_") && !email.contains("-.") && !email.contains("_-") && !email.contains("_.") &&
                !email.contains("._") && !email.contains(".-");
    }

    public static String getSDCardPath() {
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "//";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "//";

        try {
            File f = new File(path);
            if (!f.exists()) f.mkdirs();
            f = new File(path + ".nomedia");
            if (!f.exists()) f.createNewFile();
        } catch (Exception e) {
            Log.e("getImagePath: ", e.getMessage());
        }
        return path;
    }

    public static String getImagePath() {
       // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NEO/MCLG/";
       //vishnu
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/NEO/MCLG/";
        try {
            File f = new File(path);
            if (!f.exists()) f.mkdirs();

            f = new File(path + ".nomedia");
            if (!f.exists()) f.createNewFile();
        } catch (Exception e) {
            Log.e("getImagePath: ", e.getMessage());
        }
        return path;
    }
    public static String getFileName(String userId, String candId ) {
        String timeStamp = new SimpleDateFormat("ddMMyy_HHmmss", Locale.getDefault()).format(new Date());
        return  userId + "_" + candId + "_" + timeStamp + ".xml";
    }
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

public static void captureBugReport(Context context, String filename, String event,String data){

    String timeStamp = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.getDefault()).format(new Date());
    Preferences prefs=new Preferences(context);
        File f=new File(getImagePath(),filename);
    try {
        FileOutputStream stream = new FileOutputStream(f);
        stream.write(("User Name: "+prefs.getString(Keys.PREFS_USER_NAME)+"\n").getBytes());
        stream.write(("User ID: "+prefs.getInt(Keys.PREFS_USER_ID)+"\n").getBytes());
        stream.write(("User Role: "+prefs.getString(Keys.PREFS_ROLE_NAME)+"\n").getBytes());
        stream.write(("App Version: "+getVersionCode(context)+"\n").getBytes());
        stream.write(("Model: "+ Build.MODEL+"\n").getBytes());
        stream.write(("Android Version: "+ Build.VERSION.RELEASE+"\n").getBytes());
        stream.write(("Timestamp: "+timeStamp+"\n").getBytes());
        stream.write(("Event: "+event+"\n").getBytes());
        stream.write(("Bug Report: "+data).getBytes());
        stream.close();
    } catch (Exception e){
    }
}

    public static String getImageName(String userId, String candId, String quesId) {
        String timeStamp = new SimpleDateFormat("ddMMyy_HHmmss", Locale.getDefault()).format(new Date());
        return "IMG_" + userId + "_" + candId + "_" + quesId + "_" + timeStamp + ".jpg";
    }
    public static String getFileName(String userId, String candId, String quesId,String ext) {
        String timeStamp = new SimpleDateFormat("ddMMyy_HHmmssSSS", Locale.getDefault()).format(new Date());
        return "File_" + userId + "_" + candId + "_" + quesId + "_" + timeStamp + "."+ext;
    }

    public static String calculateAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        

        return Integer.toString(age);
    }

    public static String getCurrentDate(String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrentMonth() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(new Date());
    }

    public static String getTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a", Locale.getDefault());
//        you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getDefault());

        return date.format(currentLocalTime).replaceAll(" ", "_");
    }
    public static int getActivityStatusId(String status) {
        switch (status) {
            case "Mobilized":
                return 1;
            case "Interest validated":
                return 2;
            case "Course details briefed":
                return 3;
            case "Visited center":
                return 4;
            case "Invited to demo class":
                return 5;
            case "Attended demo class":
                return 6;
            case "Followed up for registration":
                return 7;
            case "Backed out before registration":
                return 8;
            case "Not interested now":
                return 9;
            case "Registered":
                return 10;
            case "Backed out before enrollment":
                return 11;
            case "Not Interested now":
                return 12;
            case "Followed up for enrollment":
                return 13;
            default:
                return 0;
        }
    }
    public static String getActivityStatusName(int id) {
        switch (id) {
            case 0:
                return "";
            case 1:
            return "Mobilized";
            case 2:
            return "Interest validated";
            case 3:
            return "Course details briefed";
            case 4:
            return "Visited center";
            case 5:
            return "Invited to demo class";
            case 6:
            return "Attended demo class";
            case 7:
            return "Followed up for registration";
            case 8:
            return "Backed out before registration";
            case 9:
            return "Not interested now";
            case 10:
            return "Registered";
            case 11:
            return "Backed out before enrollment";
            case 12:
            return "Not Interested now";
            case 13:
            return "Followed up for enrollment";
            default:
                return "";
        }
    }
    public static int getStateId(String state) {
        switch (state) {
            case "Andhra Pradesh":
                return 1;
            case "Arunachal Pradesh":
                return 2;
            case "Assam":
                return 3;
            case "Bihar":
                return 4;
            case "Chattisgarh":
                return 5;
            case "Goa":
                return 6;
            case "Gujarat":
                return 7;
            case "Haryana":
                return 8;
            case "Himachal Pradesh":
                return 9;
            case "Jammu & Kashmir":
                return 10;
            case "Jharkhand":
                return 11;
            case "Karnataka":
                return 12;
            case "Kerala":
                return 13;
            case "Madhya Pradesh":
                return 14;
            case "Maharashtra":
                return 15;
            case "Manipur":
                return 16;
            case "Meghalaya":
                return 17;
            case "Mizoram":
                return 18;
            case "Nagaland":
                return 19;
            case "Orissa":
                return 20;
            case "Punjab":
                return 21;
            case "Rajasthan":
                return 22;
            case "Sikkim":
                return 23;
            case "Tamil Nadu":
                return 24;
            case "Telangana":
                return 25;
            case "Tripura":
                return 26;
            case "Uttar Pradesh":
                return 27;
            case "Uttarakhand":
                return 28;
            case "West Bengal":
                return 29;
            case "Delhi":
                return 30;
            case "Odisha":
                return 31;
            case "Andaman & Nicobar":
                return 32;
            case "Pondicherry":
                return 33;
            case "Daman & Diu":
                return 34;
            case "Lakshadweep":
                return 35;
            case "Chandigarh":
                return 36;
            case "Dadra & Nagar Haveli":
                return 37;
            default:
                return 0;
        }
    }
    public static String getStateName(int id) {
        switch (id) {
            case 1:
                return "Andhra Pradesh";
            case 2:
                return "Arunachal Pradesh";
            case 3:
                return "Assam";
            case 4:
                return "Bihar";
            case 5:
                return "Chattisgarh";
            case 6:
                return "Goa";
            case 7:
                return "Gujarat";
            case 8:
                return "Haryana";
            case 9:
                return "Himachal Pradesh";
            case 10:
                return "Jammu & Kashmir";
            case 11:
                return "Jharkhand";
            case 12:
                return "Karnataka";
            case 13:
                return "Kerala";
            case 14:
                return "Madhya Pradesh";
            case 15:
                return "Maharashtra";
            case 16:
                return "Manipur";
            case 17:
                return "Meghalaya";
            case 18:
                return "Mizoram";
            case 19:
                return "Nagaland";
            case 20:
                return "Orissa";
            case 21:
                return "Punjab";
            case 22:
                return "Rajasthan";
            case 23:
                return "Sikkim";
            case 24:
                return "Tamil Nadu";
            case 25:
                return "Telangana";
            case 26:
                return "Tripura";
            case 27:
                return "Uttar Pradesh";
            case 28:
                return "Uttarakhand";
            case 29:
                return "West Bengal";
            case 30:
                return "Delhi";
            case 31:
                return "Odisha";
            case 32:
                return "Andaman & Nicobar";
            case 33:
                return "Pondicherry";
            case 34:
                return "Daman & Diu";
            case 35:
                return "Lakshadweep";
            case 36:
                return "Chandigarh";
            case 37:
                return "Dadra & Nagar Haveli";
            default:
                return "";
        }
    }
    public static int getCountryId(String country) {
        switch (country) {
            case "India":
                return 1;
            default:
                return 0;
        }
    }
    public static String getCountryName(int id) {
        switch (id) {
            case 0:
                return "";
            case 1:
                return "India";
            default:
                return "India";
        }
    }
    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }

    public static String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date d = new Date();
        return sdf.format(d);
    }

    public static String getCurrentTimeIn24() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return formatter.format(new Date());
        } catch (Exception e) {
            Log.e("Utils.Java", e.getMessage());
        }
        return "";
    }

    public static String getVersionName(Context ctx) {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            Log.e("Error Version Name", e.getMessage());
            return "";
        }
    }

    public static String getVersionCode(Context ctx)  {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static String getDeviceId(Context ctx) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            return String.valueOf(telephonyManager.getDeviceId());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

    public static void showTooltip(String msg, View v) {
        mTooltip = new Tooltip.Builder(v).setBackgroundColor(Color.RED).setTextColor(Color.WHITE).setText(msg).show();
    }

    public static void dismissTooltip() {
        try {
            if (mTooltip.isShowing()) mTooltip.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Retrofit getClient(Context context){
        Preferences prefs=new Preferences(context);
        switch (prefs.getInt(Keys.PREFS_SERVER_MODE)){
            //case Keys.CONST_LIVE_MODE:return RetrofitClient.getLiveClient();
            case Keys.CONST_DEMO_MODE: return  RetrofitClient.getDemoClient();
            //case Keys.CONST_UAT_MODE: return  RetrofitClient.getUatClient();
            default:return RetrofitClient.getDemoClient();
        }
    }

    public static void uploadToServer(final Activity context, File file) {
//        Preferences prefs=new Preferences(context);
//        Retrofit retrofit = prefs.getInt(Keys.PREFS_SERVER_MODE)==Keys.CONST_LIVE_MODE?RetrofitClient.getLiveClient():RetrofitClient.getDemoClient();
      Retrofit retrofit=getClient(context);
        RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        RequestBody client_id = RequestBody.create(MediaType.parse("text/plain"), Keys.CONST_CLIENT_ID);
        RequestBody client_key = RequestBody.create(MediaType.parse("text/plain"), Keys.CONST_CLIENT_KEY);

        Call call = retrofitConfigInterface.uploadImage(client_id, client_key, part);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void uploadFiles(final Activity context, ArrayList<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>();
//        Preferences prefs=new Preferences(context);
//        Retrofit retrofit = prefs.getInt(Keys.PREFS_SERVER_MODE)==Keys.CONST_LIVE_MODE?RetrofitClient.getLiveClient():RetrofitClient.getDemoClient();
        Retrofit retrofit=getClient(context);
        RetrofitConfigInterface retrofitConfigInterface = retrofit.create(RetrofitConfigInterface.class);
        for (int i = 0; i < files.size(); i++) {

            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), fileReqBody);
            parts.add(part);
        }

        RequestBody client_id = RequestBody.create(MediaType.parse("text/plain"), Keys.CONST_CLIENT_ID);
        RequestBody client_key = RequestBody.create(MediaType.parse("text/plain"), Keys.CONST_CLIENT_KEY);

        Call call = retrofitConfigInterface.uploadImages(client_id, client_key, parts);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
//                Log.e("imggg",t.getMessage());
            }
        });
    }

    public static List<String> getArrayFromMap(Map<Integer, String> map) {
        List<String> jsonArray = new ArrayList<>();

        for (Integer key : map.keySet()) {
            JSONObject jsonData = new JSONObject();
            String value = map.get(key);

            try {
                jsonData.put("question_id", key);
                jsonData.put("response", value);
                jsonArray.add(jsonData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    public static void showToast(Context context, String msg, boolean success) {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(success ? R.drawable.success : R.drawable.error);
        toastContentView.addView(imageView, 0);
        }
        toast.show();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             toast.cancel();
            }
        },1500);
    }

    public void showAlertPopup(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        if (cursor != null) {
            cursor.close();
        }
        return res;
    }

    private void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) for (File child : fileOrDirectory.listFiles())
            deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public void deleteFolder() {
        File dir = new File(Environment.getExternalStorageDirectory() + "//");
        /*if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }*/
        deleteRecursive(dir);
    }

}
