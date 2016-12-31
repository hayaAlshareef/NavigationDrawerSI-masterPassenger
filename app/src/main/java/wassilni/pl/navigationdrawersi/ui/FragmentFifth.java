package wassilni.pl.navigationdrawersi.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;

/**
 * Created by haya on 10/19/2016.
 */

public class FragmentFifth extends Fragment implements TextWatcher ,View.OnClickListener{

    String jsonString;
    public static final String MY_JSON ="GetAllPass";//MY_JSON
    String url="http://wassilni.com/db/GetAllPass.php";

    String sJson;
   // TextView name , email , id;
    private static final String JSON_ARRAY ="result";
    private static final String ID = "P_ID";//same as the name in php file
    private static final String PASSWORD  = "P_password";
    private static final String FName = "P_F_Name";
    private static final String LName= "P_L_Name";
    private static final String EMAIL= "P_email";
    private static final String PHONE= "P_phone";
    private static final String school= "P_school";

    EditText et_fName ,et_lName , et_email ,et_currentPassword , et_password, et_checkPassword ,et_phone , et_school;

    String sName ,lName , email , password , currentPassword,currentPassFromUser,passwordCheck , phone , Editschool;
    Button saveB;
    String id;

    private int TRACK = 0;
    private JSONArray users = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);

        saveB=(Button) view.findViewById(R.id.saveB);
        saveB.setOnClickListener(this);
        et_fName = (EditText) view.findViewById(R.id.fistNameET);
        et_lName = (EditText)view.findViewById(R.id.lastNameET);
        et_email = (EditText)view.findViewById(R.id.emailET);
        et_password = (EditText)view.findViewById(R.id.passwordET);
        et_currentPassword=(EditText)view.findViewById(R.id.passwordCurrentET);
        et_checkPassword = (EditText)view.findViewById(R.id.passwordCheckET);
        et_phone = (EditText)view.findViewById(R.id.phoneET);
        et_school = (EditText)view.findViewById(R.id.school);
        et_fName.addTextChangedListener(this);
        et_lName.addTextChangedListener(this);
        et_email.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        et_currentPassword.addTextChangedListener(this);
        et_checkPassword.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        et_school.addTextChangedListener(this);

        TextView d =(TextView)view.findViewById(R.id.deletPassenger);

        setValues();

       d.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new AlertDialog.Builder(getContext())
                       .setTitle("تأكيد حذف الحساب")
                       .setMessage("هل أنت متأكد من حذف حسابك؟")
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                           public void onClick(DialogInterface dialog, int whichButton) {
                               Toast.makeText(getContext(),"تم حذف حسابك",Toast.LENGTH_SHORT).show();
                               SharedPreferences sp= getContext().getSharedPreferences("session", Context.MODE_APPEND);
                               SharedPreferences.Editor editor= sp.edit();
                               editor=editor.clear();
                               editor.clear();
                               editor.commit();
                               MyApp.passenger_from_session=null;
                               Toast.makeText(getActivity(),"تم حذف الحساب",Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(getActivity(),login.class));
                               getActivity().finish();
                               /*
                               * Here put the code for deleting the account !!!!!!!!!!!!
                               * */
                               String res= "";
                               String method = "delete";
                               backgroundTask bc = new backgroundTask(getActivity());
                               try {
                                   res=bc.execute(method,id).get();
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               } catch (ExecutionException e) {
                                   e.printStackTrace();
                               }
                               if(res.equals("Passenger Deleted Successfully")){
                                   startActivity(new Intent(getActivity(),login.class));
                               }


                           }})
                       .setNegativeButton(android.R.string.no, null).show();

              // switch (v.getId()) {
                //   case R.id.registerB:
                       // Intent e=new Intent(getActivity(),editpage.class);
                       //  startActivity(e);
                    //   break;
                  // case R.id.deletPassenger:


                    //   break;
           }
       });




       // getJSON(url);
       return view;

    }

    public void userReg() {
        intitialize();
        if(!validateInput())
        {

            Toast.makeText(getActivity(),"خطأ في التعديل",Toast.LENGTH_SHORT).show();
        }
        else{
            String method="update";
            backgroundTask backgroundTask = new backgroundTask(getActivity());
            String result;//to take the result form the php and check if it register or no
            try {
                result=backgroundTask.execute(method, id, sName, lName, email, password, phone, Editschool).get();
                if(result.contains("update")){
                    SharedPreferences sp= getContext().getSharedPreferences("session", Context.MODE_APPEND);
                    SharedPreferences.Editor editor= sp.edit();
                    editor=editor.clear();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(getActivity(),"تم حذف ملف التعريف, يتوجب عليك تسجيل الدخول مجدداً",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),login.class));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }}
    }


    //this method to initionalize  all the input from user to string
    private void intitialize() {
        sName =et_fName.getText().toString().trim();
        lName =et_lName.getText().toString().trim();
        email =et_email.getText().toString().trim();
        phone =et_phone.getText().toString().trim();
        password =et_password.getText().toString().trim();
        passwordCheck =et_checkPassword.getText().toString().trim();
        Editschool=et_school.getText().toString().trim();
        currentPassFromUser=et_currentPassword.getText().toString().trim();
    }


    private boolean validateInput(){

        boolean result =true;

        if(et_fName.getText().toString().length()==0 && et_fName.getText().toString().equals(null) )     //size as per your requirement
        {
            et_fName.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_lName.getText().toString().length()==0 && et_lName.getText().toString().equals(null))     //size as per your requirement
        {
            et_lName.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_email.getText().toString().length()==0 && et_email.getText().toString().equals(null))     //size as per your requirement
        {
            et_email.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_phone.getText().toString().length()!=10)     //size as per your requirement
        {
            et_phone.setError("رقم الجوال يجب ان يكون 10 خانات ");
            result=false;

        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches() && et_email.getText().toString().equals(null))     //size as per your requirement
        {
            et_email.setError("فورمات الايميل غير صحيح ");
            result=false;

        }
        if(et_school.getText().toString().length()==0 && et_school.getText().toString().equals(null)){

            et_school.setError("يجب عليك تعبية هذه الخانة ");
            result=false;

        }
        if(et_currentPassword.getText().toString().equals(null)){}
        else{
            if(currentPassword.equals(et_currentPassword.getText().toString())) {
                boolean r = checkPassword();
                if (!r) result = false;

            }
            et_currentPassword.setError("الرقم السري الحالي غير متوافق!");

        }


        return result;
    }
    @Override
    public void onClick(View v) {
        userReg();
    }

  public boolean checkPassword(){
          boolean r=true;

          if(et_password.getText().toString().length()==0 && et_password.getText().toString().equals(null))     //size as per your requirement
          {
              et_password.setError("يجب عليك تعبية هذه الخانة ");
              r=false;

          }
          if(et_checkPassword.getText().toString().length()==0 && et_checkPassword.getText().toString().equals(null))     //size as per your requirement
          {
              et_checkPassword.setError("يجب عليك تعبية هذه الخانة ");
              r=false;

          }

          if(et_password.getText().toString().length()<6 && et_password.getText().toString().equals(null))
          {
              et_password.setError("الرقم السري يجب ان يكون أطول من 10 خانات");
              r=false;
          }
          if(et_checkPassword.getText().toString().length()<6 && et_checkPassword.getText().toString().equals(null))
          {

              et_checkPassword.setError("الرقم السري يجب ان يكون أطول من 10 خانات ");
              r=false;

          }
          else if (!et_checkPassword.getText().toString().equals(et_password.getText().toString()))
          {
              et_checkPassword.setError("التأكد من كلمة المرور يجب أن يكون مطابق لكلمة المرور ");
              r=false;

          }


      return r;
  }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        validateInput();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
         // validateInput();
    }

    @Override
    public void afterTextChanged(Editable s) {
        validateInput();
    }
    public void setValues()
    {
        id=MyApp.passenger_from_session.getID()+"";
        currentPassword=MyApp.passenger_from_session.getPasswrod(); //current password
        et_fName.setText(MyApp.passenger_from_session.getFName());
        et_lName.setText(MyApp.passenger_from_session.getLName());
        et_email.setText(MyApp.passenger_from_session.getEmail());
        et_phone.setText(MyApp.passenger_from_session.getPhone());
        et_school.setText(MyApp.passenger_from_session.getSchool());
    }
}




