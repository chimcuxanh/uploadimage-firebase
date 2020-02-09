package com.example.bua1sautet;

public class upload {
    private String mname;
    private  String mImageUrl;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public upload(String mname, String mImageUrl) {
        if(mname.trim().equals("")){
            mname = "no name";

        }
        this.mname = mname;
        this.mImageUrl = mImageUrl;
    }

    public upload() {
    }
}
