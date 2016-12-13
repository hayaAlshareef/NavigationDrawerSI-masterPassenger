package wassilni.pl.navigationdrawersi.ui;

/**
 * Created by haya on 12/11/2016.
 */

public class Request {

    private String reqNum;
    private String DriverN;
    private String dropOffL;
    private String dropOffT;
    private String confirm;


    public Request(String reqNum, String driverName, String dropOffL, String dropOffT, String confirm) {
        this.reqNum = reqNum;
        DriverN = driverName;
        this.dropOffL = dropOffL;
        this.dropOffT = dropOffT;
        this.confirm = confirm;
    }


    public String getReqNum() {
        return reqNum;
    }

    public void setReqNum(String reqNum) {
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
}
