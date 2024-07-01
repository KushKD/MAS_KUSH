package com.dit.hp.janki.activities;

import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.dit.hp.janki.utilities.Preferences;
import com.dit.hp.janki.utilities.SamplePresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.Configurations;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;
import com.kushkumardhawan.locationmanager.constants.FailType;
import com.kushkumardhawan.locationmanager.constants.ProcessType;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class BookAmbulance extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObject {

    TextView booking_date,booking_time,booking_time_upto;
    EditText booking_location,destination_location;


    Button save, back;
    CustomDialog CD = new CustomDialog();

    private SamplePresenter samplePresenter;
    Calendar calendar = null;
    public String userLocation = null;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ambulance);

//        if (!Places.isInitialized()) {
//            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
//        }


        if (AppStatus.getInstance(BookAmbulance.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.GET_AUTHENTICATION_TOKEN);
            object.setMethordName(Econstants.methordAuthentication);


            new GenericAsyncPostObject(
                    BookAmbulance.this,
                    BookAmbulance.this,
                    TaskType.GET_AUTHENTICATION_TOKEN).
                    execute(object);

        } else {
            CD.showDialog(BookAmbulance.this, "Please Connect to Internet and try again.");
        }

        if (AppStatus.getInstance(BookAmbulance.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.DEFAULT_ADMIN_DIS);
            object.setMethordName(Econstants.methordDefadmindet);


            new GenericAsyncPostObject(
                    BookAmbulance.this,
                    BookAmbulance.this,
                    TaskType.DEFAULT_ADMIN_DIS).
                    execute(object);

        } else {
            CD.showDialog(BookAmbulance.this, "Please Connect to Internet and try again.");
        }



        booking_date = findViewById(R.id.booking_date);
        booking_time = findViewById(R.id.booking_time);
        booking_time_upto = findViewById(R.id.booking_time_upto);

        booking_location = findViewById(R.id.booking_location);
        destination_location = findViewById(R.id.destination_location);

        save = findViewById(R.id.save);
        back = findViewById(R.id.back);

        // Set up Calendar instance
        calendar = Calendar.getInstance();

        // Set onClickListener for Date and Time pickers
        setDateTimePickerListeners();

        samplePresenter = new SamplePresenter(this);
        getLocation();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookAmbulance.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(booking_date.getText().toString()!=null && !booking_date.getText().toString().isEmpty()){
                    if(booking_time.getText().toString()!=null && !booking_time.getText().toString().isEmpty()){
                        if(booking_time_upto.getText().toString()!=null && !booking_time_upto.getText().toString().isEmpty()){
                            if(booking_location.getText().toString()!=null && !booking_location.getText().toString().isEmpty()){
                                if(destination_location.getText().toString()!=null && !destination_location.getText().toString().isEmpty()){
                                    if (AppStatus.getInstance(BookAmbulance.this).isOnline()) {

                                        UploadObject object = new UploadObject();
                                        object.setUrl(Econstants.url);
                                        object.setTasktype(TaskType.AMBULANCE_BOOKING);
                                        object.setMethordName(Econstants.methodAmbulancebooking);
                                        object.setParam(booking_date.getText().toString().trim());
                                        object.setParam2(booking_time.getText().toString().trim());
                                        object.setParam3(booking_time_upto.getText().toString().trim());
                                        object.setParam4(userLocation.split(",")[0]);
                                        object.setParam5(userLocation.split(",")[1]);


                                        new GenericAsyncPostObject(
                                                BookAmbulance.this,
                                                BookAmbulance.this,
                                                TaskType.AMBULANCE_BOOKING).
                                                execute(object);

                                    } else {
                                        CD.showDialog(BookAmbulance.this, "Please Connect to Internet and try again.");
                                    }

                                }else{
                                    CD.showDialog(BookAmbulance.this, "Please select Destination up Point Time.");
                                }
                            }else{
                                CD.showDialog(BookAmbulance.this, "Please select Pick up Point Time.");
                            }
                        }else{
                            CD.showDialog(BookAmbulance.this, "Please select Buffer Time.");
                        }
                    }else{
                        CD.showDialog(BookAmbulance.this, "Please select Booking Time.");
                    }
                }else{
                    CD.showDialog(BookAmbulance.this, "Please select Booking Date.");
                }


            }
        });



        // Set up Places Autocomplete for booking_location
        //setUpAutocompleteFragment(R.id.autocomplete_booking_location, booking_location);

        // Set up Places Autocomplete for destination_location
        //setUpAutocompleteFragment(R.id.autocomplete_destination_location, destination_location);


    }

    private void setUpAutocompleteFragment(int fragmentId, final EditText editText) {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(fragmentId);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setHint(getString(R.string.enter_location));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Handle the selected place
                editText.setText(place.getAddress());
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle the error
                Toast.makeText(BookAmbulance.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
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

        booking_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(booking_time);
            }
        });

        booking_time_upto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(booking_time_upto);
            }
        });


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
        } else if (editText == booking_time || editText == booking_time_upto) {
            editText.setText(timeFormat.format(calendar.getTime()));
        }
    }

    @Override
    public void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException {

        if (TaskType.AMBULANCE_BOOKING == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    CD.showDialogCloseActivity(BookAmbulance.this,response.getMessage());

                }else{
                    CD.showDialog(BookAmbulance.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookAmbulance.this, "Unable to get Data from server. Please try again.");
            }
        }
        if (TaskType.GET_AUTHENTICATION_TOKEN == taskType) {

            if (result.getResponseCode().equalsIgnoreCase(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                System.out.println(result.getResponse());
                SuccessResponse response = null;
                response = JsonParse.getSuccessResponse(result.getResponse());
                if (!response.getError()) {
                    Preferences.getInstance().loadPreferences(BookAmbulance.this);
                    Preferences.getInstance().transKey = String.valueOf(response.getTrans_key());
                    Preferences.getInstance().savePreferences(BookAmbulance.this);
                    System.out.println("Trans Key:- \t" + Preferences.getInstance().transKey);

                }else{
                    CD.showDialog(BookAmbulance.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookAmbulance.this, "Unable to get Data from server. Please try again.");
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

                    Preferences.getInstance().loadPreferences(BookAmbulance.this);

                    Preferences.getInstance().setAccessAdminId(dataObject.optString("access_admin_id"));
                    Preferences.getInstance().setCompanyId(dataObject.optString("company_id"));

                    Preferences.getInstance().savePreferences(BookAmbulance.this);
                    System.out.println("Access Admin ID :- \t" + Preferences.getInstance().getAccessAdminId());
                    System.out.println("Company ID :- \t" + Preferences.getInstance().getCompanyId());


                }else{
                    CD.showDialog(BookAmbulance.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(BookAmbulance.this, "Unable to get Data from server. Please try again.");
            }
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
}