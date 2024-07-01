package com.dit.hp.janki.activities;

import androidx.annotation.Nullable;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dit.hp.janki.Adapter.GenericAdapterLabs;
import com.dit.hp.janki.Adapter.GenericAdapterYes;
import com.dit.hp.janki.Modal.LabPojo;
import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.TestDetailPojo;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.Modal.YesNoPojo;
import com.dit.hp.janki.R;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.dit.hp.janki.utilities.Preferences;
import com.dit.hp.janki.utilities.SamplePresenter;
import com.doi.spinnersearchable.SearchableSpinner;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.Configurations;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;
import com.kushkumardhawan.locationmanager.constants.FailType;
import com.kushkumardhawan.locationmanager.constants.ProcessType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class BookLab extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObject {

    TextView booking_date ,pickup_time_from , pickup_time_upto, visit_time_from, visit_time_upto;
    EditText address;
    SearchableSpinner  lab_select, lab_home , lab_visit;

    private SamplePresenter samplePresenter;
    Calendar calendar = null;
    public String userLocation = null;

    private ProgressDialog progressDialog;

    CustomDialog CD = new CustomDialog();

    GenericAdapterYes adapter_yesno;

    Button back, save, add_test;

    String Glbal_lab_home_id, GLobal_lab_home_name;
    String Glbal_lab_visit_id, GLobal_lab_visit_name;

    List<LabPojo> labs = new ArrayList<>();

    GenericAdapterLabs adapterLabs;

    String Glbal_lab_Select_id,  GLobal_lab_Select_name;
    List<TestDetailPojo> addedTestList = new ArrayList<>();

    LinearLayout home_pick , visit_lab_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lab);

        booking_date = findViewById(R.id.booking_date);
        pickup_time_from = findViewById(R.id.pickup_time_from);
        pickup_time_upto = findViewById(R.id.pickup_time_upto);
        visit_time_from = findViewById(R.id.visit_time_from);
        visit_time_upto = findViewById(R.id.visit_time_upto);
        address = findViewById(R.id.address);


        lab_select = findViewById(R.id.lab_select);
        lab_home = findViewById(R.id.lab_home);
        lab_visit = findViewById(R.id.lab_visit);
        home_pick = findViewById(R.id.home_pick);
        visit_lab_linear = findViewById(R.id.visit_lab_linear);

        back = findViewById(R.id.back);
        save = findViewById(R.id.save);

        setDateTimePickerListeners();
        // Set up Calendar instance
        calendar = Calendar.getInstance();


        samplePresenter = new SamplePresenter(this);
        getLocation();

        List<YesNoPojo> property = new ArrayList<>();
        YesNoPojo pojo1 = new YesNoPojo();
        pojo1.setId(Integer.toString(1));
        pojo1.setName("Yes");

        YesNoPojo pojo2 = new YesNoPojo();
        pojo2.setId(Integer.toString(2));
        pojo2.setName("No");

        property.add(pojo1);
        property.add(pojo2);

        adapter_yesno = new GenericAdapterYes(this, android.R.layout.simple_spinner_item, property);
        lab_home.setAdapter(adapter_yesno);
        lab_visit.setAdapter(adapter_yesno);

        add_test = findViewById(R.id.add_test);

        add_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if(Glbal_lab_home_id!=null && !Glbal_lab_home_id.isEmpty()){

                     Intent intent = new Intent(BookLab.this, LabTests.class);
                     intent.putExtra("LabId",Glbal_lab_Select_id);
                     startActivityForResult(intent, 1001);

                 }else{
                     CD.showDialog(BookLab.this,"Please Select Lab before adding test.");
                 }

            }
        });



        lab_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                YesNoPojo item = adapter_yesno.getItem(position);

                Glbal_lab_home_id = item.getId();
                GLobal_lab_home_name = item.getName();
                System.out.println(Glbal_lab_home_id);

                if(GLobal_lab_home_name.equalsIgnoreCase("No")){
                    home_pick.setVisibility(View.GONE);
                }else{
                    home_pick.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lab_visit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                YesNoPojo item = adapter_yesno.getItem(position);

                Glbal_lab_visit_id = item.getId();
                GLobal_lab_visit_name = item.getName();
                System.out.println(" ID " + Glbal_lab_visit_id);

                if(GLobal_lab_visit_name.equalsIgnoreCase("No")){
                    visit_lab_linear.setVisibility(View.GONE);
                }else{
                    visit_lab_linear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lab_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LabPojo item = adapterLabs.getItem(position);

                Glbal_lab_Select_id = item.getId();
                GLobal_lab_Select_name = item.getPathologyName();
                System.out.println(" ID " + Glbal_lab_Select_id);
                System.out.println(" Name " + GLobal_lab_Select_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if (AppStatus.getInstance(BookLab.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.GET_AUTHENTICATION_TOKEN);
            object.setMethordName(Econstants.methordAuthentication);


            new GenericAsyncPostObject(
                    BookLab.this,
                    BookLab.this,
                    TaskType.GET_AUTHENTICATION_TOKEN).
                    execute(object);

        } else {
            CD.showDialog(BookLab.this, "Please Connect to Internet and try again.");
        }

        if (AppStatus.getInstance(BookLab.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.DEFAULT_ADMIN_DIS);
            object.setMethordName(Econstants.methordDefadmindet);


            new GenericAsyncPostObject(
                    BookLab.this,
                    BookLab.this,
                    TaskType.DEFAULT_ADMIN_DIS).
                    execute(object);

        } else {
            CD.showDialog(BookLab.this, "Please Connect to Internet and try again.");
        }

        /**
         * GET LABS
         */
        if (AppStatus.getInstance(BookLab.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.ALL_LABS);
            object.setMethordName(Econstants.methordAlllab);


            new GenericAsyncPostObject(
                    BookLab.this,
                    BookLab.this,
                    TaskType.ALL_LABS).
                    execute(object);

        } else {
            CD.showDialog(BookLab.this, "Please Connect to Internet and try again.");
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookLab.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save data to server
                if(!addedTestList.isEmpty()){

                    if(Glbal_lab_Select_id!=null && !Glbal_lab_Select_id.isEmpty()){
                        if(booking_date.getText().toString()!=null && !booking_date.getText().toString().isEmpty()){


                            if (GLobal_lab_home_name.equalsIgnoreCase("Yes")) {
                                if (pickup_time_from.getText().toString() == null || pickup_time_from.getText().toString().isEmpty() ||
                                        pickup_time_upto.getText().toString() == null || pickup_time_upto.getText().toString().isEmpty()) {
                                    CD.showDialog(BookLab.this, "Please Select Pick Up Time From and Upto");
                                    return;
                                }
                            }

                            if (GLobal_lab_visit_name.equalsIgnoreCase("Yes")) {
                                if (visit_time_from.getText().toString() == null || visit_time_from.getText().toString().isEmpty() ||
                                        visit_time_upto.getText().toString() == null || visit_time_upto.getText().toString().isEmpty()) {
                                    CD.showDialog(BookLab.this, "Please Select Visiting Time (From and Upto).");
                                    return;
                                }
                            }

                            // All validations passed, proceed to save data to server
                           // saveDataToServer();
                            if (AppStatus.getInstance(BookLab.this).isOnline()) {

                                UploadObject object = new UploadObject();
                                object.setUrl(Econstants.url);
                                object.setTasktype(TaskType.LAB_BOOKING);
                                object.setMethordName(Econstants.methodLabbooking);
                                object.setParam(booking_date.getText().toString().trim());
                                object.setParam2(GLobal_lab_home_name);
                                object.setParam3(GLobal_lab_visit_name);
                                object.setParam4(userLocation.split(",")[0]);
                                object.setParam5(userLocation.split(",")[1]);
                                object.setParam6(pickup_time_from.getText().toString());
                                object.setParam7(pickup_time_upto.getText().toString());
                                object.setParam8(Glbal_lab_home_id);
                                object.setParam9(visit_time_from.getText().toString().trim());
                                object.setParam10(visit_time_upto.getText().toString());

                                /**
                                 * Create Array
                                 */

                                // Create a JSON array to store JSON objects
                                JSONArray jsonArray = new JSONArray();

                                try {
                                    // Convert each TestDetail object to a JSON object and add to the JSON array
                                    for (TestDetailPojo testDetail : addedTestList) {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("test_id", testDetail.getTestId());
                                        jsonObject.put("test_price", testDetail.getTestPrice());
                                        jsonArray.put(jsonObject);
                                    }
                                    Log.d("JSON Array", jsonArray.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                object.setParam11(jsonArray.toString());
                                System.out.println(object.toString());



                                new GenericAsyncPostObject(
                                        BookLab.this,
                                        BookLab.this,
                                        TaskType.LAB_BOOKING).
                                        execute(object);

                            } else {
                                CD.showDialog(BookLab.this, "Please Connect to Internet and try again.");
                            }


                        }else{
                            CD.showDialog(BookLab.this, "Please Select Booking Date.");
                        }
                    }else{
                        CD.showDialog(BookLab.this, "Please Select Lab.");
                    }


                }else{
                    CD.showDialog(BookLab.this, "Please add Lab Tests before booking.");
                }
            }
        });






    }

    private void setDateTimePickerListeners() {
        booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(booking_date);
            }
        });

        visit_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(visit_time_from);
            }
        });

        visit_time_upto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(visit_time_upto);
            }
        });

        pickup_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(pickup_time_from);
            }
        });

        pickup_time_upto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(pickup_time_upto);
            }
        });


    }

    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (TaskType.LAB_BOOKING == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    CD.showDialogCloseActivity(BookLab.this,response.getMessage());

                }else{
                    CD.showDialog(BookLab.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookLab.this, "Unable to get Data from server. Please try again.");
            }
        }

        if (TaskType.GET_AUTHENTICATION_TOKEN == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    Preferences.getInstance().loadPreferences(BookLab.this);
                    Preferences.getInstance().transKey = String.valueOf(response.getTrans_key());
                    Preferences.getInstance().savePreferences(BookLab.this);
                    System.out.println("Trans Key:- \t" + Preferences.getInstance().transKey);

                }else{
                    CD.showDialog(BookLab.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookLab.this, "Unable to get Data from server. Please try again.");
            }
        }

        if (TaskType.ALL_LABS == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {

                    //getLabDetails
                    labs = JsonParse.getLabDetails(response.getData());
                    System.out.println(labs.toString());
                    adapterLabs = new GenericAdapterLabs(this, android.R.layout.simple_spinner_item, labs);
                    lab_select.setAdapter(adapterLabs);



                }else{
                    CD.showDialog(BookLab.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookLab.this, "Unable to get Data from server. Please try again.");
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

                    Preferences.getInstance().loadPreferences(BookLab.this);

                    Preferences.getInstance().setAccessAdminId(dataObject.optString("access_admin_id"));
                    Preferences.getInstance().setCompanyId(dataObject.optString("company_id"));

                    Preferences.getInstance().savePreferences(BookLab.this);
                    System.out.println("Access Admin ID :- \t" + Preferences.getInstance().getAccessAdminId());
                    System.out.println("Company ID :- \t" + Preferences.getInstance().getCompanyId());


                }else{
                    CD.showDialog(BookLab.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookLab.this, "Unable to get Data from server. Please try again.");
            }
        }
    }

    private void showDatePicker(final TextView editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        updateEditText(editText);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
        // Restrict selection to dates from today onwards
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  // Subtract 1000 milliseconds to account for slight delays
    }

    private void showTimePicker(final TextView editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateEditText(editText);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }

    private void updateEditText(TextView editText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        if (editText == booking_date) {
            editText.setText(dateFormat.format(calendar.getTime()));
        } else if (editText == pickup_time_from || editText == pickup_time_upto || editText == visit_time_from || editText == visit_time_upto) {
            editText.setText(timeFormat.format(calendar.getTime()));
        }
    }

    /**
     * Location Interface Methords
     *
     * @return
     */
    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Permission Required !", "GPS needs to be turned on.");
    }

    @Override
    public void onLocationChanged(Location location) {
        samplePresenter.onLocationChanged(location);
    }

    @Override
    public void onLocationFailed(@FailType int type) {
        samplePresenter.onLocationFailed(type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public String getText() {
        return "";  //locationText.getText().toString()
    }

    @Override
    public void setText(String text) {
        //locationText.setText(text);
        Log.e("Location GPS", text);
        userLocation = text;
    }

    @Override
    public void updateProgress(String text) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(text);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int processType) {
        samplePresenter.onProcessTypeChanged(processType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samplePresenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {

                ArrayList<TestDetailPojo> returnedList = (ArrayList<TestDetailPojo>) data.getSerializableExtra("testLists");
                addedTestList = returnedList;
                System.out.println("Test List"+ addedTestList.size());
                System.out.println("Test List"+ addedTestList.toString());


            }
        }
    }
}