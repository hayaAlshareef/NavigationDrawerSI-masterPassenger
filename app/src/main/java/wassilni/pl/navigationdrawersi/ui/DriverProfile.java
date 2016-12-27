package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import wassilni.pl.navigationdrawersi.R;

public class DriverProfile extends AppCompatActivity {


    TextView TV_Name, TV_phoneNum,TV_model, TV_age, TV_nationality, TV_companyNam,
            TV_carColor, TV_carType, TV_carComp, TV_plateNum, TV_capacity,
            TV_manufactured, TV_female,TV_tripTime,TV_dayPrice,TV_monthPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

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
        showData();


    }


    private void showData(){
                /*
        * Since session already retrieved from login class,
        * no need to retrieve the data from the DB, instead get it from session.
        * using the global variable MyApp.driver_form_session
        * */
        Intent in = getIntent();
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
        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

}
