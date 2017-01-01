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
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import Objects.Driver;
import Objects.MyApp;
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
    ArrayList<Driver> driveresInfo;
    Driver driverObject;
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
        driveresInfo = new ArrayList<Driver>();
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
                String uri = "http://wassilni.com/db/PassengerComplaint.php";
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
                        String P_ID = MyApp.passenger_from_session.getID()+"";
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        OutputStream outputStream=con.getOutputStream();
                        BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        String data= URLEncoder.encode("P_ID","UTF-8")+"="+URLEncoder.encode(P_ID,"UTF-8");
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream Is=con.getInputStream();
                        bufferedReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                       // bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        StringBuilder sb = new StringBuilder();


                        String json="";
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
                System.out.println("in FragmentFour getJson"+s);
                sJson = s;
                //Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(sJson);
                    users = jsonObject.getJSONArray(JSON_ARRAY);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(users.length()!=0) {
                    try {
                        int counter = 1;
                        name = new String[users.length() + 1];
                        JSONObject jsonObject;
                        driverObject = new Driver(0, "شكوى عامة");
                        driveresInfo.add(driverObject);
                        name[0] = "شكوى عامة";
                        while (TRACK < users.length()) {
                            jsonObject = users.getJSONObject(TRACK);
                            String fName = jsonObject.getString("D_F_Name");
                            String lName = jsonObject.getString("D_L_Name");
                            name[counter] = fName + " " + lName;
                            driverObject = new Driver(Integer.parseInt(jsonObject.getString("D_ID")), name[counter]);
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
                else   Toast.makeText(getActivity(), "لم تشترك في رحلات بعد", Toast.LENGTH_SHORT).show();

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
                        if (driveresInfo.get(i).getFName().contains(driverSelected)) {
                            ID_Driver = driveresInfo.get(i).getID()+"";

                           // Toast.makeText(getActivity(), ID_Driver+" "+Complaint,Toast.LENGTH_SHORT).show();


                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Complaint = complaint.getText().toString().trim();
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
        String Pass_id=MyApp.passenger_from_session.getID()+""; // from session
        backgroundTask b = new backgroundTask(getActivity());
        System.out.println("in fragment four : "+id + comp + Pass_id + time);
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