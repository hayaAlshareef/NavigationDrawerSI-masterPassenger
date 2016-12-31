package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import wassilni.pl.navigationdrawersi.R;


public class register extends AppCompatActivity  implements View.OnClickListener ,AdapterView.OnItemSelectedListener, TextWatcher {
    //  Spinner spinNationality;
    EditText et_fName ,et_lName , et_email , et_password, et_checkPassword ,et_phone , et_school ;
    String sName ,lName , email , password , passwordCheck , phone , school;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


           Button login=(Button) findViewById(R.id.registerB);
            login.setOnClickListener(this);
            //spinNationality=(Spinner)findViewById(R.id.spinner);
           // spinNationality.setOnItemSelectedListener(this);
            et_fName = (EditText)findViewById(R.id.fistNameET);
            et_lName = (EditText)findViewById(R.id.lastNameET);
            et_email = (EditText)findViewById(R.id.emailET);
            et_password = (EditText)findViewById(R.id.passwordET);
            et_checkPassword = (EditText)findViewById(R.id.passwordCheckET);
            et_phone = (EditText)findViewById(R.id.phoneET);
            et_school = (EditText)findViewById(R.id.school);

            et_fName.addTextChangedListener(this);
            et_lName.addTextChangedListener(this);
            et_email.addTextChangedListener(this);
            et_password.addTextChangedListener(this);
            et_checkPassword.addTextChangedListener(this);
            et_phone.addTextChangedListener(this);
            et_school.addTextChangedListener(this);
        }//end onCreate method



    public void userReg() {
        intitialize();
      //  boolean flage=true;
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches())
        {


            String method="checkEmail";
            backgroundTask backgroundTask = new backgroundTask(this);
            try {
                String r ;
                r= backgroundTask.execute(method,et_email.getText().toString()).get();
                if(r.equals("The email is taken")){
                    et_email.setError("الايميل سبق التسجيل به ");
                    Toast.makeText(this,"الايميل سبق التسجيل به",Toast.LENGTH_SHORT).show();
                    //flage=false;
                }
                else{
                    regiset();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }
public void regiset(){

    if(!validateInput())
    {

        Toast.makeText(this,"خطأ في التسجيل",Toast.LENGTH_SHORT).show();
    }
    else{

        String method="register";
        backgroundTask bc=new backgroundTask(this);
        String result;//to take the result form the php and check if it register or no
        try {
            result=bc.execute(method, sName, lName, email, password, phone, school).get();
            if(result.contains("Passenger Added Successfully")){
                Toast.makeText(this,"لقد تم التسجيل بنجاح",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,login.class);
                startActivity(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

        private void intitialize(){
            //convert edittext to string
            sName =et_fName.getText().toString().trim();
            lName =et_lName.getText().toString().trim();
            email =et_email.getText().toString().trim();
            phone =et_phone.getText().toString().trim();
            password =et_password.getText().toString().trim();
            passwordCheck =et_checkPassword.getText().toString().trim();
            school=et_school.getText().toString().trim();

        }

    private boolean validateInput(){

        boolean result =true;

        if(et_fName.getText().toString().length()==0)     //size as per your requirement
        {
            et_fName.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_lName.getText().toString().length()==0)     //size as per your requirement
        {
            et_lName.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_school.getText().toString().length()==0)     //size as per your requirement
        {
            et_school.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_email.getText().toString().length()==0)     //size as per your requirement
        {
            et_email.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_password.getText().toString().length()==0)     //size as per your requirement
        {
            et_password.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_checkPassword.getText().toString().length()==0)     //size as per your requirement
        {
            et_checkPassword.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_phone.getText().toString().length()!=10)     //size as per your requirement
        {
            et_phone.setError("رقم الجوال يجب ان يكون 10 خانات ");
            result=false;

        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches())     //size as per your requirement
        {
            et_email.setError("فورمات الايميل غير صحيح ");
            result=false;

        }


        if(et_password.getText().toString().length()<6)
        {
            et_password.setError("الرقم السري يجب أن يكون أطول من 6 خانات");
            result=false;
        }
        if(et_checkPassword.getText().toString().length()<6){

            et_checkPassword.setError("الرقم السري يجب أن يكون أطول من 6 خانات");
            result=false;

        }
        else if (!et_password.getText().toString().equals(et_checkPassword.getText().toString())){
            et_password.setError("التأكد من كلمة المرور يجب أن يكون مطابق لكلمة المرور ");
            result=false;

        }


        return result;
    }

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
    public void onClick(View v) {
        userReg();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        validateInput();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validateInput();
    }

    @Override
    public void afterTextChanged(Editable s) {
        // System.out.println(s.toString());
        validateInput();


    }
        }