package com.dit.hp.janki.Modal;

import java.io.Serializable;

public class TestDetailPojo implements Serializable {

    private String id;
    private String accessAdminId;
    private String adminId;
    private String companyId;
    private String labId;
    private String testId;
    private String testPrice;
    private String testCode;
    private String testName;
    private String testCharges;
    private String pathologyName;

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

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(String testPrice) {
        this.testPrice = testPrice;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCharges() {
        return testCharges;
    }

    public void setTestCharges(String testCharges) {
        this.testCharges = testCharges;
    }

    public String getPathologyName() {
        return pathologyName;
    }

    public void setPathologyName(String pathologyName) {
        this.pathologyName = pathologyName;
    }

    @Override
    public String toString() {
        return testName +" ("+ testPrice + " /- INR)";
    }
}
