package com.dit.hp.janki.Modal;

import com.dit.hp.janki.enums.TaskType;


import java.io.Serializable;

public class UploadObject implements Serializable {

    private String url;

    private TaskType tasktype;
    private String methordName;
    private String param;
    private String param2;
    private String param3;
    private String param4;
    private String param5;

    private String param6;
    private String param7;
    private String param8;
    private String param9;
    private String param10;
    private String param11;
    private String imagePath;
    private String masterData;
    private Boolean status;
    private String masterName;
    private String API_NAME;
    private String parentId;
    private String secondaryParentId;

    public String getParam11() {
        return param11;
    }

    public void setParam11(String param11) {
        this.param11 = param11;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }

    public String getParam7() {
        return param7;
    }

    public void setParam7(String param7) {
        this.param7 = param7;
    }

    public String getParam8() {
        return param8;
    }

    public void setParam8(String param8) {
        this.param8 = param8;
    }

    public String getParam9() {
        return param9;
    }

    public void setParam9(String param9) {
        this.param9 = param9;
    }

    public String getParam10() {
        return param10;
    }

    public void setParam10(String param10) {
        this.param10 = param10;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaskType getTasktype() {
        return tasktype;
    }

    public void setTasktype(TaskType tasktype) {
        this.tasktype = tasktype;
    }

    public String getMethordName() {
        return methordName;
    }

    public void setMethordName(String methordName) {
        this.methordName = methordName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMasterData() {
        return masterData;
    }

    public void setMasterData(String masterData) {
        this.masterData = masterData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getAPI_NAME() {
        return API_NAME;
    }

    public void setAPI_NAME(String API_NAME) {
        this.API_NAME = API_NAME;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSecondaryParentId() {
        return secondaryParentId;
    }

    public void setSecondaryParentId(String secondaryParentId) {
        this.secondaryParentId = secondaryParentId;
    }

    @Override
    public String toString() {
        return "UploadObject{" +
                "url='" + url + '\'' +
                ", tasktype=" + tasktype +
                ", methordName='" + methordName + '\'' +
                ", param='" + param + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                ", param4='" + param4 + '\'' +
                ", param5='" + param5 + '\'' +
                ", param6='" + param6 + '\'' +
                ", param7='" + param7 + '\'' +
                ", param8='" + param8 + '\'' +
                ", param9='" + param9 + '\'' +
                ", param10='" + param10 + '\'' +
                ", param11='" + param11 + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", masterData='" + masterData + '\'' +
                ", status=" + status +
                ", masterName='" + masterName + '\'' +
                ", API_NAME='" + API_NAME + '\'' +
                ", parentId='" + parentId + '\'' +
                ", secondaryParentId='" + secondaryParentId + '\'' +
                '}';
    }
}
