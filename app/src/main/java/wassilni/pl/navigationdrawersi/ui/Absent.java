package wassilni.pl.navigationdrawersi.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import wassilni.pl.navigationdrawersi.R;

public class Absent extends AppCompatActivity implements  View.OnClickListener,AdapterView.OnItemSelectedListener {
    Button absent, absentD;
    //Spinner hoursSpinner, mintsSpinner;
    static EditText absentDate;
    public static String Date,R_ID;
   // public static String times, hour, mint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);

        Intent i= getIntent();
        R_ID=i.getStringExtra("R_ID");

        //hoursSpinner = (Spinner) findViewById(R.id.spinnerHours);
        //hoursSpinner.setOnItemSelectedListener(this);
       // mintsSpinner = (Spinner) findViewById(R.id.spinnerMints);
        //mintsSpinner.setOnItemSelectedListener(this);
        absentDate = (EditText) findViewById(R.id.absentDateET);
        absentD = (Button) findViewById(R.id.absentDate);
        absentD.setOnClickListener(this);
        absent = (Button) findViewById(R.id.sendAbsent);

        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method="sendAbsent";
                String json=getFields();
                if(!absentDate.getText().toString().equals(""))
                {// if not empty -----------------------------------------------------------
                        System.out.println("date= "+absentDate.getText().toString());
                        backgroundTask backgroundTask = new backgroundTask(getApplicationContext());
                        String result;//to take the result form the php and check if it register or no
                        try {
                            result=backgroundTask.execute(method,R_ID,Date).get();
                            if(result.contains("Absent send Successfully")) {
                                Toast.makeText(getApplicationContext(),"تم ارسال موعد غيابك بنجاح",Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(),"حدث خطأ أثناء ارسال موعد غيابك , الرجاء المحاولة مرة أخرى",Toast.LENGTH_SHORT).show();

                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                }else
                    Toast.makeText(getApplicationContext(),"الرجاء ملء جميع الخانات",Toast.LENGTH_SHORT).show();



            }
        });
    }

    public String getFields()
    {
        //if all fields has values
        Date = absentDate.getText().toString();
      //  times =hour+":"+mint+":"+"00";

        System.out.println(" adddddddres");
        String result=Date;
       // Background b =new Background();
       // try {
            //result =b.execute(sDate,eDate,time,pLoc,dLoc).get();

      //  } catch (InterruptedException e) {
            //e.printStackTrace();
       // } catch (ExecutionException e) {
            //e.printStackTrace();
      //  }
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO  "+R_ID+"\t"+Date);
        //2016-12-28	2017-01-20	11:00:00	24.678747461415842,46.688804626464844	24.726262143865004,46.63715735077858
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.absentDate ) {
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getSupportFragmentManager(), "DatePicker");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        TextView myText = (TextView) view;
      /*  ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) adapterView.getChildAt(0)).setTextSize(18);
        if(spinner.getId()==R.id.spinnerHours){
           /// hour = (String) myText.getText();
        }
        else if(spinner.getId()==R.id.spinnerMints){
          //  mint = (String) myText.getText();
        }*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @SuppressLint("ValidFragment")
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        int yy, mm, dd;
        String date;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
            yy = calendar.get(android.icu.util.Calendar.YEAR);
            mm = calendar.get(android.icu.util.Calendar.MONTH);
            dd = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);


            populateSetDate(calendar);
        }

        public void populateSetDate(final Calendar calendar) {
            java.util.Date newDate = calendar.getTime();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            date = spf.format(newDate);


            absentDate.setText(date);


        }

    }
}
