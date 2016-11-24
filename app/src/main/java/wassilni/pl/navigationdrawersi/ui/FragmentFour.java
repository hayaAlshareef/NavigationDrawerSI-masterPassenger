package wassilni.pl.navigationdrawersi.ui;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import wassilni.pl.navigationdrawersi.R;


/**
 * Created by haya on 10/12/2016.
 */


public class FragmentFour extends Fragment {

    public static final String MY_JSON = "SendComplaint";//MY_JSON
    String url = "http://192.168.56.1/wassilni/PassengerComplaint.php";
    String[] name;
    String sJson;
    ArrayList<String> DriverN;
    // TextView name , email , id;
    private static final String JSON_ARRAY = "result";
   /* private static final String ID = "P_ID";//same as the name in php file
    private static final String PASSWORD  = "P_password";
    private static final String FName = "P_F_Name";
    private static final String LName= "P_L_Name";
    private static final String EMAIL= "P_email";
    private static final String PHONE= "P_phone";
    private static final String school= "P_school";

    EditText et_fName ,et_lName , et_email , et_password, et_checkPassword ,et_phone , et_school;
    String id;*/

    private int TRACK = 0;
    private JSONArray users = null;
    Spinner DriversName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);

        DriversName = (Spinner) view.findViewById(R.id.spinner);
      /*  et_fName = (EditText) view.findViewById(R.id.fistNameET);
        et_lName = (EditText)view.findViewById(R.id.lastNameET);
        et_email = (EditText)view.findViewById(R.id.emailET);
        et_password = (EditText)view.findViewById(R.id.passwordET);
        et_checkPassword = (EditText)view.findViewById(R.id.passwordCheckET);
        et_phone = (EditText)view.findViewById(R.id.phoneET);
        et_school = (EditText)view.findViewById(R.id.school);
        TextView d =(TextView)view.findViewById(R.id.deletPassenger);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // switch (v.getId()) {
                //   case R.id.registerB:
                // Intent e=new Intent(getActivity(),editpage.class);
                //  startActivity(e);
                //   break;
                // case R.id.deletPassenger:

                String method = "delete";
                backgroundTask bc = new backgroundTask(getActivity());
                bc.execute(method,id);
                //   break;
            }
        });*/

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


    private void showData() {
        try {
            JSONObject jsonObject = users.getJSONObject(TRACK);




           /* id.setText(jsonObject.getString(ID));
            name.setText(jsonObject.getString(FName));
            email.setText(jsonObject.getString(EMAIL));*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void extractJSON() {
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
                loading = ProgressDialog.show(getActivity(), "Please Wait...", null, true, true);
                DriverN = new ArrayList<String>();
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
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // name=new String[users.length()];
                //  textViewJSON.settText(s);
                sJson = s;
                //Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(sJson);
                    users = jsonObject.getJSONArray(JSON_ARRAY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    int counter = 0;
                    name = new String[users.length()];
                    JSONObject jsonObject = users.getJSONObject(TRACK);

                    while (TRACK < users.length()) {

                        name[counter] = jsonObject.getString("D_F_Name");

                        counter++;
                        TRACK++;
                    }


                    for (int i = 0; i < name.length; i++) {

                        DriverN.add(name[i]);

                    }
                    spinner_fn();
                    /*id=jsonObject.getString(ID);
                    et_fName.setText(jsonObject.getString(FName));
                    et_lName.setText(jsonObject.getString(LName));
                    et_email.setText(jsonObject.getString(EMAIL));
                    et_phone.setText(jsonObject.getString(PHONE));
                    et_school.setText(jsonObject.getString(school));*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    private void spinner_fn() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
        DriversName.setAdapter(dataAdapter);
        DriversName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DriversName.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}