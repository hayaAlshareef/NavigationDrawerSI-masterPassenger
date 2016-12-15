package wassilni.pl.navigationdrawersi.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import wassilni.pl.navigationdrawersi.R;


public class FragmentOne extends Fragment {
//GetAllReq

    public static final String MY_JSON = "getRequest";//MY_JSON
    private ArrayList<Request> listOfReq;
    private static final String JSON_ARRAY = "result";
    String sJson;
    ListView requests;
    Request Objrequest;
    Button absent;
    Button unSubscribe;
    private int TRACK = 0;
    private JSONArray users = null;
    ArrayAdapter<Request> dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, containter, false);

        listOfReq = new ArrayList<Request>();
        requests = (ListView) view.findViewById(R.id.listRequest);


        getJSON("GetAllReq");


        return view;
    }


    public void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait...", null, true, true);


            }

            @Override
            protected String doInBackground(String... params) {
                String uri = "http://192.168.56.1/wassilni/GetAllReq.php";
                String Exe = null;
                String method = params[0];

                if (method.equals("GetAllReq")) {
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
                sJson = s;
                try {
                    JSONObject jsonObject = new JSONObject(sJson);
                    users = jsonObject.getJSONArray(JSON_ARRAY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    JSONObject jsonObject;

                    while (TRACK < users.length()) {
                        jsonObject = users.getJSONObject(TRACK);

                       if (jsonObject.getString("confirm").equals("y")) {

                            Objrequest = new Request(jsonObject.getString("R_ID"), "أحمد", jsonObject.getString("R_dropoff_loc"), jsonObject.getString("r_time"), "مقبول");
                            listOfReq.add(Objrequest);

                  }  /* else if (jsonObject.getString("confirm").equals("n")) {
                            // statues.setText("مرفوض");
                            //confirm=statues.getText().toString().trim();
                            Objrequest = new Request(jsonObject.getString("R_ID"), "أحمد", jsonObject.getString("R_dropoff_loc"), jsonObject.getString("r_time"), "مرفوض");
                            listOfReq.add(Objrequest);
                            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%nnnnnnnn%%%%%%%%%%%%%%%%%%%%%%%%%");
                        } else if (jsonObject.getString("confirm").equals("w")) {
                            //statues.setText("قيد الإنتظار");
                            // confirm=statues.getText().toString().trim();
                            Objrequest = new Request(jsonObject.getString("R_ID"), "أحمد", jsonObject.getString("R_dropoff_loc"), jsonObject.getString("r_time"), "قيد الإنتظار");
                            listOfReq.add(Objrequest);
                            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%wwwwwww%%%%%%%%%%%%%%%%%%%%%%%%%%");

                        }*/

                        TRACK++;
                    }
                    requests.setAdapter(new myList(getActivity(), listOfReq));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
    class myList extends BaseAdapter {
        ArrayList<Request> items;
        Context context;
        Request temp;
        public myList(Context context,ArrayList<Request> Listitem) {
            this.context=context;
            items=Listitem;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {

            LayoutInflater inflater= LayoutInflater.from(context);
            View row=inflater.inflate(R.layout.list_item_requests,parent,false);
            TextView requestNum=(TextView) row.findViewById(R.id.requestNumberRetrive);
            TextView driverName=(TextView) row.findViewById(R.id.DriverNameRetrive);
            TextView dropOffLocation=(TextView) row.findViewById(R.id.dropOffLRet);
            TextView dropOffTime=(TextView) row.findViewById(R.id.dropOffTRet);
            TextView statues=(TextView)row.findViewById(R.id.statusRet);
            Button absent=(Button) row.findViewById(R.id.absent);
            Button unSubscribe=(Button) row.findViewById(R.id.unSubscribe);

            temp=items.get(position);

            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(context,temp.getReqNum(),Toast.LENGTH_SHORT).show(); //get request id to send absent to her driver
                }
            });
            unSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,temp.getReqNum(),Toast.LENGTH_SHORT).show(); //get request id to send unSubscribe to her driver
                }
            });


            requestNum.setText(temp.getReqNum());
            driverName.setText(temp.getDriverN());
            dropOffLocation.setText(temp.getDropOffL());
            dropOffTime.setText(temp.getDropOffT());
            statues.setText(temp.getConfirm());

            return row;
        }
    }
