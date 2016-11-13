package wassilni.pl.navigationdrawersi.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import wassilni.pl.navigationdrawersi.R;


public class login extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
//klklk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Button login=(Button) findViewById(R.id.loginbutton);
        TextView register=(TextView) findViewById(R.id.signin) ;
        TextView recover=(TextView) findViewById(R.id.recoverdpass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });
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



    }

    public void datePicker(View v)
    {
        DatePickerFragment f = new DatePickerFragment();
        f.show(getSupportFragmentManager(),"date");

    }

    private void setDate(final Calendar calendar){

       // final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
       // ((TextView) findViewById(R.id.datepickerTV)).setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar= new GregorianCalendar(year,month,day);
        setDate(calendar);
    }

    public static class DatePickerFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle saveInstanceState){
            final Calendar c=Calendar.getInstance();
            int year =c.get(Calendar.YEAR);
            int month =c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);

        }
    }
}