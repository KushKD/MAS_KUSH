package com.dit.hp.janki.utilities;

import android.util.Log;

import com.dit.hp.janki.Modal.LabPojo;
import com.dit.hp.janki.Modal.SuccessResponse;
import com.dit.hp.janki.Modal.TestDetailPojo;
import com.dit.hp.janki.Modal.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {

    public static SuccessResponse getSuccessResponse(String data) throws JSONException {

        JSONObject responseObject = new JSONObject(data);
        SuccessResponse sr = new SuccessResponse();
        sr.setError(responseObject.optBoolean("error"));
        sr.setMessage(responseObject.optString("message"));
        sr.setTrans_key(responseObject.optString("trans_key"));
        sr.setData(responseObject.optString("data"));
        sr.setOtp(responseObject.optString("otp"));
        sr.setOtp_message(responseObject.optString("otp_message"));

        return sr;
    }

    public static User getUserDetails(String data) throws JSONException {
        JSONArray responseArray = new JSONArray(data);
        JSONObject responseObject = responseArray.getJSONObject(0);

        User user = new User();
        user.setId(responseObject.optString("id"));
        user.setAccessAdminId(responseObject.optString("access_admin_id"));
        user.setAdminId(responseObject.optString("admin_id"));
        user.setCompanyId(responseObject.optString("company_id"));
        user.setLoginId(responseObject.optString("login_id"));
        user.setFullName(responseObject.optString("full_name"));
        user.setEmail(responseObject.optString("email"));
        user.setRole(responseObject.optString("role"));
        user.setContact(responseObject.optString("contact"));
        user.setGender(responseObject.optString("gender"));
        user.setAddress(responseObject.optString("address"));
        user.setState(responseObject.optString("state"));
        user.setCity(responseObject.optString("city"));
        user.setPinCode(responseObject.optString("pin_code"));
        user.setProfilePic(responseObject.optString("profile_pic"));
        user.setStatus(responseObject.optString("status"));
        user.setCreatedBy(responseObject.optString("created_by"));
        user.setCreatedOn(responseObject.optString("created_on"));
        user.setAadharImage(responseObject.optString("aadhar_image"));
        user.setOtherImage(responseObject.optString("other_image"));
        user.setRemark(responseObject.optString("remark"));
        user.setDlNo(responseObject.optString("dl_no"));
        user.setUserType(responseObject.optString("user_type"));
        user.setRoleName(responseObject.optString("role_name"));

        Log.e("Data", user.toString());
        return user;
    }

    public static List<LabPojo> getLabDetails(String data) throws JSONException {
        JSONArray responseArray = new JSONArray(data);
        List<LabPojo> labList = new ArrayList<>();

        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject responseObject = responseArray.getJSONObject(i);

            LabPojo lab = new LabPojo();
            lab.setId(responseObject.optString("id"));
            lab.setAccessAdminId(responseObject.optString("access_admin_id"));
            lab.setAdminId(responseObject.optString("admin_id"));
            lab.setCompanyId(responseObject.optString("company_id"));
            lab.setPathologyName(responseObject.optString("pathology_name"));
            lab.setAddress(responseObject.optString("address"));
            lab.setState(responseObject.optString("state"));
            lab.setCity(responseObject.optString("city"));
            lab.setPincode(responseObject.optString("pincode"));
            lab.setPhoneNo(responseObject.optString("phone_no"));
            lab.setEmail(responseObject.optString("email"));
            lab.setOpenTime(responseObject.optString("open_time"));
            lab.setCloseTime(responseObject.optString("close_time"));
            lab.setLatitude(responseObject.optString("latitude"));
            lab.setLongitude(responseObject.optString("longitude"));
            lab.setRemark(responseObject.optString("remark"));
            lab.setStatus(responseObject.optString("status"));
            lab.setStateName(responseObject.optString("state_name"));
            lab.setCityName(responseObject.optString("city_name"));

            Log.e("Data", lab.toString());
            labList.add(lab);
        }

        return labList;
    }

    public static List<TestDetailPojo> getTestDetails(String data) throws JSONException {
        JSONArray responseArray = new JSONArray(data);
        List<TestDetailPojo> testDetailList = new ArrayList<>();

        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject responseObject = responseArray.getJSONObject(i);

            TestDetailPojo testDetail = new TestDetailPojo();
            testDetail.setId(responseObject.optString("id"));
            testDetail.setAccessAdminId(responseObject.optString("access_admin_id"));
            testDetail.setAdminId(responseObject.optString("admin_id"));
            testDetail.setCompanyId(responseObject.optString("company_id"));
            testDetail.setLabId(responseObject.optString("lab_id"));
            testDetail.setTestId(responseObject.optString("test_id"));
            testDetail.setTestPrice(responseObject.optString("test_price"));
            testDetail.setTestCode(responseObject.optString("test_code"));
            testDetail.setTestName(responseObject.optString("test_name"));
            testDetail.setTestCharges(responseObject.optString("test_charges"));
            testDetail.setPathologyName(responseObject.optString("pathology_name"));

            testDetailList.add(testDetail);
        }

        return testDetailList;
    }
}
