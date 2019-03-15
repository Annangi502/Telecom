package com.sp.location;

import java.io.*;

public class MediaType implements Serializable
{
    private int mediatypeid;
    private String mediatypedesc;
    
    public MediaType() {
    }
    
    public MediaType(final int mediatypeid, final String mediatypedesc) {
        this.mediatypeid = mediatypeid;
        this.mediatypedesc = mediatypedesc;
    }
    
    public int getMediatypeid() {
        return this.mediatypeid;
    }
    
    public void setMediatypeid(final int mediatypeid) {
        this.mediatypeid = mediatypeid;
    }
    
    public String getMediatypedesc() {
        return this.mediatypedesc;
    }
    
    public void setMediatypedesc(final String mediatypedesc) {
        this.mediatypedesc = mediatypedesc;
    }
}