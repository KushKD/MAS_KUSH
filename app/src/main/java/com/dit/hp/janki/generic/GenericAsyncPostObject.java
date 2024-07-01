package com.dit.hp.janki.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.Modal.UploadObject;
import com.dit.hp.janki.enums.TaskType;
import com.dit.hp.janki.interfaces.AsyncTaskListenerObject;
import com.dit.hp.janki.network.HttpManager;

import org.json.JSONException;

public class GenericAsyncPostObject extends AsyncTask<UploadObject,Void , ResponsePojoGet> {

    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObject taskListener_;
    TaskType taskType;
    private ProgressDialog mProgressDialog;

    public GenericAsyncPostObject(Context context, AsyncTaskListenerObject taskListener, TaskType taskType) {
        this.context = context;
        this.taskListener_ = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected ResponsePojoGet doInBackground(UploadObject... uploadObjects) {
        UploadObject data = null;
        data = uploadObjects[0];
        HttpManager http_manager = null;
        ResponsePojoGet Data_From_Server = null;
        boolean save = false;

        try {
            http_manager = new HttpManager();

            if (TaskType.GET_AUTHENTICATION_TOKEN.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }
            else if (TaskType.VERIFY_CONTACT.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_VERIFYCONTACT(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }
            else if (TaskType.VERIFY_OTP.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_VerifyOTP(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            } else if (TaskType.SIGN_UP.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_SIGNUP(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }else if (TaskType.DEFAULT_ADMIN_DIS.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_DEFAULTADMIN(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }else if (TaskType.AMBULANCE_BOOKING.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_AMBULANCE_BOOKING(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }else if (TaskType.LAB_BOOKING.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_LAB_BOOKING(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }


            else if (TaskType.ALL_LABS.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_ALLLABS(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }else if (TaskType.ALL_LAB_TESTS.toString().equalsIgnoreCase(data.getTasktype().toString())) {
                Data_From_Server = http_manager.PostData_ALLLABTESTS(data);
                Log.e("Data hhghsds", Data_From_Server.toString());
                return Data_From_Server;
            }






        } catch (Exception e) {
            return Data_From_Server;
        }


        return Data_From_Server;
    }

    @Override
    protected void onPostExecute(ResponsePojoGet result) {
        super.onPostExecute(result);

        try {
            taskListener_.onTaskCompleted(result, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();


    }
}