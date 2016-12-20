package wassilni.pl.navigationdrawersi.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;

/**
 * Created by haya on 10/19/2016.
 */

public class FragmentFifth extends Fragment {

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

    EditText et_fName ,et_lName , et_email , et_password, et_checkPassword ,et_phone , et_school;
    Button saveB;
    String id;

    private int TRACK = 0;
    private JSONArray users = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);

        saveB=(Button) view.findViewById(R.id.saveB);

        et_fName = (EditText) view.findViewById(R.id.fistNameET);
        et_lName = (EditText)view.findViewById(R.id.lastNameET);
        et_email = (EditText)view.findViewById(R.id.emailET);
        et_password = (EditText)view.findViewById(R.id.passwordET);
        et_checkPassword = (EditText)view.findViewById(R.id.passwordCheckET);
        et_phone = (EditText)view.findViewById(R.id.phoneET);
        et_school = (EditText)view.findViewById(R.id.school);
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
                               /*
                               * Here put the code for deleting the account !!!!!!!!!!!!
                               * */
                               String method = "delete";
                               backgroundTask bc = new backgroundTask(getActivity());
                               bc.execute(method,id);

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

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Delete session because it contains the old data.*/
                SharedPreferences sp= getContext().getSharedPreferences("session", Context.MODE_APPEND);
                SharedPreferences.Editor editor= sp.edit();
                editor=editor.clear();
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(),"تم حذف ملف التعريف, يتوجب عليك تسجيل الدخول مجدداً",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),login.class));

            }
        });


        getJSON(url);
      //  Button edit=(Button)view.findViewById(R.id.editinfo);
          /*  edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent e=new Intent(getActivity(),editpage.class);
                    startActivity(e);
                }
            });*/
       return view;

    }


    private void showData(){
        try {
            JSONObject jsonObject = users.getJSONObject(TRACK);




           /* id.setText(jsonObject.getString(ID));
            name.setText(jsonObject.getString(FName));
            email.setText(jsonObject.getString(EMAIL));*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void extractJSON(){
        try {
            JSONObject jsonObject = new JSONObject(sJson);
            users = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  ButterKnife.reset(this);
    }

    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("Fragment 5 --","##################"+s);
                //  textViewJSON.settText(s);
                sJson=s;
                //Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(sJson);
                    users = jsonObject.getJSONArray(JSON_ARRAY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


               try {
                    JSONObject jsonObject = users.getJSONObject(TRACK);
                    id=jsonObject.getString(ID);
                    et_fName.setText(jsonObject.getString(FName));
                    et_lName.setText(jsonObject.getString(LName));
                    et_email.setText(jsonObject.getString(EMAIL));
                    et_phone.setText(jsonObject.getString(PHONE));
                    et_school.setText(jsonObject.getString(school));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }
   // public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.registerB:
               // Intent e=new Intent(getActivity(),editpage.class);
              //  startActivity(e);
                break;
            case R.id.deletPassenger:
               email=et_email.getText().toString().trim();
                String method = "delete";
                backgroundTask bc = new backgroundTask(getActivity());
                bc.execute(method,email);
                break;
        }*/
  //  }

    public void setValues()
    {
        et_fName.setText(MyApp.passenger_from_session.getFName());
        et_lName.setText(MyApp.passenger_from_session.getLName());
        et_email.setText(MyApp.passenger_from_session.getEmail());
        et_phone.setText(MyApp.passenger_from_session.getPhone());
        et_school.setText(MyApp.passenger_from_session.getSchool());

    }
}




