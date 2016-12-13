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
    //TextView requestNum;
    //TextView driverName;
   // TextView dropOffLocation;
    //TextView dropOffTime;
   // TextView statues;
    Button absent;
    Button unSubscribe;
    private int TRACK = 0;
    private JSONArray users = null;
   // String reqN;
    //String driverN;
    //String dfLocation;
    //String dftime;
  //  String confirm;
    ArrayAdapter<Request> dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_one, containter, false);

       /*requestNum=(TextView) view.findViewById(R.id.requestNumberRetrive);
        driverName=(TextView) view.findViewById(R.id.DriverNameRetrive);
        dropOffLocation=(TextView) view.findViewById(R.id.dropOffLRet);
        dropOffTime=(TextView) view.findViewById(R.id.dropOffTRet);
        statues=(TextView)view.findViewById(R.id.statusRet);
        absent=(Button) view.findViewById(R.id.absent);
        unSubscribe=(Button) view.findViewById(R.id.unSubscribe);*/



        requests=(ListView) view.findViewById(R.id.listRequest);
       listOfReq=new ArrayList<Request>();
        //list();


        getJSON("GetAllReq");


        return view;
    }

    public void list(){

        for(int i=0;i<=5;i++){
          //  listOfReq.add("the number is"+i );

        }


    }

    public void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait...", null, true, true);
               // DriverN = new ArrayList<String>();

            }

            @Override
            protected String doInBackground(String... params) {
                String uri = "http://192.168.56.1/wassilni/GetAllReq.php";
                String Exe = null;
                String method = params[0];
                //String comp;
                //String Drivername;
               /* if (method.equals("sendComplaint")) {

                }
                else*/
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
               // System.out.println(s);
                // name=new String[users.length()];
                //  textViewJSON.settText(s);
                sJson = s;
              //  Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
               // System.out.println(s);

                try {
                    JSONObject jsonObject = new JSONObject(sJson);
                    users = jsonObject.getJSONArray(JSON_ARRAY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                 //   int counter = 0;

                    JSONObject jsonObject;

                    while (TRACK < users.length()) {
                        jsonObject = users.getJSONObject(TRACK);

                         if(jsonObject.getString("confirm").equals("y"))
                         {
                             //statues.setText();
                             //confirm=statues.getText().toString().trim();
                             Objrequest = new Request(jsonObject.getString("R_ID"),"أحمد",jsonObject.getString("R_dropoff_loc"),jsonObject.getString("r_time"),"مقبول");
                             listOfReq.add(Objrequest);
                             System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%yyyyyyy%%%%%%%%%%%");
                         }

                        else  if(jsonObject.getString("confirm").equals("n"))
                         {
                            // statues.setText("مرفوض");
                             //confirm=statues.getText().toString().trim();
                             Objrequest = new Request(jsonObject.getString("R_ID"),"أحمد",jsonObject.getString("R_dropoff_loc"),jsonObject.getString("r_time"),"مرفوض");
                             listOfReq.add(Objrequest);
                             System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%nnnnnnnn%%%%%%%%%%%%%%%%%%%%%%%%%");
                         }

                        else  if(jsonObject.getString("confirm").equals("w"))
                         {
                             //statues.setText("قيد الإنتظار");
                            // confirm=statues.getText().toString().trim();
                             Objrequest = new Request(jsonObject.getString("R_ID"),"أحمد",jsonObject.getString("R_dropoff_loc"),jsonObject.getString("r_time"),"قيد الإنتظار");
                             listOfReq.add(Objrequest);
                             System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%wwwwwww%%%%%%%%%%%%%%%%%%%%%%%%%%");

                         }

                        // else System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%else%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");



                         // counter++;
                          TRACK++;
                        }
//for (int i=0;i<listOfReq.size();i++){
  //  System.out.println(listOfReq.get(i).getReqNum() + "test data");

//}

              // spinner_fn();
                    requests.setAdapter(new mylistreq(getActivity(),R.layout.list_item_requests,listOfReq));

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


private class mylistreq extends ArrayAdapter<Request>{
   private int layout;
ArrayList<Request> array;
    public mylistreq(Context context, int resource, ArrayList<Request> objects) {
        super(context, resource, objects);
        layout=resource;
        array=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewlist viewlist=new Viewlist();
       // if(convertView==null){
          LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(layout,parent,false);

            for (int i = 0; i < listOfReq.size(); i++) {

            viewlist.requestNum=(TextView) convertView.findViewById(R.id.requestNumberRetrive);
             viewlist.driverName=(TextView) convertView.findViewById(R.id.DriverNameRetrive);
            viewlist.dropOffLocation=(TextView) convertView.findViewById(R.id.dropOffLRet);
            viewlist.dropOffTime=(TextView) convertView.findViewById(R.id.dropOffTRet);
            viewlist.statues=(TextView)convertView.findViewById(R.id.statusRet);
            viewlist.absent=(Button) convertView.findViewById(R.id.absent);
            viewlist.unSubscribe=(Button) convertView.findViewById(R.id.unSubscribe);

                viewlist.requestNum.setText(listOfReq.get(i).getReqNum());
               System.out.println(viewlist.requestNum.getText() + " //test data");
                viewlist.driverName.setText(listOfReq.get(i).getDriverN());
                viewlist.dropOffLocation.setText(listOfReq.get(i).getDropOffL());
                viewlist.dropOffTime.setText(listOfReq.get(i).getDropOffT());
                viewlist.statues.setText(listOfReq.get(i).getConfirm());

              //  convertView.setTag(viewlist);
          //  }
       }
       // else convertView.getTag();




        return convertView;
   }
}



    public class Viewlist {

        TextView requestNum;
        TextView driverName;
        TextView dropOffLocation;
        TextView dropOffTime;
        TextView statues;
        Button absent;
        Button unSubscribe;

    }
}





 /* public void spinner_fn() {


        dataAdapter = new ArrayAdapter<Request>(getActivity(), R.layout.list_item_requests, 0,listOfReq);
        requests.setAdapter(dataAdapter);

        for (int i = 0; i < listOfReq.size(); i++) {
            requestNum.setText(listOfReq.get(i).getReqNum());
            driverName.setText(listOfReq.get(i).getDriverN());
            dropOffLocation.setText(listOfReq.get(i).getDropOffL());
            dropOffTime.setText(listOfReq.get(i).getDropOffT());
            statues.setText(listOfReq.get(i).getConfirm());
        }


    }*/
  /*  public class mylistreq extends ArrayAdapter<String> {

        public mylistreq(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        public void spinner_fn() {


            dataAdapter = new ArrayAdapter<Request>(getActivity(), R.layout.list_item_requests, listOfReq);
            requests.setAdapter(dataAdapter);

               requestNum=(TextView) convertView.findViewById(R.id.requestNumberRetrive);
            driverName=(TextView) convertView.findViewById(R.id.DriverNameRetrive);
            dropOffLocation=(TextView) convertView.findViewById(R.id.dropOffLRet);
            dropOffTime=(TextView) convertView.findViewById(R.id.dropOffTRet);
            statues=(TextView)convertView.findViewById(R.id.statusRet);
            absent=(Button) convertView.findViewById(R.id.absent);
            unSubscribe=(Button) convertView.findViewById(R.id.unSubscribe);


        }
    }*/