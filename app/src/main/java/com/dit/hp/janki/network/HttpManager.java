package com.dit.hp.janki.network;


import android.util.Log;

import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.Preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpManager {



    public HttpManager(){
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            TrustManager[] tm = {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }



    public ResponsePojoGet PostData(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);
            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    public ResponsePojoGet PostData_VERIFYCONTACT(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            // Append mobile parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"mobile\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    //VerifyOTP
    public ResponsePojoGet PostData_VerifyOTP(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            // Append mobile parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"mobile\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);

            // Append OTP parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"otp\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam2()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    public ResponsePojoGet PostData_SIGNUP(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"access_admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAccessAdminId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAdminId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"company_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getCompanyId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"name\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"email\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam4()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"gender\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam3()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"contact\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam2()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    public ResponsePojoGet PostData_DEFAULTADMIN(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }


    public ResponsePojoGet PostData_ALLLABS(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

//            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
//            postDataBuilder.append("Content-Disposition: form-data; name=\"id\"").append(lineEnd);
//            postDataBuilder.append(lineEnd);
//            postDataBuilder.append(Preferences.getInstance().getId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"access_admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAccessAdminId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAdminId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"company_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getCompanyId()).append(lineEnd);

//            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
//            postDataBuilder.append("Content-Disposition: form-data; name=\"pathology_name\"").append(lineEnd);
//            postDataBuilder.append(lineEnd);
//            postDataBuilder.append("").append(lineEnd);
//
//            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
//            postDataBuilder.append("Content-Disposition: form-data; name=\"phone_no\"").append(lineEnd);
//            postDataBuilder.append(lineEnd);
//            postDataBuilder.append("").append(lineEnd);
//
//            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
//            postDataBuilder.append("Content-Disposition: form-data; name=\"email\"").append(lineEnd);
//            postDataBuilder.append(lineEnd);
//            postDataBuilder.append("").append(lineEnd);



            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    public ResponsePojoGet PostData_ALLLABTESTS(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"lab_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"access_admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAccessAdminId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAdminId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"company_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getCompanyId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }


    public ResponsePojoGet PostData_AMBULANCE_BOOKING(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            // Append id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"user_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getId()).append(lineEnd);

            // Append id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append("1").append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"access_admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAccessAdminId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAdminId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"company_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getCompanyId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"booking_date\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"booking_type\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append("Ambulance").append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"pick_up_time_from\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam2()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"pick_up_time_upto\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam3()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"source_lat\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam4()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"source_long\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam5()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"dest_lat\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Long.parseLong("123")).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"dest_long\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Long.parseLong("123")).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            System.out.println("Data to Post \t :" +postDataBuilder.toString());
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }



    public ResponsePojoGet PostData_LAB_BOOKING(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        String URL = null;
        ResponsePojoGet response = null;

        try {
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL = data.getUrl()+data.getMethordName();
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            if (conn_ instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn_).setSSLSocketFactory(getSSLSocketFactory());
            }
            conn_.setDoOutput(true);
            conn_.setDoInput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"authkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Econstants.authKey).append(lineEnd);


            // Append trans_id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"transkey\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().transKey).append(lineEnd);

            // Append id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"user_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getId()).append(lineEnd);

            // Append id parameter
            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append("1").append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"access_admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAccessAdminId()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"admin_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getAdminId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"company_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Preferences.getInstance().getCompanyId()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"booking_date\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam()).append(lineEnd);


            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"booking_type\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append("Pathology").append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"lab_test_home_pick\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam2()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"lab_test_phy_visit\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam3()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"source_lat\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam4()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"source_long\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam5()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"dest_lat\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Long.parseLong("123")).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"dest_long\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(Long.parseLong("123")).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"pick_up_time_from\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam6()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"pick_up_time_upto\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam7()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"pathology_id\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam8()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"visit_time_from\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam9()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"visit_time_upto\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam10()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(lineEnd);
            postDataBuilder.append("Content-Disposition: form-data; name=\"test_det_arr\"").append(lineEnd);
            postDataBuilder.append(lineEnd);
            postDataBuilder.append(data.getParam11()).append(lineEnd);

            postDataBuilder.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);
            conn_.connect();
            OutputStream out = conn_.getOutputStream();
            System.out.println("Data to Post \t :" +postDataBuilder.toString());
            out.write(postDataBuilder.toString().getBytes());
            out.flush();
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK ) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString() , Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {

                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }



}