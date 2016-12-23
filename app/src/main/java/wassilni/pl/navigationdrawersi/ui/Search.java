package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button request=(Button) findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DriverProfile.class);
                startActivity(i);
            }
        });

        Intent i=getIntent();
        String result=i.getStringExtra("json");
        //parseJSON();


    }

    public void parseJSON()
    {
        /*
        * create 3 objects, one for driver, one for schedule and one for car
        * then parse the data into array of 3 arrays, their indices corresponds to the car/driver/schedule arrays
        * then present these data to the user in some order
        *    this solution might not be the best, think of another solution!*/

 /*       try {

            JSONArray auser= null;
            JSONObject j = new JSONObject(s);
            auser=j.getJSONArray("result");
            JSONObject jsonObject=auser.getJSONObject(0);

            //String fullName = jsonObject.getString(FName)+" "+jsonObject.getString(LName);
            String JSON_ARRAY ="result";// the string must be the same as the name of the json object in the php file
            String p_id= "P_ID";// the string must be the same as the key name in the php file
            String FName = "P_F_Name";// the string must be the same as the key name in the php file
            String LName= "P_L_Name";// the string must be the same as the key name in the php file
            String phoneNum = "P_Phone";// the string must be the same as the key name in the php file
            String email= "P_Email";// the string must be the same as the key name in the php file
            String school= "school";// the string must be the same as the key name in the php file

            p.setID(jsonObject.getInt(p_id));
            p.setEmail(jsonObject.getString(email));
            //System.out.println("***************************************************"+jsonObject.getString(email));
            p.setFName(jsonObject.getString(FName));
            p.setLName(jsonObject.getString(LName));
            p.setPhone(jsonObject.getString(phoneNum));
            p.setSchool(jsonObject.getString(school));

            //System.out.println(d.Email+"\t'ffffffffffffffffffffffffffffffffffffffffffff");
            MyApp.passenger_from_DB= p;
            System.out.println(" ################ --1-- IN Parse Data class "+MyApp.passenger_from_DB.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }


    /*
    *
    *  this is how to parse values and then assign it to an array
    * public void parseJSON(String s)
    {
        String latlng;//POINT(24.844278 46.780666)
        String[] sp;
        LatLng pickup;

        try {

            JSONArray auser= null;
            JSONObject j = new JSONObject(s);
            auser=j.getJSONArray("result");
        for (int i = 0 ; i <auser.length() ; i++) {
            JSONObject jsonObject=auser.getJSONObject(i);
            //String fullName = jsonObject.getString(FName)+" "+jsonObject.getString(LName);
            String JSON_ARRAY = "result";// the string must be the same as the name of the json object in the php file
            String p_id = "P_ID";// the string must be the same as the key name in the php file
            String FName = "P_F_Name";
            String LName = "P_L_Name";
            String phone = "P_phone";
            String email = "P_email";
            String school = "school";

            String R_ID = "R_ID";
            String R_pickup = "R_pickup_loc";
            String R_dropoff = "R_dropoff_loc";

Passenger p=new Passenger();


            p.setID(jsonObject.getInt(p_id));
            System.out.println("***************************************************"+jsonObject.getString(email));
            p.setFName(jsonObject.getString(FName));
            p.setLName(jsonObject.getString(LName));
            p.setPhone(jsonObject.getString(phone));
            p.setEmail(jsonObject.getString(email));
            p.setSchool(jsonObject.getString(school));

            passengers.add(p);
            latlng = jsonObject.getString(R_pickup);
            latlng=latlng.substring(latlng.indexOf('(')+1, latlng.indexOf(')'));
            //System.out.println(latlng);
            sp=latlng.split("\\s+");
            pickup= new LatLng(Double.parseDouble(sp[0]),Double.parseDouble(sp[1]));
            pickupLocations.add(pickup);
            //Log.d("Driver", "=============================="+d.getEmail() );

        }//end for auser.length
            //System.out.println(d.Email+"\t'ffffffffffffffffffffffffffffffffffffffffffff");
            //MyApp.driver_from_DB= d;
            System.out.println(passengers.toString()+" LatLng : "+pickupLocations.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    *
    * */
}
