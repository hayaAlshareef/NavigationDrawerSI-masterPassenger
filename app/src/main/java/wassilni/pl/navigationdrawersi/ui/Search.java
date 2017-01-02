package wassilni.pl.navigationdrawersi.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import Objects.Car;
import Objects.Driver;
import Objects.MyApp;
import Objects.schedule;
import Objects.searchResult;
import wassilni.pl.navigationdrawersi.R;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public ArrayList<Driver> drivers=new ArrayList<Driver>();
    public ArrayList<schedule> schedules=new ArrayList<schedule>();
    public ArrayList<searchResult> searchResults=new ArrayList<searchResult>();
    ListView listView;
    int ids[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        listView=(ListView)findViewById(R.id.listSchedule);
        Intent i=getIntent();
        String result=i.getStringExtra("json");
        parseJSON(result);

        Spinner spinnerSorting = (Spinner) findViewById(R.id.spinnerSort);
        spinnerSorting.setOnItemSelectedListener(this);

    }

    private void showDate()
    {
        final myList list=new myList(this,searchResults);

        listView.setAdapter(list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                // query and parseJSON, then go to mapActivity.

                arg0.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.gray_shadow));


                arg0.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.transparent));
                list.setBackground();
                schedule s=searchResults.get(position).getSchedule();
                Driver d = searchResults.get(position).getDriver();

                System.out.println("befor "+d.getCar().getYearOfManufacture());
                Intent i = new Intent(Search.this, DriverProfile.class);
                i.putExtra("DriverName", d.getFName()+" "+d.getLName());//json will be parsed in the map activity.
                i.putExtra("DriverPhone", d.getPhone());
                i.putExtra("DriverAge", d.getAge());
                i.putExtra("DriverNationality", d.getNationality());
                i.putExtra("DriverCompany",d.getCompany());
                i.putExtra("carType", d.getCar().getType());
                i.putExtra("carModel", d.getCar().getModel());
                i.putExtra("carCompany", d.getCar().getCompany());
                i.putExtra("carColor", d.getCar().getColor());
                i.putExtra("carYearOfManufacture", d.getCar().getYearOfManufacture()+"");
                i.putExtra("carCapacity", d.getCar().getCapacity()+"");
                i.putExtra("DayPrice", s.dayPrice+"");
                i.putExtra("MonthPrice", s.montPrice+"");
                i.putExtra("Female",d.getFemaleCompanion()+"");
                i.putExtra("tripTime",s.getTime());
                i.putExtra("D_ID",d.getID()+"");
                i.putExtra("S_ID",s.getS_ID()+"");
                i.putExtra("Plate",d.getCar().getPlate());


                startActivity(i);
                //this.finish();

            }
        });


    }

    public void parseJSON(String s) {
        /*
        * create 3 objects, one for driver, one for schedule and one for car
        * then parse the data into array of 3 arrays, their indices corresponds to the car/driver/schedule arrays
        * then present these data to the user in some order
        *    this solution might not be the best, think of another solution!*/


            String JSON_ARRAY = "result";
            String s_id = "S_ID";
            String sDate = "S_Start_date";
            String eDate = "S_End_date";
            String time = "S_time";
            String mPrice = "monthPrice";
            String dPrice = "dayPrice";
            String BookedSeat = "BookedSeat";
            String pickup = "pickup";
            String dropoff = "dropoff";

            String d_id = "D_ID";
            String FName = "D_F_Name";
            String LName = "D_L_Name";
            String phoneNum = "D_phone";
            String email = "D_Email";
            String company = "company";
            String license = "license";
            String nationality = "nationality";
            String female_companion = "female_companion";
            String id_iqama = "id_iqama";
            String age = "age";
            String rating = "rating";
            String confirmed = "confirmed";

            String Plate_num = "Plate_num";
            String C_company = "C_company";
            String type = "type";
            String model = "model";
            String color = "color";
            String capacity = "capacity";
            String yearOfmanufacture = "yearOfmanufacture";

        showDate();


                /*
    *
    *  this is how to parse values and then assign it to an array
    * public void parseJSON(String s)*/


try {
    JSONArray auser = null;
    JSONObject j = new JSONObject(s);
    auser = j.getJSONArray("result");
    if(auser.length()>0) {
        Driver d;
        schedule sched;
        Car car;

        for (int i = 0; i < auser.length(); i++) {
            JSONObject jsonObject = auser.getJSONObject(i);

            /** create schedule  object   **/
            sched = new schedule();
            sched.setS_ID(jsonObject.getInt(s_id));
            sched.setStartDate(jsonObject.getString(sDate));
            sched.setEndDate(jsonObject.getString(eDate));
            sched.setTime(jsonObject.getString(time));
            sched.setPickup(MyApp.getLatlng(jsonObject.getString(pickup)));
            sched.setDropoff(MyApp.getLatlng(jsonObject.getString(dropoff)));
            sched.setBookedSeat(jsonObject.getInt(BookedSeat));
            sched.setMontPrice(jsonObject.getInt(mPrice));
            sched.setDayPrice(jsonObject.getInt(dPrice));

            /** create car object   **/
            car = new Car();
            car.setPlate(jsonObject.getString(Plate_num));
            car.setType(jsonObject.getString(type));
            car.setModel(jsonObject.getString(model));
            car.setColor(jsonObject.getString(color));
            car.setCompany(jsonObject.getString(C_company));
            car.setCapacity(jsonObject.getInt(capacity));
            car.setYearOfManufacture(jsonObject.getInt(yearOfmanufacture));


            /**  create driver object  **/

            System.out.println();
            d = new Driver();
            d.setID(jsonObject.getInt(d_id));
            d.setEmail(jsonObject.getString(email));
            d.setFName(jsonObject.getString(FName));
            d.setLName(jsonObject.getString(LName));
            d.setPhone(jsonObject.getString(phoneNum));
            d.setCompany(jsonObject.getString(company));
            d.setLicense(jsonObject.getString(license));
            d.setNationality(jsonObject.getString(nationality));
            d.setFemaleCompanion(jsonObject.getString(female_companion).charAt(0));
            d.setID_Iqama(jsonObject.getString(id_iqama));
            d.setAge(jsonObject.getString(age));
            d.setRating(jsonObject.getDouble(rating));
            d.setConfirmed(jsonObject.getString(confirmed).charAt(0));
            d.setCar(car);
           // d.getSchedule().add(sched);
           // drivers.add(d);
            searchResult searchR = new searchResult(d,sched);
            searchResults.add(searchR);
           /* int D_id =jsonObject.getInt(d_id);
            boolean flag = true;
            for(int a=0;a<drivers.size();a++){
                if(drivers.get(a).getID()==D_id){

                    drivers.get(a).setCar(car);
                    drivers.get(a).getSchedule().add(sched);
                    flag=false;
                    break;

                }

            }*/

            //schedules.add(sched);
          /*  if(flag) {
                System.out.println();
                d = new Driver();
                d.setID(jsonObject.getInt(d_id));
                d.setEmail(jsonObject.getString(email));
                d.setFName(jsonObject.getString(FName));
                d.setLName(jsonObject.getString(LName));
                d.setPhone(jsonObject.getString(phoneNum));
                d.setCompany(jsonObject.getString(company));
                d.setLicense(jsonObject.getString(license));
                d.setNationality(jsonObject.getString(nationality));
                d.setFemaleCompanion(jsonObject.getString(female_companion).charAt(0));
                d.setID_Iqama(jsonObject.getString(id_iqama));
                d.setAge(jsonObject.getString(age));
                d.setRating(jsonObject.getDouble(rating));
                d.setConfirmed(jsonObject.getString(confirmed).charAt(0));
                d.setCar(car);
                d.getSchedule().add(sched);
                drivers.add(d);

            }*/

        }//end for auser.length
    }// if JSON EMPTY
    else Toast.makeText(getApplicationContext(),"لا توجد رحلة بهذه المواصفات", Toast.LENGTH_SHORT).show();
            }catch (JSONException e){e.printStackTrace();}

       // for (int i=0 ; i< drivers.size();i++)
          //  System.out.println(drivers.get(i).toString()+"\t"+schedules.get(i).getS_ID());
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        TextView myText = (TextView) view;
        if(spinner.getSelectedItemPosition()==1){//sort the result high price
          //  Toast.makeText(getApplicationContext(),"أعلى سعر ", Toast.LENGTH_SHORT).show();
            Collections.sort(searchResults, Collections.reverseOrder());
            showDate();


        }
        else if (spinner.getSelectedItemPosition()==2){//sort the result lower price
           // Toast.makeText(getApplicationContext(),"اقل سعر ", Toast.LENGTH_SHORT).show();

        Collections.sort(searchResults);
            showDate();}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class myList extends BaseAdapter {
        ArrayList<searchResult> ListSearchResults;
        Context context;
        searchResult tempSearchResult;
        schedule tempSchedule;
        Driver tempDriver;
        int sched_id, driver_id;
        TableRow firstR;
        TableRow thirdR;

        public myList(Context context, ArrayList<searchResult> ListitemsearchR) {
            this.context = context;

            ListSearchResults = ListitemsearchR;


        }
        public void setBackground(){

            firstR.setBackgroundColor(Color.TRANSPARENT);
            thirdR.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public int getCount() {
            return ListSearchResults.size();
        }

        @Override
        public Object getItem(int position) {
            return ListSearchResults.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            tempSearchResult = ListSearchResults.get(position);
            // for(int i=0 ;i<tempDriver.getSchedule().size();i++){
            // tempSchedule=tempDriver.getSchedule().get(i);
            tempSchedule = tempSearchResult.getSchedule();
            tempDriver = tempSearchResult.getDriver();
            LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(R.layout.search_result_layout, parent, false);
            TextView DriverName = (TextView) row.findViewById(R.id.DriverNameTV);
            TextView CarName = (TextView) row.findViewById(R.id.CarNameTV);
            TextView MonthPrice = (TextView) row.findViewById(R.id.MonthPriceTV);
            TextView DayPrice = (TextView) row.findViewById(R.id.DayPriceTV);
             firstR=(TableRow)row.findViewById(R.id.fistRow);
             thirdR=(TableRow)row.findViewById(R.id.thirdRow);
            ///tempSchedule=itemsSchedule.get(position);

            sched_id = tempSchedule.getS_ID();
            driver_id = tempDriver.getID();

            //  ids[position]=temp.getS_ID();//to take the id for the schedule


            DriverName.setText(tempDriver.getFName() + " " + tempDriver.getLName());
            CarName.setText(tempDriver.getCar().getCompany() + " " + tempDriver.getCar().getModel());
            MonthPrice.setText(tempSchedule.getMontPrice() + "");
            DayPrice.setText(tempSchedule.getDayPrice() + "");

            return row;

    }

    }



}



