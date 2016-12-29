package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import Objects.*;
import wassilni.pl.navigationdrawersi.R;

public class DriverProfile extends AppCompatActivity {


    TextView TV_Name, TV_phoneNum,TV_model, TV_age, TV_nationality, TV_companyNam,
            TV_carColor, TV_carType, TV_carComp, TV_plateNum, TV_capacity,
            TV_manufactured, TV_female,TV_tripTime,TV_dayPrice,TV_monthPrice;
    String D_ID,S_ID,P_ID;
    Intent in ;

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
        System.out.println(P_ID+"pppppppppppp");
        showData();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S_ID=in.getStringExtra("S_ID");
                D_ID=in.getStringExtra("D_ID");

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

}
