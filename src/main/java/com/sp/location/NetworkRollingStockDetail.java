package com.sp.location;

import java.io.Serializable;
import java.util.Date;

public class NetworkRollingStockDetail implements Serializable {
private int rolling_stock_id ;	
private String equipmentname;
private String model ;
private String serialno ;
private String total ;
private String location_name ;
private String make ;
private String amc ;
private String status;
private String remarks;
private String ponumber;
private Date supplydate;
private int networkequipid ;
private String supply_date ;
private String circledesciption ;
	
public NetworkRollingStockDetail(){
	
}

public NetworkRollingStockDetail(int rolling_stock_id,String equipmentname, String model, String serialno, String total,String remarks) {
	super();
	this.rolling_stock_id = rolling_stock_id ;
	this.equipmentname = equipmentname ;
	this.model = model ;
	this.serialno = serialno ;
	this.total = total ;
	this.remarks = remarks ;
}

public NetworkRollingStockDetail(String location_name,String equipmentname,String make, String model, String serialno, String amc,int networkequipid,String status,String ponumber,Date supplydate,String remarks) {
	super();
	this.location_name = location_name ;
	this.equipmentname = equipmentname ;
	this.model = model ;
	this.serialno = serialno ;
	this.make = make ;
	this.amc = amc ;
	this.networkequipid = networkequipid ;
	this.status = status ;
	this.remarks = remarks ;
	this.supplydate = supplydate ;
	this.ponumber = ponumber ;
	
}
public NetworkRollingStockDetail(String location_name,String equipmentname,String make, String model, String serialno, String amc,int networkequipid,String status,String ponumber,String supply_date,String remarks) {
	super();
	this.equipmentname = equipmentname ;
	this.model = model ;
	this.serialno = serialno ;
	this.make = make ;
	this.amc = amc ;
	this.networkequipid = networkequipid ;
	this.status = status ;
	this.remarks = remarks ;
	this.supply_date = supply_date ;
	this.ponumber = ponumber ;
	
	
}

public NetworkRollingStockDetail(String equipmentname,String make, String model, String serialno, String amc,String status,String ponumber,String supply_date,String remarks,int rolling_stock_id,String circledesciption) {
	super();
	
	this.equipmentname = equipmentname ;
	this.make = make ;
	this.model = model ;
	this.serialno = serialno ;
	this.amc = amc ;
	this.status = status ;
	this.ponumber = ponumber ;
	this.supply_date = supply_date ;
	this.remarks = remarks ;
	this.rolling_stock_id = rolling_stock_id ;
	this.circledesciption = circledesciption ;
	
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getEquipmentname() {
	return equipmentname;
}

public void setEquipmentname(String equipmentname) {
	this.equipmentname = equipmentname;
}

public String getModel() {
	return model;
}

public void setModel(String model) {
	this.model = model;
}

public String getSerialno() {
	return serialno;
}

public void setSerialno(String serialno) {
	this.serialno = serialno;
}

public String getTotal() {
	return total;
}

public void setTotal(String total) {
	this.total = total;
}

public int getRolling_stock_id() {
	return rolling_stock_id;
}

public void setRolling_stock_id(int rolling_stock_id) {
	this.rolling_stock_id = rolling_stock_id;
}

public String getLocation_name() {
	return location_name;
}

public void setLocation_name(String location_name) {
	this.location_name = location_name;
}

public String getMake() {
	return make;
}

public void setMake(String make) {
	this.make = make;
}

public String getAmc() {
	return amc;
}

public void setAmc(String amc) {
	this.amc = amc;
}


public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getPonumber() {
	return ponumber;
}

public void setPonumber(String ponumber) {
	this.ponumber = ponumber;
}

public Date getSupplydate() {
	return supplydate;
}

public void setSupplydate(Date supplydate) {
	this.supplydate = supplydate;
}

public int getNetworkequipid() {
	return networkequipid;
}

public void setNetworkequipid(int networkequipid) {
	this.networkequipid = networkequipid;
}

public String getSupply_date() {
	return supply_date;
}

public void setSupply_date(String supply_date) {
	this.supply_date = supply_date;
}

public String getCircledesciption() {
	return circledesciption;
}

public void setCircledesciption(String circledesciption) {
	this.circledesciption = circledesciption;
}



}
