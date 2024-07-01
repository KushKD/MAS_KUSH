package com.dit.hp.janki.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.Modal.User;
import com.dit.hp.janki.R;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.dit.hp.janki.utilities.Preferences;

import org.json.JSONException;

import javax.net.ssl.HttpsURLConnection;

public class Registration extends AppCompatActivity implements AsyncTaskListenerObject {



    CustomDialog CD = new CustomDialog();


    EditText mobile, otp;
    Button register;
    TextView register_link ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        requestPermissions();

        mobile = findViewById(R.id.mobile);
        register = findViewById(R.id.register);
        otp = findViewById(R.id.otp);
        register_link = findViewById(R.id.register_link);



        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Registration.this, SignUp.class);
                Registration.this.startActivity(mainIntent);

            }
        });

        // Set a TextWatcher to monitor changes in the EditText
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used in this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check the length of the text after it has been changed
                String mobileNumber = s.toString().trim();
                if (mobileNumber.length() == 10) {
                    if (AppStatus.getInstance(Registration.this).isOnline()) {

                        UploadObject object = new UploadObject();
                        object.setUrl(Econstants.url);
                        object.setTasktype(TaskType.VERIFY_CONTACT);
                        object.setMethordName(Econstants.methordVerifyContact);
                        object.setParam(mobile.getText().toString().trim());


                        new GenericAsyncPostObject(
                                Registration.this,
                                Registration.this,
                                TaskType.VERIFY_CONTACT).
                                execute(object);

                    } else {
                        CD.showDialog(Registration.this, "Please Connect to Internet and try again.");
                    }
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString()!=null && otp.getText().length()==4 && !otp.getText().toString().isEmpty()){

                    if (AppStatus.getInstance(Registration.this).isOnline()) {

                        UploadObject object = new UploadObject();
                        object.setUrl(Econstants.url);
                        object.setTasktype(TaskType.VERIFY_OTP);
                        object.setMethordName(Econstants.methordVerifyOtp);
                        object.setParam(mobile.getText().toString().trim());
                        object.setParam2(otp.getText().toString().trim());


                        new GenericAsyncPostObject(
                                Registration.this,
                                Registration.this,
                                TaskType.VERIFY_OTP).
                                execute(object);

                    } else {
                        CD.showDialog(Registration.this, "Please Connect to Internet and try again.");
                    }



                }else{
                    CD.showDialog(Registration.this, "Please enter valid OTP.");
                }

            }
        });


        if (AppStatus.getInstance(Registration.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.GET_AUTHENTICATION_TOKEN);
            object.setMethordName(Econstants.methordAuthentication);


            new GenericAsyncPostObject(
                    Registration.this,
                    Registration.this,
                    TaskType.GET_AUTHENTICATION_TOKEN).
                    execute(object);

        } else {
            CD.showDialog(Registration.this, "Please Connect to Internet and try again.");
        }


    }


    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS


                }, 0);
            }
        }
    }


    @Override
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (TaskType.GET_AUTHENTICATION_TOKEN == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    Preferences.getInstance().loadPreferences(Registration.this);
                    Preferences.getInstance().transKey = String.valueOf(response.getTrans_key());
                    //Preferences.getInstance().isLoggedIn = true;
                    //Preferences.getInstance().appType_ = "AMBULANCE_SERVICE";
                    Preferences.getInstance().savePreferences(Registration.this);

                    System.out.println("Trans Key:- \t" + Preferences.getInstance().transKey);
                   // otp.setVisibility(View.VISIBLE);

//                    Intent mainIntent = new Intent(Registration.this, MainActivity.class); //MainActivity
//                    Registration.this.startActivity(mainIntent);
//                    Registration.this.finish();

                }else{
                    CD.showDialog(Registration.this, "Unrecognised Response from Server. Please try again.");
                   // otp.setVisibility(View.GONE);
                }

            }else{
                CD.showDialog(Registration.this, "Unable to get Data from server. Please try again.");
            }




        }

        //VERIFY_CONTACT
        if (TaskType.VERIFY_CONTACT == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                System.out.println(response);
                if (!response.getError()) {

                    CD.showDialog(Registration.this, response.getMessage() +"\n"+ response.getOtp_message() +"\n"+ response.getOtp());
                    otp.setVisibility(View.VISIBLE);
                    otp.setText(response.getOtp());
//                    Intent mainIntent = new Intent(Registration.this, MainActivity.class); //MainActivity
//                    Registration.this.startActivity(mainIntent);
//                    Registration.this.finish();

                }else{
                    CD.showDialog(Registration.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(Registration.this, "Unable to get Data from server. Please try again.");
            }




        }

        //VERIFY_OTP
        if (TaskType.VERIFY_OTP == taskType) {
            User userData = new User();
            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                System.out.println(response);
                if (!response.getError()) {

                    /**
                     * Save Data to Prefrences
                     */

                    userData = JsonParse.getUserDetails(response.getData());
                    System.out.println(userData);

                    Toast.makeText(Registration.this, response.getMessage(), Toast.LENGTH_LONG).show();

                    Preferences preferences = Preferences.getInstance();
                    preferences.loadPreferences(Registration.this);


                    preferences.setId(userData.getId());
                    preferences.setAccessAdminId(userData.getAccessAdminId());
                    preferences.setAdminId(userData.getAdminId());
                    preferences.setCompanyId(userData.getCompanyId());
                    preferences.setLoginId(userData.getLoginId());
                    preferences.setFullName(userData.getFullName());
                    preferences.setEmail(userData.getEmail());
                    preferences.setRole(userData.getRole());
                    preferences.setContact(userData.getContact());
                    preferences.setGender(userData.getGender());
                    preferences.setAddress(userData.getAddress());
                    preferences.setState(userData.getState());
                    preferences.setCity(userData.getCity());
                    preferences.setPinCode(userData.getPinCode());
                    preferences.setProfilePic(userData.getProfilePic());
                    preferences.setStatus(userData.getStatus());
                    preferences.setCreatedBy(userData.getCreatedBy());
                    preferences.setCreatedOn(userData.getCreatedOn());
                    preferences.setAadharImage(userData.getAadharImage());
                    preferences.setOtherImage(userData.getOtherImage());
                    preferences.setRemark(userData.getRemark());
                    preferences.setDlNo(userData.getDlNo());
                    preferences.setUserType(userData.getUserType());
                    preferences.setRoleName(userData.getRoleName());


                    Preferences.getInstance().isLoggedIn = true;
                    Preferences.getInstance().appType_ = "AMBULANCE_SERVICE";
                    Preferences.getInstance().savePreferences(Registration.this);
                    System.out.println(response.getData());

                    System.out.println(preferences.getRole());
                    Intent mainIntent = new Intent(Registration.this, MainActivity.class); //MainActivity
                    Registration.this.startActivity(mainIntent);
                    Registration.this.finish();

                }else{
                    CD.showDialog(Registration.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(Registration.this, "Unable to get Data from server. Please try again.");
            }




        }


    }
}