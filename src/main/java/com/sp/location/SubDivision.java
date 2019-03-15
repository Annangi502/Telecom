package com.sp.location;

import java.io.*;

public class SubDivision implements Serializable
{
    private int subdivisionid;
    private String subdivisiondesc;
    private String subdivisioncode;
    
    public SubDivision() {
    }
    
    public SubDivision(final int subdivisionid, final String subdivisiondesc, final String subdivisioncode) {
        this.subdivisionid = subdivisionid;
        this.subdivisiondesc = subdivisiondesc;
        this.subdivisioncode = subdivisioncode;
    }
    
    public int getSubdivisionid() {
        return this.subdivisionid;
    }
    
    public void setSubdivisionid(final int subdivisionid) {
        this.subdivisionid = subdivisionid;
    }
    
    public String getSubdivisiondesc() {
        return this.subdivisiondesc;
    }
    
    public void setSubdivisiondesc(final String subdivisiondesc) {
        this.subdivisiondesc = subdivisiondesc;
    }
    
    public String getSubdivisioncode() {
        return this.subdivisioncode;
    }
    
    public void setSubdivisioncode(final String subdivisioncode) {
        this.subdivisioncode = subdivisioncode;
    }
}