package wassilni.pl.navigationdrawersi.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import Objects.MyApp;
import Objects.Passenger;
import wassilni.pl.navigationdrawersi.R;


public class login extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public SharedPreferences sharedPreferences;
    EditText et_loginName ,et_loginPass;
    String login_name,login_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("session", Context.MODE_APPEND);
        setContentView(R.layout.activity_login);
        Button login=(Button) findViewById(R.id.loginbutton);
        TextView register=(TextView) findViewById(R.id.signin) ;
        TextView recover=(TextView) findViewById(R.id.recoverdpass);
        et_loginName=(EditText) findViewById(R.id.emailUET);
        et_loginPass=(EditText) findViewById(R.id.passwordET);


       /* login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        }); */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r=new Intent(getApplicationContext(), wassilni.pl.navigationdrawersi.ui.register.class);
                startActivity(r);
            }
        });
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c=new Intent(getApplicationContext(),recoverdpass.class);
                startActivity(c);
            }
        });


        //SessionSetup();
    } //end onCreat method


    public void userReg(View view ){

    }
    public void userLogin(View view){
        //startActivity(new Intent(this , register.class));
        intitialize();
        String method="login";
        String res= "";
        backgroundTask bc=new backgroundTask(this);
        try {
            res= bc.execute(method,login_name,login_pass).get();
            System.out.println("########################"+res);
            //if()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    if(res.contains("success")) {
        //Toast.makeText(getApplicationContext(),"فشل تسجيل الدخول , تحقق من بياناتك ",Toast.LENGTH_SHORT).show();
        createSession();
        moveNext();
    }
        else
    Toast.makeText(getApplicationContext(),"فشل تسجيل الدخول , تحقق من بياناتك ",Toast.LENGTH_SHORT).show();
        //finish();
    }
    public void moveNext()
    {
        Intent i = new Intent(login.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void intitialize(){
        login_name =et_loginName.getText().toString().trim();
        login_pass =et_loginPass.getText().toString().trim();
    }

    public void datePicker(View v)
    {
        DatePickerFragment f = new DatePickerFragment();
        f.show(getSupportFragmentManager(),"date");

    }

    private void setDate(final Calendar calendar){

      //  final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
      //  ((TextView) findViewById(R.id.datepickerTV)).setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar= new GregorianCalendar(year,month,day);
        setDate(calendar);
    }

    public static class DatePickerFragment extends DialogFragment{
        public Dialog onCreateDialog(Bundle saveInstanceState){
            final Calendar c=Calendar.getInstance();
            int year =c.get(Calendar.YEAR);
            int month =c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);

        }
    }

    /*public void SessionSetup() {



        class Task extends AsyncTask<String, Void, String> {
            ProgressDialog loggingin;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                //loggingin = ProgressDialog.show(login.this, "ارجو الإنتظار...", "جاري استرجاع المعلومات إن وجدت");
            }

            @Override
            protected String doInBackground(String... params) {
                String flag = "";
                if (sharedPreferences.contains("ID"))//means that the XML file isn't empty.
                {//retrieve session , and set the variable MyApp.passenger_from_session
                    Passenger.retrieveSession(sharedPreferences);
                //System.out.println("فييييييه سييييييييييششششششششششننننننننن موجوده");
                    flag = "succeed";
                } else // there's no session saved, then create one!
                {
                    flag = "failed";
                    // 1.prompt for login, using Toast
                }
                System.out.println("$#$#$#$#$#$#$#$#$#$#$#$#$$#$#$#$#$#$#$ "+flag);
                return flag;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loggingin.dismiss();
                if (s.equalsIgnoreCase("succeed")) {
                    Toast.makeText(login.this, "مرحبا مجدداً!", Toast.LENGTH_LONG).show();
                    System.out.println("@@@@@@@@@@@\t "+ MyApp.passenger_from_session.getEmail());
                    moveNext();
                    //direct user to next page.
                } else {
                    //prompt to logiin
                    Toast.makeText(login.this, "لا يوجد ملف تعريف, الرجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
                }

            }

        }
        Task t=new Task();
        //t.execute(id+"");
        t.execute();
    }*/


    public void createSession()
    {
             /*
            * 1. Prompt user to login.
            * 2. restore the data from the DB.
            * 3. and insert it in a session.
            * 4. set data in MyApp.driver_from_session
            * */
        Passenger p = new Passenger();
        //Email entered by the user, since the authentication succeed, it's guarnteed that the email valid//step 2
        p.getPassengerFromDB(login_name);//step 2
        p.createSession(sharedPreferences);//step 3,4
    }
}