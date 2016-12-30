package wassilni.pl.navigationdrawersi.ui;

import java.sql.Time;

/**
 * Created by haya on 12/11/2016.
 */


public class Request {

    private int reqNum;
    private String DriverN;
    private String startDate;
    private String endDate;
    private String dropOffL;
    private String dropOffT;
    private String confirm;
    private String time ;
    private String pickupAdd;
    private String dropoffAdd;
    private String pickupL;
    private String D_ID;



//**************************************** Constructor(S) ▼▼▼▼▼


    public Request(String startDate, String endDate, String pickupL ,String dropOffL, String time, String pickupAdd, String dropoffAdd) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dropOffL = dropOffL;
        this.pickupL = pickupL;
        this.time = time;
        this.pickupAdd = pickupAdd;
        this.dropoffAdd = dropoffAdd;
    }

    public Request(int reqNum, String driverName, String dropOffL, String dropOffT,String pickupAdd, String confirm) {
        this.reqNum = reqNum;
        DriverN = driverName;
        this.dropOffL = dropOffL;
        this.dropOffT = dropOffT;
        this.confirm = confirm;
        this.pickupAdd=pickupAdd;
    }

    public Request(int reqNum,String D_ID, String driverName, String dropOffL, String dropOffT,String pickupAdd, String confirm) {
        this.reqNum = reqNum;
        DriverN = driverName;
        this.dropOffL = dropOffL;
        this.dropOffT = dropOffT;
        this.confirm = confirm;
        this.pickupAdd=pickupAdd;
        this.D_ID=D_ID;
    }


    /**     getters and setters  **/

            public String getStartDate() {
                return startDate;
            }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPickupL() {
        return pickupL;
    }

    public void setPickupL(String pickupL) {
        this.pickupL = pickupL;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickupAdd() {
        return pickupAdd;
    }

    public void setPickupAdd(String pickupAdd) {
        this.pickupAdd = pickupAdd;
    }

    public String getDropoffAdd() {
        return dropoffAdd;
    }

    public void setDropoffAdd(String dropoffAdd) {
        this.dropoffAdd = dropoffAdd;
    }

    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public String getDriverN() {
        return DriverN;
    }

    public void setDriverN(String driverName) {
        DriverN = driverName;
    }

    public String getDropOffL() {
        return dropOffL;
    }

    public void setDropOffL(String dropOffL) {
        this.dropOffL = dropOffL;
    }

    public String getDropOffT() {
        return dropOffT;
    }

    public void setDropOffT(String dropOffT) {
        this.dropOffT = dropOffT;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getD_ID() {return D_ID; }

    public void setD_ID(String d_ID) {  D_ID = d_ID; }
}
