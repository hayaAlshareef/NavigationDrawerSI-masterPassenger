package wassilni.pl.navigationdrawersi.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wassilni.pl.navigationdrawersi.R;


public class register extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener {
    //  Spinner spinNationality;
    EditText et_fName ,et_lName , et_email , et_password, et_checkPassword ,et_phone , et_school ;
    String sName ,lName , email , password , passwordCheck , phone , school;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


           Button login=(Button) findViewById(R.id.registerB);
            //spinNationality=(Spinner)findViewById(R.id.spinner);
           // spinNationality.setOnItemSelectedListener(this);
            et_fName = (EditText)findViewById(R.id.fistNameET);
            et_lName = (EditText)findViewById(R.id.lastNameET);
            et_email = (EditText)findViewById(R.id.emailET);
            et_password = (EditText)findViewById(R.id.passwordET);
            et_checkPassword = (EditText)findViewById(R.id.passwordCheckET);
            et_phone = (EditText)findViewById(R.id.phoneET);
            et_school = (EditText)findViewById(R.id.school);
          //  et_nationality = (EditText)findViewById(R.id.NationalityET);
           // et_driverNum = (EditText)findViewById(R.id.driveNumET);

            // LinearLayout viewGroup1 = (LinearLayout) findViewById(R.id.viewGroup);
           // checkFieldsRequired(viewGroup1);

            login.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    register();//call to validate the inputs
                }
            });

        }//end onCreate method



        private void register() {
            intitialize();// intitialize the input to string var
            if (!validate()) {
                // Toast.makeText(this,"خطأ في التسجيل",Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(this,"اكتمل التسجيل",Toast.LENGTH_SHORT).show();
                //onSigunSuccess();
                if (!check()) {
                    //    Toast.makeText(this,"خطأ في التسجيل",Toast.LENGTH_SHORT).show();

                } else {
                  //  Toast.makeText(this, "اكتمل التسجيل", Toast.LENGTH_SHORT).show();
                    onSigunSuccess();

                }
            }
        }
        public void onSigunSuccess(){
           // Intent i=new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(i);
            String method="register";
            backgroundTask bc=new backgroundTask(this);
            bc.execute(method,sName,lName,email,password,phone,school);
        }
        public boolean validate(){
            boolean valid=true;
            if(sName.isEmpty() || sName.length()>=32){ //to validate the first name
                et_fName.setError("أدخل اسم صحيح!!");
                valid=false;
            }
            else if(lName.isEmpty() || lName.length()>=32){ //to validate the last name
                et_lName.setError("أدخل اسم صحيح!!");
                valid=false;
            }

            else if(email.isEmpty() || email.length()>=32){ //to validate the email
                et_email.setError("أدخل البريد الإلكتروني!!");
                valid=false;
            }
            else if(phone.isEmpty() || phone.length()!=10){ //to validate the phone number
                et_phone.setError("أدخل اسم صحيح!!");valid=false;
            }
            else if(password.isEmpty() || password.length() >=10){ //to validate the password
                et_password.setError("أدخل كلمة مرور مناسبة!!");
                valid=false;
            }
            else if(passwordCheck.isEmpty() || passwordCheck.length() >=10){ //to validate the confirmpassword
                et_checkPassword.setError("أدخل كلمة مرور مناسبة!!");
                valid=false;
            }

            return valid;
        }

        private void intitialize(){
            //Reset errors
            et_fName.setError(null);
            et_lName.setError(null);
            et_email.setError(null);
            et_password.setError(null);
            et_checkPassword.setError(null);
            et_phone.setError(null);

            //convert edittext to string
            sName =et_fName.getText().toString().trim();
            lName =et_lName.getText().toString().trim();
            email =et_email.getText().toString().trim();
            phone =et_phone.getText().toString().trim();
            password =et_password.getText().toString().trim();
            passwordCheck =et_checkPassword.getText().toString().trim();
            school=et_school.getText().toString().trim();

        }

    private boolean check(){
        boolean validate1=true;
        if(!isPasswordMatching(password,passwordCheck)){
            et_password.setError("كلمة المرور غير متوافقة!");
            et_checkPassword.setError("كلمة المرور غير متوافقة!");

            validate1=false;
        }
        else if(!isEmailValid(email)){
            et_email.setError("ليس بريد إلكتروني!");
            validate1=false;
        }

        return validate1;

    }

    public boolean isPasswordMatching(String pass, String cPass) {
        boolean val=true;
        Pattern pattern = Pattern.compile(pass, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cPass);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");
            val=false;

        }

        return val;
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
     /*   public boolean checkFieldsRequired(ViewGroup viewGroup){

            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof ViewGroup)
                    checkFieldsRequired((ViewGroup) view);
                else if (view instanceof EditText) {
                    EditText edittext = (EditText) view;
                    if (edittext.getText().toString().trim().equals("")) {
                        edittext.setError("Required!");
                    }
                }
            }

            return false;
        }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText =(TextView)view;
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView)adapterView.getChildAt(0)).setTextSize(18);
        Toast.makeText(this,"إخترت :"+myText.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void datePicker(View v)
    {
        login.DatePickerFragment f = new login.DatePickerFragment();
        f.show(getSupportFragmentManager(),"date");

    }

    private void setDate(final Calendar calendar){

      //  final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
       // ((EditText) findViewById(R.id.datepickerET)).setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar= new GregorianCalendar(year,month,day);
        setDate(calendar);
    }

    public static class DatePickerFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle saveInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
   /* private void OnButtonClick(View v)
    {
        if(v.getId()== R.id.registerB){

            Intent i = new Intent(register.this,registermore.class);
            startActivity(i);
        }

    }*/
        }}