package com.dit.hp.janki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.dit.hp.janki.Adapter.GenericAdapterLabsTests;
import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.TestDetailPojo;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.R;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.dit.hp.janki.utilities.Preferences;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LabTests extends AppCompatActivity implements AsyncTaskListenerObject {

    Button  add_test, save, back, clear_list;
    TextView test_list_textview;
    SearchableSpinner select_test;
    
    CustomDialog CD = new CustomDialog();

    List<TestDetailPojo> labTests = new ArrayList<>();

    GenericAdapterLabsTests adapterLabsTests;
    List<TestDetailPojo> addedLabTests = null;

    TestDetailPojo dataFromSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_tests);

        Intent intent = getIntent();
        String LabId = intent.getStringExtra("LabId");

        add_test = findViewById(R.id.add_test);
        save  = findViewById(R.id.save);
        back = findViewById(R.id.back);
        select_test = findViewById(R.id.select_test);
        clear_list = findViewById(R.id.clear_list);
        test_list_textview = findViewById(R.id.test_list_textview);
        test_list_textview.setText("Total Lab Tests Added: 0");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LabTests.this.finish();
            }
        });


        addedLabTests = new ArrayList<>();
        add_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                boolean isAlreadyAdded = false;
                for (TestDetailPojo existingCrop : addedLabTests) {
                    if (existingCrop.equals(dataFromSpinner)) {
                        isAlreadyAdded = true;
                        break;
                    }
                }

                if (!isAlreadyAdded) {
                    addedLabTests.add(dataFromSpinner);
                    test_list_textview.setText("Total Lab Test Added: " + addedLabTests.size());
                    CD.showDialog(LabTests.this,  dataFromSpinner.getTestName() +  " added to list.");
                } else {
                    CD.showDialog(LabTests.this, dataFromSpinner.getTestName() +  " Already Added.");
                }

            }
        });


        clear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedLabTests.clear();
                CD.showDialog(LabTests.this, "Added Lab Tests List Cleared.");
                if(addedLabTests.isEmpty()){
                    test_list_textview.setText("Total Lab Tests Added: 0");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addedLabTests.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("testLists", new ArrayList<>(addedLabTests));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    CD.showDialog(LabTests.this, "Please add Lab Tests.");
                }
        }
    });


        if (AppStatus.getInstance(LabTests.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.GET_AUTHENTICATION_TOKEN);
            object.setMethordName(Econstants.methordAuthentication);


            new GenericAsyncPostObject(
                    LabTests.this,
                    LabTests.this,
                    TaskType.GET_AUTHENTICATION_TOKEN).
                    execute(object);

        } else {
            CD.showDialog(LabTests.this, "Please Connect to Internet and try again.");
        }

        if (AppStatus.getInstance(LabTests.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.DEFAULT_ADMIN_DIS);
            object.setMethordName(Econstants.methordDefadmindet);


            new GenericAsyncPostObject(
                    LabTests.this,
                    LabTests.this,
                    TaskType.DEFAULT_ADMIN_DIS).
                    execute(object);

        } else {
            CD.showDialog(LabTests.this, "Please Connect to Internet and try again.");
        }

        /**
         * GET LABS
         */
        if (AppStatus.getInstance(LabTests.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.ALL_LAB_TESTS);
            object.setMethordName(Econstants.methordAlllabTests);
            object.setParam(LabId);


            new GenericAsyncPostObject(
                    LabTests.this,
                    LabTests.this,
                    TaskType.ALL_LAB_TESTS).
                    execute(object);

        } else {
            CD.showDialog(LabTests.this, "Please Connect to Internet and try again.");
        }



        select_test.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TestDetailPojo item = adapterLabsTests.getItem(position);

                dataFromSpinner = new TestDetailPojo();

                dataFromSpinner = item;

                System.out.println(" dataFromSpinner " + dataFromSpinner.toString());
                System.out.println(" dataFromSpinner " + dataFromSpinner.getTestCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (TaskType.GET_AUTHENTICATION_TOKEN == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    Preferences.getInstance().loadPreferences(LabTests.this);
                    Preferences.getInstance().transKey = String.valueOf(response.getTrans_key());
                    Preferences.getInstance().savePreferences(LabTests.this);
                    System.out.println("Trans Key:- \t" + Preferences.getInstance().transKey);

                }else{
                    CD.showDialog(LabTests.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(LabTests.this, "Unable to get Data from server. Please try again.");
            }
        }

        if (TaskType.ALL_LAB_TESTS == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {

                    System.out.println(response.getData());
                    //getLabDetails
                    labTests = JsonParse.getTestDetails(response.getData());
                    System.out.println(labTests.toString());
                    adapterLabsTests = new GenericAdapterLabsTests(this, android.R.layout.simple_spinner_item, labTests);
                    select_test.setAdapter(adapterLabsTests);

                }else{
                    CD.showDialog(LabTests.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(LabTests.this, "Unable to get Data from server. Please try again.");
            }
        }



        if (TaskType.DEFAULT_ADMIN_DIS == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {

                    JSONObject responseObject = new JSONObject(result.getResponse());
                    JSONObject dataObject = responseObject.optJSONObject("data");
                    if (dataObject == null) {
                        throw new JSONException("No data found in response");
                    }

                    Preferences.getInstance().loadPreferences(LabTests.this);

                    Preferences.getInstance().setAccessAdminId(dataObject.optString("access_admin_id"));
                    Preferences.getInstance().setCompanyId(dataObject.optString("company_id"));

                    Preferences.getInstance().savePreferences(LabTests.this);
                    System.out.println("Access Admin ID :- \t" + Preferences.getInstance().getAccessAdminId());
                    System.out.println("Company ID :- \t" + Preferences.getInstance().getCompanyId());


                }else{
                    CD.showDialog(LabTests.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(LabTests.this, "Unable to get Data from server. Please try again.");
            }
        }
    }
}