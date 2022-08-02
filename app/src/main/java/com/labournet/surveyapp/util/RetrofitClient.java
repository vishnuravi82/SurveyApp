package com.labournet.surveyapp.util;

import android.util.Log;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Athira on 11/6/19.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    //change in network_security_config and Keys

    public static String BASE_DEMO_URL = "http://192.168.1.5:5000";

    public static String BASE_PINCODE_URL = "http://api.postalpincode.in";
    public static String BASE_UPLOAD_URL = "https://neo.certiplate.com";

    public static String BASE_S3_URL = "https://col-neo.labournet.in";
    public static final String BASE_S3_UPLOAD_URL = "https://labournet.s3.amazonaws.com/";

    private RetrofitClient() {
    }
    public static Retrofit getS3UploadClient() {

        Log.e("server","s3 upload");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_S3_UPLOAD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
    public static Retrofit getS3Client() {

        Log.e("server","s3");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_S3_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
    public static Retrofit getUploadClient() {

        Log.e("server","upload");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_UPLOAD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static class UnsafeOkHttpClient {
        public static OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Retrofit getPincodeClient() {

        Log.e("server","pincode");
        /*OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();*/
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_PINCODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    public static Retrofit getDemoClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Log.e("server","demo");
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)

//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
			.connectTimeout(2, TimeUnit.MINUTES)
			.readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_DEMO_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        return retrofit;
    }

}
