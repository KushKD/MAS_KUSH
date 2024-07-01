package com.dit.hp.janki.utilities;

import com.dit.hp.janki.Modal.ResponsePojoGet;

public class Econstants {


    public static final String url = "https://hr51.in/ambulance_api";
    public static final String methordAuthentication = "/authorization";
    public static final String methordVerifyContact = "/verifycontact";
    public static final String methordRegister = "/register";
    public static final String methordVerifyOtp = "/verifyotp";
    public static final String methordDefadmindet = "/defadmindet";
    public static final String methordAlllab = "/alllab";
    public static final String methordAlllabTests = "/alllabtest";



    public static final String methodAmbulancebooking = "/ambulancebookingapp";
    public static final String methodLabbooking = "/labbooking";
    public static final String authKey = "c7c2c5b6457f67800b5b650d86c2be961ad7bd585d2006e2cce0557065bdd4adbac33a7fcc38570adf83e3e3f64a2d01665e93594a13ad6587ede6e20cf7aa15qEuu4TxFlEFHigd5XE1j+sJOZ0Bj924PVYKo6+JjN7aDUIMSP5IgaNo3eniXPxTbE1V+1nEFNmYQ/HkYquumoF//j8aCMkhPB1b1uyyHAPU=";

    public static ResponsePojoGet createOfflineObject(String url, String requestParams, String response, String Code, String functionName) {
        ResponsePojoGet pojo = new ResponsePojoGet();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);
        pojo.setFunctionName(functionName);
        pojo.setResponseCode(Code);
        return pojo;
    }



}
