package Objects;

/**
 * Created by hp on 01/01/17.
 */

public class searchResult implements Comparable{
    private Driver driver;
    private schedule Schedule;
    //**************************************** Constructor(S) ▼▼▼▼▼
    public searchResult() {
    }

    public searchResult(Driver driver, schedule schedule) {
        this.driver = driver;
        Schedule = schedule;
    }


    //**************************************** setter and getter(S) ▼▼▼▼▼
    public schedule getSchedule() {
        return Schedule;
    }

    public void setSchedule(schedule schedule) {
        Schedule = schedule;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    @Override
    public int compareTo(Object o) {
        int returnVal = 0;
        searchResult search= (searchResult)o;
        if(getSchedule().getDayPrice()==search.getSchedule().getDayPrice())
            returnVal=0;
        else if(getSchedule().getDayPrice() < search.getSchedule().getDayPrice()){
            returnVal =  -1;
        }else   if(getSchedule().getDayPrice() >search.getSchedule().getDayPrice())
            returnVal =  1;
        return returnVal;
    }
}
