package com.sp.location;

import java.io.*;

public class NetworkVendorDetail implements Serializable
{
    private int vendorid;
    private String vendorname;
    private String bandwidth;
    private String servicetype;
    private String vninterface;
    private String circuitid;
    private String remark;
    private String mediatypedesc;
    
    public NetworkVendorDetail() {
    }
    
    public NetworkVendorDetail(final int vendorid, final String vendorname, final String bandwidth, final String servicetype, final String vninterface, final String circuitid, final String remark, final String mediatypedesc) {
        this.vendorid = vendorid;
        this.vendorname = vendorname;
        this.bandwidth = bandwidth;
        this.servicetype = servicetype;
        this.circuitid = circuitid;
        this.remark = remark;
        this.vninterface = vninterface;
        this.mediatypedesc = mediatypedesc;
    }
    
    public int getVendorid() {
        return this.vendorid;
    }
    
    public void setVendorid(final int vendorid) {
        this.vendorid = vendorid;
    }
    
    public String getVendorname() {
        return this.vendorname;
    }
    
    public void setVendorname(final String vendorname) {
        this.vendorname = vendorname;
    }
    
    public String getBandwidth() {
        return this.bandwidth;
    }
    
    public void setBandwidth(final String bandwidth) {
        this.bandwidth = bandwidth;
    }
    
    public String getServicetype() {
        return this.servicetype;
    }
    
    public void setServicetype(final String servicetype) {
        this.servicetype = servicetype;
    }
    
    public String getCircuitid() {
        return this.circuitid;
    }
    
    public void setCircuitid(final String circuitid) {
        this.circuitid = circuitid;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getVninterface() {
        return this.vninterface;
    }
    
    public void setVninterface(final String vninterface) {
        this.vninterface = vninterface;
    }
    
    public String getMediatypedesc() {
        return this.mediatypedesc;
    }
    
    public void setMediatypedesc(final String mediatypedesc) {
        this.mediatypedesc = mediatypedesc;
    }
}