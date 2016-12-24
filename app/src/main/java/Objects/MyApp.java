package Objects;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.google.android.gms.maps.model.LatLng;

import java.net.InetAddress;

import Objects.Car;
import Objects.Driver;

/**
 * Created by Najla AlHazzani on 11/24/2016.
 */

public class MyApp extends Application {

    /*public static int D_ID;
    public static Driver driver_from_session = null;// this will be retreived from XML.
    public static Driver driver_from_DB = null;// this will be used as reference for GetJSON class in Driver.
    public static Car car;
*/
    public static Passenger passenger_from_session = null;// this will be retreived from XML.
    public static Passenger passenger_from_DB = null;// this will be used as reference for GetJSON class in passenger.
    // Driver information will be assigned to an object of Driver class from session.

    public MyApp(){}//default constructor
//This method is called to check that user's device is connected to the internet, returns true if connected, false otherwise
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public static LatLng getLatlng(String point)
    {
        String [] sp;
        LatLng result;
        String latlng;
        latlng = point;
        latlng=latlng.substring(latlng.indexOf('(')+1, latlng.indexOf(')'));
        //System.out.println(latlng);
        sp=latlng.split("\\s+");
        result= new LatLng(Double.parseDouble(sp[0]),Double.parseDouble(sp[1]));
        return result;
    }
}
