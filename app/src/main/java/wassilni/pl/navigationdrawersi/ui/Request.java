package wassilni.pl.navigationdrawersi.ui;

/**
 * Created by haya on 12/11/2016.
 */

public class Request {

    private int reqNum;
    private int DriverN;
    private String dropOffL;
    private String dropOffT;
    private String confirm;


    public Request(int reqNum, int driverName, String dropOffL, String dropOffT, String confirm) {
        this.reqNum = reqNum;
        DriverN = driverName;
        this.dropOffL = dropOffL;
        this.dropOffT = dropOffT;
        this.confirm = confirm;
    }


    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public int getDriverN() {
        return DriverN;
    }

    public void setDriverN(int driverName) {
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
