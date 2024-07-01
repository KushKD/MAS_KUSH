package com.dit.hp.janki.Modal;

import java.io.Serializable;

public class SuccessResponse implements Serializable {

    private Boolean error;
    private String message;
    private String data;
    private String otp;
    private String otp_message;
    private String trans_key;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp_message() {
        return otp_message;
    }

    public void setOtp_message(String otp_message) {
        this.otp_message = otp_message;
    }

    public String getTrans_key() {
        return trans_key;
    }

    public void setTrans_key(String trans_key) {
        this.trans_key = trans_key;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", otp='" + otp + '\'' +
                ", otp_message='" + otp_message + '\'' +
                ", trans_key='" + trans_key + '\'' +
                '}';
    }
}
