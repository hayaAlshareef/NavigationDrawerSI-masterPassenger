package Objects;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hp on 10/12/16.
 */

public class schedule {

    public int S_ID;
    public  String StartDate;
    public  String endDate;
    public  String time ;
    public LatLng pickup;
    public LatLng dropoff;
    public  int bookedSeat;
    public  int montPrice;
    public  int dayPrice;

    public schedule(){}

    public schedule(int s_ID, String startDate, String endDate, String time, LatLng pickup, LatLng dropoff, int bookedSeat, int montPrice, int dayPrice) {
        S_ID = s_ID;
        StartDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.bookedSeat = bookedSeat;
        this.montPrice = montPrice;
        this.dayPrice = dayPrice;
    }

    public int getS_ID() {
        return S_ID;
    }

    public void setS_ID(int s_ID) {
        S_ID = s_ID;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LatLng getPickup() {
        return pickup;
    }

    public void setPickup(LatLng pickup) {
        this.pickup = pickup;
    }

    public LatLng getDropoff() {
        return dropoff;
    }

    public void setDropoff(LatLng dropoff) {
        this.dropoff = dropoff;
    }

    public int getMontPrice() {
        return montPrice;
    }

    public void setMontPrice(int montPrice) {
        this.montPrice = montPrice;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public String toString (){


        return S_ID+" "+montPrice+" "+dayPrice;
    }
}
