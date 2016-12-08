package wassilni.pl.navigationdrawersi.ui;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import wassilni.pl.navigationdrawersi.R;


/**
 * Created by haya on 10/12/2016.
 */


@TargetApi(Build.VERSION_CODES.N)
public class FragmentFour extends Fragment {

    public static final String MY_JSON = "SendComplaint";//MY_JSON

    String[] name;
    String sJson;
    String ID_Driver=null;
    String Complaint;
    EditText complaint;
    ArrayList<String> DriverN;
    ArrayList<GetDriversName> driveresInfo;
    GetDriversName driverObject;
    final Calendar calendar = Calendar.getInstance();
    private static final String JSON_ARRAY = "result";


    private int TRACK = 0;
    private JSONArray users = null;
    Spinner DriversName;
    Button send;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);

        DriversName = (Spinner) view.findViewById(R.id.spinner);
        driveresInfo = new ArrayList<GetDriversName>();
        complaint = (EditText) view.findViewById(R.id.edit_texto);
        send = (Button) view.findViewById(R.id.send);


        getJSON("getComplaint");

        return view;

    }


    private void showData() {
        try {
            JSONObject jsonObject = users.getJSONObject(TRACK);


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

    }

    public void getJSON(String url) {
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
                String uri = "http://192.168.56.1/wassilni/PassengerComplaint.php";
                String Exe = null;
                String method = params[0];
                //String comp;
                //String Drivername;
               /* if (method.equals("sendComplaint")) {






                }
                else*/
                if (method.equals("getComplaint")) {
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

                        Exe = sb.toString().trim();

                    } catch (Exception e) {
                        Exe = null;
                    }
                }
                return Exe;
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
                    int counter = 1;
                    name = new String[users.length() + 1];
                    JSONObject jsonObject;
                    driverObject = new GetDriversName("0", "شكوى عامة");
                    driveresInfo.add(driverObject);
                    name[0] = "شكوى عامة";
                    while (TRACK < users.length()) {
                        jsonObject = users.getJSONObject(TRACK);
                        String fName = jsonObject.getString("D_F_Name");
                        String lName = jsonObject.getString("D_L_Name");
                        name[counter] = fName + " " + lName;
                        driverObject = new GetDriversName(jsonObject.getString("D_ID"), name[counter]);
                        driveresInfo.add(driverObject);
                        counter++;
                        TRACK++;
                    }


                    for (int i = 0; i < name.length; i++) {

                        DriverN.add(name[i]);

                    }

                    spinner_fn();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    private void spinner_fn() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, DriverN);
        DriversName.setAdapter(dataAdapter);
        DriversName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DriversName.setSelection(position);
                String driverSelected = parent.getItemAtPosition(position).toString();


                if (!driverSelected.isEmpty()) {
                    for (int i = 0; i < driveresInfo.size(); i++) {
                        if (driveresInfo.get(i).getDriverName().contains(driverSelected)) {
                            ID_Driver = driveresInfo.get(i).getDriverID();
                            Complaint = complaint.getText().toString().trim();
                            Toast.makeText(getActivity(), ID_Driver+Complaint,Toast.LENGTH_SHORT).show();

                            //String method="sendComplaint";
                            //getJSON(method);
                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SendComplaint(ID_Driver, Complaint);
                                }
                            });
                        }
                    }

                    // Complaint=complaint.getText().toString().trim();
                    // ID_Driver=null;
                    //SendComplaint(ID_Driver,Complaint);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void SendComplaint(String id, String comp) {
        final String methods = "sendComplaint";
       // String date = setCurrentDate();
        String time=setCurrentTime();
        String Pass_id="3"; // login session
        backgroundTask b = new backgroundTask(getActivity());
        b.execute(methods, id, comp,Pass_id,time);

    }

    private String setCurrentDate() {

        String dateFormat = "yyyy-mm-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

        return sdf.format(calendar.getTime());
    }


    private String setCurrentTime() {
        String timeFormat = "hh:mm:ss a";
       SimpleDateFormat sdf=new SimpleDateFormat(timeFormat,Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}