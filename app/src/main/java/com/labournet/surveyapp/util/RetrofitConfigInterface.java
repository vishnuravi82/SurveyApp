package com.labournet.surveyapp.util;



import com.labournet.surveyapp.model.BasicFarmer;
import com.labournet.surveyapp.model.PincodeDetailsResponse;
import com.labournet.surveyapp.model.SignInResponse;
import com.labournet.surveyapp.model.SubmitFamily;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Athira on 11/6/19.
 */
public interface RetrofitConfigInterface {

    @FormUrlEncoded
    @POST("/submit_farmer_basic_details")
    Call<BasicFarmer> Basic_farmer(
            @Field(Keys.KEY_CLIENT_ID) String client_id,
            @Field(Keys.KEY_CLIENT_KEY) String client_key,
            @Field("house_hold_id") int house_hold_id,
            @Field("hhcode") String hhcode,
            @Field("name") String name,
            @Field("house_no") String house_no,
            @Field("pincode") String pincode,
            @Field("district") String district,
            @Field("state") String state,

            @Field("country") String country,
            @Field("cast") String cast,
            @Field("resident_village") String resident_village,
            @Field("total_family_income") String total_family_income,
            @Field("total_family_annual_income") String total_family_annual_income,
            @Field("photo") String photo,
            @Field("created_by") String created_by,
             @Field("is_active") String is_active
    );


    @GET("/login")
    Call<SignInResponse> userLogin(
            @Query(Keys.KEY_CLIENT_ID) String client_id,
            @Query(Keys.KEY_CLIENT_KEY) String client_key,
            @Query("username") String username,
            @Query("password") String password);
    @Multipart
    @POST("/upload_image")
    Call<ResponseBody> uploadImage(
            @Part(Keys.KEY_CLIENT_ID) RequestBody client_id,
            @Part(Keys.KEY_CLIENT_KEY) RequestBody client_key,
            @Part MultipartBody.Part file);
    @Multipart
    @POST("/upload_multiple_images")
    Call<ResponseBody> uploadImages(
            @Part(Keys.KEY_CLIENT_ID) RequestBody client_id,
            @Part(Keys.KEY_CLIENT_KEY) RequestBody client_key,
            @Part List<MultipartBody.Part> files);

    @GET("/pincode/{pc}")
    Call<List<PincodeDetailsResponse>> getPcDetails(
            @Path("pc") int pc);
    @GET("/s3_signature")
    Call<ResponseBody> getS3Signature(
            @Query("file_name") String file_name,
            @Query("file_type") String file_type);
    @Multipart
    @POST(RetrofitClient.BASE_S3_UPLOAD_URL)
    Call<Void> uploadToS3(
            @Part("acl") RequestBody acl,
            @Part("Content-Type") RequestBody content_type,
            @Part("key") RequestBody key,
            @Part("x-amz-algorithm") RequestBody x_amz_algorithm,
            @Part("x-amz-credential") RequestBody x_amz_credential,
            @Part("x-amz-date") RequestBody x_amz_date,
            @Part("policy") RequestBody policy,
            @Part("x-amz-signature") RequestBody x_amz_signature,
            @Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST("/submit_farmer_family_details")
    Call<SubmitFamily> submitCandResponse(
            @Field(Keys.KEY_CLIENT_ID) String client_id,
            @Field(Keys.KEY_CLIENT_KEY) String client_key,
            @Field("user_id") int user_id,

            @Field("xml") String xml
           );
    @FormUrlEncoded
    @POST("/submit_farmer_water_source_details")
    Call<SubmitFamily> submitsourceResponse(
            @Field(Keys.KEY_CLIENT_ID) String client_id,
            @Field(Keys.KEY_CLIENT_KEY) String client_key,
            @Field("user_id") int user_id,

            @Field("xml") String xml
    );

}











