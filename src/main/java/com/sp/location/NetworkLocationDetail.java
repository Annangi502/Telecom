package com.sp.location;

import java.io.*;

public class NetworkLocationDetail implements Serializable
{
    private String spcircuitid;
    private int projecttypeid;
    private String projecttypedescription;
    private int noofpointsavailable;
    private String installationdate;
    private String dateofconnected;
    private String officecontactno;
    private String officeaddress;
    private String locationcontactperson;
    private String loactioncontactno;
    private String remark;
    private String locationname;
    private String phase;
    private String spciruitcode;
    private String circledesc;
    private String divisiondesc;
    private String subdivisiondesc;
    private String sectiondesc;
    private String vendorname;
    private String bandwidth;
    private int ismergelocation;
    private String mergelocationdesc;
    private String latitude;
    private String longitude;
    public NetworkLocationDetail(String spcircuitid, int projecttypeid, String projecttypedescription,
			int noofpointsavailable, String installationdate, String dateofconnected, String officecontactno,
			String officeaddress, String locationcontactperson, String loactioncontactno, String remark,
			String locationname, String phase, String spciruitcode, String circledesc, String divisiondesc,
			String subdivisiondesc, String sectiondesc, int ismergelocation,
			String mergelocationdesc, String latitude, String longitude) {
		super();
		this.spcircuitid = spcircuitid;
		this.projecttypeid = projecttypeid;
		this.projecttypedescription = projecttypedescription;
		this.noofpointsavailable = noofpointsavailable;
		this.installationdate = installationdate;
		this.dateofconnected = dateofconnected;
		this.officecontactno = officecontactno;
		this.officeaddress = officeaddress;
		this.locationcontactperson = locationcontactperson;
		this.loactioncontactno = loactioncontactno;
		this.remark = remark;
		this.locationname = locationname;
		this.phase = phase;
		this.spciruitcode = spciruitcode;
		this.circledesc = circledesc;
		this.divisiondesc = divisiondesc;
		this.subdivisiondesc = subdivisiondesc;
		this.sectiondesc = sectiondesc;
		this.ismergelocation = ismergelocation;
		this.mergelocationdesc = mergelocationdesc;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public NetworkLocationDetail(String spcircuitid, String spciruitcode,String circledesc, String divisiondesc,
			String subdivisiondesc, String sectiondesc,int projecttypeid, String projecttypedescription,String townname, String phase)
    {
    	this.spcircuitid = spcircuitid;
		this.spciruitcode = spciruitcode;
		this.circledesc = circledesc;
		this.divisiondesc = divisiondesc;
		this.subdivisiondesc = subdivisiondesc;
		this.sectiondesc = sectiondesc;
		this.projecttypeid = projecttypeid;
		this.projecttypedescription = projecttypedescription;
    	this.locationname = townname;
		this.phase = phase;
    }
	public NetworkLocationDetail(String spcircuitid,String spciruitcode, int projecttypeid, String projecttypedescription, String locationname,
			String vendorname, String bandwidth,String circledesc, String divisiondesc,
			String subdivisiondesc, String sectiondesc) {
		super();
		this.spcircuitid = spcircuitid;
		this.spciruitcode = spciruitcode;
		this.projecttypeid = projecttypeid;
		this.projecttypedescription = projecttypedescription;
		this.locationname = locationname;
		this.vendorname = vendorname;
		this.bandwidth = bandwidth;
		this.circledesc = circledesc;
		this.divisiondesc = divisiondesc;
		this.subdivisiondesc = subdivisiondesc;
		this.sectiondesc = sectiondesc;
	}

	public NetworkLocationDetail() {
    }
    
    public NetworkLocationDetail(final String spcircuitid, final int projecttypeid, final String projecttypedescription, final int noofpointsavailable, final String installationdate, final String dateofconnected,final String officecontactno, final String officeaddress, final String locationcontactperson, final String loactioncontactno, final String remark, final String townname, final String phase, final String spciruitcode, final String circledesc, final String divisiondesc, final String subdivisiondesc, final String sectiondesc) {
        this.spcircuitid = spcircuitid;
        this.projecttypeid = projecttypeid;
        this.projecttypedescription = projecttypedescription;
        this.noofpointsavailable = noofpointsavailable;
        this.installationdate = installationdate;
        this.dateofconnected = dateofconnected;
        this.officecontactno = officecontactno;
        this.officeaddress = officeaddress;
        this.locationcontactperson = locationcontactperson;
        this.loactioncontactno = loactioncontactno;
        this.remark = remark;
        this.locationname = townname;
        this.phase = phase;
        this.spciruitcode = spciruitcode;
        this.circledesc = circledesc;
        this.divisiondesc = divisiondesc;
        this.subdivisiondesc = subdivisiondesc;
        this.sectiondesc = sectiondesc;
    }
    
    public String getSpcircuitid() {
        return this.spcircuitid;
    }
    
    public void setSpcircuitid(final String spcircuitid) {
        this.spcircuitid = spcircuitid;
    }
    
    public int getProjecttypeid() {
        return this.projecttypeid;
    }
    
    public void setProjecttypeid(final int projecttypeid) {
        this.projecttypeid = projecttypeid;
    }
    
    public String getProjecttypedescription() {
        return this.projecttypedescription;
    }
    
    public void setProjecttypedescription(final String projecttypedescription) {
        this.projecttypedescription = projecttypedescription;
    }
    
    public int getNoofpointsavailable() {
        return this.noofpointsavailable;
    }
    
    public void setNoofpointsavailable(final int noofpointsavailable) {
        this.noofpointsavailable = noofpointsavailable;
    }
    
    public String getInstallationdate() {
        return this.installationdate;
    }
    
    public void setInstallationdate(final String installationdate) {
        this.installationdate = installationdate;
    }
    
    public String getDateofconnected() {
        return this.dateofconnected;
    }
    
    public void setDateofconnected(final String dateofconnected) {
        this.dateofconnected = dateofconnected;
    }
    
    public String getOfficecontactno() {
        return this.officecontactno;
    }
    
    public void setOfficecontactno(final String officecontactno) {
        this.officecontactno = officecontactno;
    }
    
    public String getOfficeaddress() {
        return this.officeaddress;
    }
    
    public void setOfficeaddress(final String officeaddress) {
        this.officeaddress = officeaddress;
    }
    
    public String getLocationcontactperson() {
        return this.locationcontactperson;
    }
    
    public void setLocationcontactperson(final String locationcontactperson) {
        this.locationcontactperson = locationcontactperson;
    }
    
    public String getLoactioncontactno() {
        return this.loactioncontactno;
    }
    
    public void setLoactioncontactno(final String loactioncontactno) {
        this.loactioncontactno = loactioncontactno;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getTownname() {
        return this.locationname;
    }
    
    public void setTownname(final String townname) {
        this.locationname = townname;
    }
    
    public String getPhase() {
        return this.phase;
    }
    
    public void setPhase(final String phase) {
        this.phase = phase;
    }
    
    public String getSpciruitcode() {
        return this.spciruitcode;
    }
    
    public void setSpciruitcode(final String spciruitcode) {
        this.spciruitcode = spciruitcode;
    }
    
    public String getCircledesc() {
        return this.circledesc;
    }
    
    public void setCircledesc(final String circledesc) {
        this.circledesc = circledesc;
    }
    
    public String getDivisiondesc() {
        return this.divisiondesc;
    }
    
    public void setDivisiondesc(final String divisiondesc) {
        this.divisiondesc = divisiondesc;
    }
    
    public String getSubdivisiondesc() {
        return this.subdivisiondesc;
    }
    
    public void setSubdivisiondesc(final String subdivisiondesc) {
        this.subdivisiondesc = subdivisiondesc;
    }
    
    public String getSectiondesc() {
        return this.sectiondesc;
    }
    
    public void setSectiondesc(final String sectiondesc) {
        this.sectiondesc = sectiondesc;
    }
    public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public int getIsmergelocation() {
		return ismergelocation;
	}
	public void setIsmergelocation(int ismergelocation) {
		this.ismergelocation = ismergelocation;
	}
	public String getMergelocationdesc() {
		return mergelocationdesc;
	}
	public void setMergelocationdesc(String mergelocationdesc) {
		this.mergelocationdesc = mergelocationdesc;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}