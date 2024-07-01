package com.dit.hp.janki.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {

    private static Preferences instance;
    private String preferenceName = "com.dit.himachal.ulbSurvey";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private String KEY_ID = "id";
    private String KEY_ACCESS_ADMIN_ID = "access_admin_id";
    private String KEY_ADMIN_ID = "admin_id";
    private String KEY_COMPANY_ID = "company_id";
    private String KEY_LOGIN_ID = "login_id";
    private String KEY_FULL_NAME = "full_name";
    private String KEY_EMAIL = "email";
    private String KEY_ROLE = "role";
    private String KEY_CONTACT = "contact";
    private String KEY_GENDER = "gender";
    private String KEY_ADDRESS = "address";
    private String KEY_STATE = "state";
    private String KEY_CITY = "city";
    private String KEY_PIN_CODE = "pin_code";
    private String KEY_PROFILE_PIC = "profile_pic";
    private String KEY_STATUS = "status";
    private String KEY_CREATED_BY = "created_by";
    private String KEY_CREATED_ON = "created_on";
    private String KEY_AADHAR_IMAGE = "aadhar_image";
    private String KEY_OTHER_IMAGE = "other_image";
    private String KEY_REMARK = "remark";
    private String KEY_DL_NO = "dl_no";
    private String KEY_USER_TYPE = "user_type";
    private String KEY_ROLE_NAME = "role_name";

    private String KEY_NAME_APP_TYPE = "appType";
    private String KEY_IS_LOGED_IN = "loggedIn";
    private String KEY_TRANS_KEY = "TRANS_KEY";

    public boolean isLoggedIn;

    public String id;
    public String accessAdminId;
    public String adminId;
    public String companyId;
    public String loginId;
    public String fullName;
    public String email;
    public String role;
    public String contact;
    public String gender;
    public String address;
    public String state;
    public String city;
    public String pinCode;
    public String profilePic;
    public String status;
    public String createdBy;
    public String createdOn;
    public String aadharImage;
    public String otherImage;
    public String remark;
    public String dlNo;
    public String userType;
    public String roleName;
    public String appType_;
    public String transKey;

    private Preferences() {
    }

    public synchronized static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();
        return instance;
    }

    public void loadPreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);

        id = preferences.getString(KEY_ID, "");
        accessAdminId = preferences.getString(KEY_ACCESS_ADMIN_ID, "");
        adminId = preferences.getString(KEY_ADMIN_ID, "");
        companyId = preferences.getString(KEY_COMPANY_ID, "");
        loginId = preferences.getString(KEY_LOGIN_ID, "");
        fullName = preferences.getString(KEY_FULL_NAME, "");
        email = preferences.getString(KEY_EMAIL, "");
        role = preferences.getString(KEY_ROLE, "");
        contact = preferences.getString(KEY_CONTACT, "");
        gender = preferences.getString(KEY_GENDER, "");
        address = preferences.getString(KEY_ADDRESS, "");
        state = preferences.getString(KEY_STATE, "");
        city = preferences.getString(KEY_CITY, "");
        pinCode = preferences.getString(KEY_PIN_CODE, "");
        profilePic = preferences.getString(KEY_PROFILE_PIC, "");
        status = preferences.getString(KEY_STATUS, "");
        createdBy = preferences.getString(KEY_CREATED_BY, "");
        createdOn = preferences.getString(KEY_CREATED_ON, "");
        aadharImage = preferences.getString(KEY_AADHAR_IMAGE, "");
        otherImage = preferences.getString(KEY_OTHER_IMAGE, "");
        remark = preferences.getString(KEY_REMARK, "");
        dlNo = preferences.getString(KEY_DL_NO, "");
        userType = preferences.getString(KEY_USER_TYPE, "");
        roleName = preferences.getString(KEY_ROLE_NAME, "");

        appType_ = preferences.getString(KEY_NAME_APP_TYPE, "");
        isLoggedIn = preferences.getBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        transKey = preferences.getString(KEY_TRANS_KEY, "");
    }

    public void savePreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString(KEY_ID, id);
        editor.putString(KEY_ACCESS_ADMIN_ID, accessAdminId);
        editor.putString(KEY_ADMIN_ID, adminId);
        editor.putString(KEY_COMPANY_ID, companyId);
        editor.putString(KEY_LOGIN_ID, loginId);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_CONTACT, contact);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_STATE, state);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_PIN_CODE, pinCode);
        editor.putString(KEY_PROFILE_PIC, profilePic);
        editor.putString(KEY_STATUS, status);
        editor.putString(KEY_CREATED_BY, createdBy);
        editor.putString(KEY_CREATED_ON, createdOn);
        editor.putString(KEY_AADHAR_IMAGE, aadharImage);
        editor.putString(KEY_OTHER_IMAGE, otherImage);
        editor.putString(KEY_REMARK, remark);
        editor.putString(KEY_DL_NO, dlNo);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_ROLE_NAME, roleName);

        editor.putString(KEY_NAME_APP_TYPE, appType_);
        editor.putBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        editor.putString(KEY_TRANS_KEY, transKey);

        editor.commit();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessAdminId() {
        return accessAdminId;
    }

    public void setAccessAdminId(String accessAdminId) {
        this.accessAdminId = accessAdminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getAadharImage() {
        return aadharImage;
    }

    public void setAadharImage(String aadharImage) {
        this.aadharImage = aadharImage;
    }

    public String getOtherImage() {
        return otherImage;
    }

    public void setOtherImage(String otherImage) {
        this.otherImage = otherImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDlNo() {
        return dlNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAppType_() {
        return appType_;
    }

    public void setAppType_(String appType_) {
        this.appType_ = appType_;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getTransKey() {
        return transKey;
    }

    public void setTransKey(String transKey) {
        this.transKey = transKey;
    }
}
