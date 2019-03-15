package com.sp.location;

import java.io.*;

public class Division implements Serializable
{
    private int divisionid;
    private String divisiondesc;
    private String divisioncode;
    
    public Division() {
    }
    
    public Division(final int divisionid, final String divisiondesc, final String divisioncode) {
        this.divisionid = divisionid;
        this.divisiondesc = divisiondesc;
        this.divisioncode = divisioncode;
    }
    
    public int getDivisionid() {
        return this.divisionid;
    }
    
    public void setDivisionid(final int divisionid) {
        this.divisionid = divisionid;
    }
    
    public String getDivisiondesc() {
        return this.divisiondesc;
    }
    
    public void setDivisiondesc(final String divisiondesc) {
        this.divisiondesc = divisiondesc;
    }
    
    public String getDivisioncode() {
        return this.divisioncode;
    }
    
    public void setDivisioncode(final String divisioncode) {
        this.divisioncode = divisioncode;
    }
}