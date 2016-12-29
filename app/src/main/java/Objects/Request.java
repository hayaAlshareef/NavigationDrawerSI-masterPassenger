package Objects;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Najla AlHazzani on 11/19/2016.
 */

public class Request {

    private Date StartingDate;
    private Date EndingDate;
    private Time time;
    private LatLng PickupLoc;// or LatLng
    private LatLng DropoffLoc;// or LatLng
    private String State;
    private String pickupAdd;
    private String dropoffAdd;



    //**************************************** Constructor(S) ▼▼▼▼▼
    public Request() {}//default Constructor.

    public Request(Date startingDate, Date endingDate, Time time, LatLng pickupLoc, LatLng dropoffLoc, String state) {
        StartingDate = startingDate;
        EndingDate = endingDate;
        this.time = time;
        PickupLoc = pickupLoc;
        DropoffLoc = dropoffLoc;
        State = state;
    }

    public Request(Date startingDate, Date endingDate, LatLng pickupLoc, Time time, LatLng dropoffLoc, String state, String pickupAdd, String dropoffAdd) {
        StartingDate = startingDate;
        EndingDate = endingDate;
        PickupLoc = pickupLoc;
        this.time = time;
        DropoffLoc = dropoffLoc;
        State = state;
        this.pickupAdd = pickupAdd;
        this.dropoffAdd = dropoffAdd;
    }

    //  **************************************** service method HERE ▼▼▼▼▼

    public Request viewRequest(int R_ID){
        // this method will .....
        return null;
    }

    public Request[] getRequests(int S_ID){
        //this method will retreive list/array of request based on scheduleID.
        return null;
    }

    public boolean cancelSubscription(Request r){
        //this method will take request and delete it from DB.
    return false;
    }

    public void setMeAbsent(int R_ID, Date d, Time t){
        //this method will take request id, date and time , and set the passenger absent.

    }

    public void ConfirmRequest(int R_ID){
        //this method will be used by driver to confirm the request and make change to DB.
    }

    //**************************************** SETTERS &&& GETTERS HERE ▼▼▼▼▼

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

    public Date getStartingDate() {
        return StartingDate;
    }

    public void setStartingDate(Date startingDate) {
        StartingDate = startingDate;
    }

    public Date getEndingDate() {
        return EndingDate;
    }

    public void setEndingDate(Date endingDate) {
        EndingDate = endingDate;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public LatLng getPickupLoc() {
        return PickupLoc;
    }

    public void setPickupLoc(LatLng pickupLoc) {
        PickupLoc = pickupLoc;
    }

    public LatLng getDropoffLoc() {
        return DropoffLoc;
    }

    public void setDropoffLoc(LatLng dropoffLoc) {
        DropoffLoc = dropoffLoc;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
