package com.dit.hp.janki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.dit.hp.janki.Adapter.GenericAdapterGender;
import com.dit.hp.janki.Modal.GenderPojo;
import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.R;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SignUp extends AppCompatActivity implements AsyncTaskListenerObject {

    EditText name, phone, email,address;
    SearchableSpinner gender;
    Button back, signup;
    GenericAdapterGender adapter_gender;

    String Global_Gender_Name, Global_Gender_id;

    CustomDialog CD = new CustomDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        gender = findViewById(R.id.gender);
        gender.setTitle("Please Select Gender");
        gender.setPrompt("Please Select Gender");

        address = findViewById(R.id.address);

        back = findViewById(R.id.back);
        signup = findViewById(R.id.signup);

        List<GenderPojo> property = new ArrayList<>();
        GenderPojo pojo1 = new GenderPojo();
        pojo1.setId(Integer.toString(1));
        pojo1.setGender("Male");

        GenderPojo pojo2 = new GenderPojo();
        pojo2.setId(Integer.toString(2));
        pojo2.setGender("Female");

        GenderPojo pojo3 = new GenderPojo();
        pojo3.setId(Integer.toString(3));
        pojo3.setGender("Other");

        property.add(pojo1);
        property.add(pojo2);
        property.add(pojo3);


        adapter_gender = new GenericAdapterGender(this, android.R.layout.simple_spinner_item, property);
        gender.setAdapter(adapter_gender);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GenderPojo item = adapter_gender.getItem(position);

                Global_Gender_id = item.getId();
                Global_Gender_Name = item.getGender();
                System.out.println(Global_Gender_Name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.this.finish();
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Global_Gender_id!=null && !Global_Gender_id.isEmpty()){
                    if(name.getText().toString()!=null && !name.getText().toString().isEmpty()){
                        if(address.getText().toString()!=null && !address.getText().toString().isEmpty()){
                            if(phone.getText().toString()!=null && !phone.getText().toString().isEmpty() && phone.getText().length()==10){

                                /**
                                 * Resigter the User
                                 */

                                if (AppStatus.getInstance(SignUp.this).isOnline()) {

                                    UploadObject object = new UploadObject();
                                    object.setUrl(Econstants.url);
                                    object.setTasktype(TaskType.SIGN_UP);
                                    object.setMethordName(Econstants.methordRegister);
                                    object.setParam(name.getText().toString().trim());
                                    object.setParam2(phone.getText().toString().trim());
                                    object.setParam3(Global_Gender_Name);
                                    object.setParam4(email.getText().toString().trim());


                                    new GenericAsyncPostObject(
                                            SignUp.this,
                                            SignUp.this,
                                            TaskType.SIGN_UP).
                                            execute(object);

                                } else {
                                    CD.showDialog(SignUp.this, "Please Connect to Internet and try again.");
                                }



                            }else{
                                CD.showDialog(SignUp.this,"Please enter valid 10 digit Phone number.");
                            }
                        }else{
                            CD.showDialog(SignUp.this,"Please enter Address");
                        }
                    }else{
                        CD.showDialog(SignUp.this,"Please enter Name");
                    }
                }else{
                    CD.showDialog(SignUp.this,"Please Select Gender");
                }
            }
        });



    }

    @Override
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (TaskType.SIGN_UP == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                System.out.println(response);
                if (!response.getError()) {

                    CD.showDialogCloseActivity(SignUp.this, response.getMessage());



                }else{
                    CD.showDialog(SignUp.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(SignUp.this, "Unable to get Data from server. Please try again.");
            }




        }
    }
}