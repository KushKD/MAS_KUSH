package com.dit.hp.janki.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import com.dit.hp.janki.Adapter.HomeGridViewAdapter;
import com.dit.hp.janki.Adapter.SliderAdapter;
import com.dit.hp.janki.Modal.ModulesPojo;
import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.R;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.generic.GenericAsyncPostObject;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.AppStatus;
import com.dit.hp.janki.utilities.CommonUtils;
import com.dit.hp.janki.utilities.Econstants;
import com.dit.hp.janki.utilities.JsonParse;
import com.dit.hp.janki.utilities.Preferences;
import com.dit.hp.janki.utilities.SamplePresenter;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.Configurations;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;
import com.kushkumardhawan.locationmanager.constants.FailType;
import com.kushkumardhawan.locationmanager.constants.ProcessType;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends LocationBaseActivity implements SamplePresenter.SampleView, AsyncTaskListenerObject {

    CustomDialog CD = new CustomDialog();
    private final String LOGTAG = "QRCScanner-MainActivity";
    HomeGridViewAdapter adapter_modules;
    GridView home_gv;
    SliderView sliderView;
    SliderViewAdapter adapters = null;
    private ProgressDialog progressDialog;

    private SamplePresenter samplePresenter;
    public String userLocation = null;
    private BroadcastReceiver mReceiver;
    List<ModulesPojo> modules = null;

    TextView location, username, app_code ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        app_code = findViewById(R.id.app_code);

        app_code.setText(CommonUtils.getVersionInfo(MainActivity.this));
        username.setText(Preferences.getInstance().getFullName() +"\n"+ Preferences.getInstance().getContact());

        home_gv = findViewById(R.id.gv);
        sliderView = findViewById(R.id.imageSlider);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        samplePresenter = new SamplePresenter(this);
        getLocation();

        modules = new ArrayList<>();

        ModulesPojo mp = new ModulesPojo();
        mp.setId("1");
        mp.setName("Book Ambulance");
        mp.setLogo("book_ambulance");

        ModulesPojo mp2 = new ModulesPojo();
        mp2.setId("2");
        mp2.setName("Book Lab");
        mp2.setLogo("book_lab");

        ModulesPojo mp3 = new ModulesPojo();
        mp3.setId("3");
        mp3.setName("All Bookings");
        mp3.setLogo("visits");

        ModulesPojo mp4 = new ModulesPojo();
        mp4.setId("4");
        mp4.setName("Log Out");
        mp4.setLogo("logout");

        modules.add(mp);
        modules.add(mp2);
        modules.add(mp3);
        modules.add(mp4);



        adapter_modules = new HomeGridViewAdapter(this, (ArrayList<ModulesPojo>) modules);
        home_gv.setAdapter(adapter_modules);

        adapters = new SliderAdapter(MainActivity.this);
        sliderView.setSliderAdapter(adapters);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);

            }
        });

        if (AppStatus.getInstance(MainActivity.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.GET_AUTHENTICATION_TOKEN);
            object.setMethordName(Econstants.methordAuthentication);


            new GenericAsyncPostObject(
                    MainActivity.this,
                    MainActivity.this,
                    TaskType.GET_AUTHENTICATION_TOKEN).
                    execute(object);

        } else {
            CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
        }

        if (AppStatus.getInstance(MainActivity.this).isOnline()) {

            UploadObject object = new UploadObject();
            object.setUrl(Econstants.url);
            object.setTasktype(TaskType.DEFAULT_ADMIN_DIS);
            object.setMethordName(Econstants.methordDefadmindet);


            new GenericAsyncPostObject(
                    MainActivity.this,
                    MainActivity.this,
                    TaskType.DEFAULT_ADMIN_DIS).
                    execute(object);

        } else {
            CD.showDialog(MainActivity.this, "Please Connect to Internet and try again.");
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
                    Preferences.getInstance().loadPreferences(MainActivity.this);
                    Preferences.getInstance().transKey = String.valueOf(response.getTrans_key());
                    Preferences.getInstance().savePreferences(MainActivity.this);
                    System.out.println("Trans Key:- \t" + Preferences.getInstance().transKey);

                }else{
                    CD.showDialog(MainActivity.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(MainActivity.this, "Unable to get Data from server. Please try again.");
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

                    Preferences.getInstance().loadPreferences(MainActivity.this);

                    Preferences.getInstance().setAccessAdminId(dataObject.optString("access_admin_id"));
                    Preferences.getInstance().setCompanyId(dataObject.optString("company_id"));

                    Preferences.getInstance().savePreferences(MainActivity.this);
                    System.out.println("Access Admin ID :- \t" + Preferences.getInstance().getAccessAdminId());
                    System.out.println("Company ID :- \t" + Preferences.getInstance().getCompanyId());


                }else{
                    CD.showDialog(MainActivity.this, "Unrecognised Response from Server. Please try again.");
                }

            }else{
                CD.showDialog(MainActivity.this, "Unable to get Data from server. Please try again.");
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