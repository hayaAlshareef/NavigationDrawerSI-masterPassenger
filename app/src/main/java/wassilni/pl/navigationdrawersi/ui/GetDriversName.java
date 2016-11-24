package wassilni.pl.navigationdrawersi.ui;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by haya on 11/23/2016.
 */

public class GetDriversName extends AppCompatActivity {

    private String DriverID;
    private String DriverName;


    public GetDriversName(String id,String name){

        DriverID=id;
        DriverName=name;

    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }
}
