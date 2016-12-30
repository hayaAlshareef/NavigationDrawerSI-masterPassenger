package wassilni.pl.navigationdrawersi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import Objects.*;
import wassilni.pl.navigationdrawersi.R;

import static android.provider.MediaStore.Audio.AudioColumns.TRACK;

public class DriverProfile extends AppCompatActivity {


    TextView TV_Name, TV_phoneNum,TV_model, TV_age, TV_nationality, TV_companyNam,
            TV_carColor, TV_carType, TV_carComp, TV_plateNum, TV_capacity,
            TV_manufactured, TV_female,TV_tripTime,TV_dayPrice,TV_monthPrice,TV_NumberOfRater;
    String D_ID,S_ID,P_ID;
    Intent in ;
    String url_getRating ="http://wassilni.com/db/getRating.php";
    String sJson;
    private static final String JSON_ARRAY ="result";// the string must be the same as the name of the json object in the php file
    private static final String d_ID="D_ID";
    private static final String Rating="Ratting";
    private static final String numberRater="numberOfRetter";
    RatingBar ratingBar;
    private JSONArray rating = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        in = getIntent();
        TV_Name=(TextView)findViewById(R.id.driveNameET);
        TV_phoneNum=(TextView)findViewById(R.id.driverNumET);
        TV_age=(TextView)findViewById(R.id.diverAgeET);
        TV_nationality=(TextView)findViewById(R.id.nationalityET);
        TV_companyNam=(TextView)findViewById(R.id.companyName);
        TV_carType=(TextView)findViewById(R.id.carType);
        TV_carComp =(TextView)findViewById(R.id.carCompanyET);
        TV_plateNum=(TextView)findViewById(R.id.PlateNumET);
        TV_capacity=(TextView)findViewById(R.id.capacityTV);
        TV_manufactured=(TextView)findViewById(R.id.manufacturedTV);
        TV_female=(TextView)findViewById(R.id.femaleTV);
        TV_model=(TextView)findViewById(R.id.modelNum);
        TV_carColor=(TextView)findViewById(R.id.carColorET);
        TV_tripTime=(TextView)findViewById(R.id.tripTimeET);
        TV_dayPrice=(TextView)findViewById(R.id.dayPriceET);
        TV_monthPrice=(TextView)findViewById(R.id.monthPriceET);
        Button request=(Button) findViewById(R.id.request);
        P_ID= MyApp.passenger_from_session.getID()+"";
        TV_NumberOfRater=(TextView)findViewById(R.id.numberOfRaeter);
        ratingBar= (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setClickable(false);
        showData();
        S_ID=in.getStringExtra("S_ID");
        D_ID=in.getStringExtra("D_ID");
        GetJSON gj = new GetJSON();
        gj.execute(url_getRating);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String picL,dropL,time,startD,endD,pickAdd,dropAdd;
                FragmentTwo fragmentTwo=new FragmentTwo();
                Request r =fragmentTwo.getRequest();

                picL=FragmentTwo.pLoc;
                dropL=FragmentTwo.dLoc;
                time=FragmentTwo.time;
                startD=FragmentTwo.sDate;
                endD=FragmentTwo.eDate;
                pickAdd=FragmentTwo.pAdd;
                dropAdd=FragmentTwo.dAdd;
                String method="addReq";
                String res= "";
                backgroundTask bc=new backgroundTask(getApplication());
                try {

                    res= bc.execute(method,S_ID,P_ID,picL,dropL,time,startD,endD,pickAdd,dropAdd,D_ID).get();

                    //if()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
              /*  Intent i=new Intent(getApplicationContext(),DriverProfile.class);
                startActivity(i);*/

            }
        });





    }


    private void showData(){
                /*
        * Since session already retrieved from login class,
        * no need to retrieve the data from the DB, instead get it from session.
        * using the global variable MyApp.driver_form_session
        * */

        String capacity =(String) in.getStringExtra("carCapacity");
        TV_Name.setText( in.getStringExtra("DriverName"));
        TV_phoneNum.setText( in.getStringExtra("DriverPhone"));
        TV_age.setText(in.getStringExtra("DriverAge"));
        TV_nationality.setText(in.getStringExtra("DriverNationality"));

        TV_companyNam.setText(in.getStringExtra("DriverCompany"));

        TV_carType.setText(in.getStringExtra("carType"));
        TV_model.setText(in.getStringExtra("carModel"));
        TV_carColor.setText(in.getStringExtra("carColor"));
        TV_carComp.setText(in.getStringExtra("carCompany"));
       // TV_plateNum.setText(MyApp.driver_from_session.getCar().getPlate());
        TV_manufactured.setText(in.getStringExtra("carYearOfManufacture"));
        TV_capacity.setText(capacity);
        if(in.getStringExtra("Female").equalsIgnoreCase("N"))
        TV_female.setText("لا يوجد");
        else
        {
            TV_female.setText(" يوجد");
        }
        TV_tripTime.setText(in.getStringExtra("tripTime"));
        TV_dayPrice.setText(in.getStringExtra("DayPrice"));
        TV_monthPrice.setText(in.getStringExtra("MonthPrice"));
        TV_plateNum.setText(in.getStringExtra("Plate"));
        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/




    }

    private void extractJSONRatting(){
        try {
            JSONObject jsonObject = new JSONObject(sJson);
            rating = jsonObject.getJSONArray(JSON_ARRAY);

            setRatingValue();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setRatingValue() {


        Float RatingV;
        JSONObject jsonObject;

        for (int i = 0; i < rating.length(); i++) {
            try {
                jsonObject = rating.getJSONObject(0);


                RatingV = Float.parseFloat(jsonObject.getString(Rating));


                ratingBar.setRating(RatingV);
                TV_NumberOfRater.setText(TV_NumberOfRater.getText() + jsonObject.getString(numberRater));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetJSON extends AsyncTask<String ,Void,String> {
        ProgressDialog loading;
        String method;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         loading = ProgressDialog.show(DriverProfile.this, "Please Wait...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {

            String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);//take the url for the php in case we want to retrives the driver schedule
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("D_ID", "UTF-8") + "=" + URLEncoder.encode(D_ID, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream Is = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    Is.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           loading.dismiss();
            sJson=s;
         //    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            extractJSONRatting();

        }
    }

}
