package wassilni.pl.navigationdrawersi.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;


public class FragmentThree extends Fragment {

    public static final String MY_JSON ="getRequest";//MY_JSON
    String url="http://wassilni.com/db/GetAllReq.php";
    String sJson;
    private static final String JSON_ARRAY ="result";// the string must be the same as the name of the json object in the php file
    private static final String R_ID = "R_ID";// the string must be the same as the key name in the php file
    private static final String D_ID ="D_ID";
    private static final String R_dropoff_loc="R_dropoff_loc";
    private static final String r_time="r_time";
    private static final String confirm="confirm";
    private int TRACK = 0;
    private JSONArray users = null;
    ListView listView;
    int ids[];
    ArrayList<Request> RequestArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        RequestArrayList = new ArrayList<Request>();
        listView=(ListView)view.findViewById(R.id.listRequest);

        getJSON(url); //call json
        return view;
    }


    int i_ID;
    //show the one row from database
    private void showData(){
        try {

            JSONObject jsonObject;
            String id[] = new String[users.length()];
            String dropOffL, time,confirms;
            int DID;
            for (int i = 0; i < users.length(); i++) {
                jsonObject = users.getJSONObject(TRACK);
                i_ID = Integer.parseInt(jsonObject.getString(R_ID));
                DID=Integer.parseInt(jsonObject.getString(D_ID));
                dropOffL = jsonObject.getString(R_dropoff_loc);
                time = jsonObject.getString(r_time);
                confirms = jsonObject.getString(confirm);
                id[i] = jsonObject.getString(R_ID);
                if(confirms.equals("w")){
                    Request s = new Request(i_ID,DID,dropOffL, time,"قيد الإنتظار");
                    RequestArrayList.add(s);//add the object to array list
                }
                else if(confirms.equals("n")){
                    Request s = new Request(i_ID,DID,dropOffL, time,"مرفوض");
                    RequestArrayList.add(s);//add the object to array list
                }
                TRACK++;
            }
            ids=new int [TRACK];
            listView.setAdapter(new FragmentThree.myList(getActivity(),  RequestArrayList));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //extract the jason object
    private void extractJSON(){
        try {
            JSONObject jsonObject = new JSONObject(sJson);
            users = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class Background extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //loading = ProgressDialog.show(getActivity(), "Please Wait...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params)
        {
            String uri="http://wassilni.com/db/GetAllReq.php";

//the data will be parsed twice, once in MapActivity and once in "passengers registered"
            if(MyApp.isInternetAvailable()) {
                BufferedReader bufferedReader = null;
                try {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ params[0]:"+params[0]);
                    URL url = new URL(uri);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    //[0]type  [1]value
                    String data;
                    //if(params[0].equalsIgnoreCase("ID"))
                    data = URLEncoder.encode("R_ID", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                    //Log.d("F1",data);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in backgroundTask 222222");
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    InputStream Is = httpURLConnection.getInputStream();
                    Is.close();
                    httpURLConnection.disconnect();
                    String result = sb.toString().trim();
                    //System.out.println(result);
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in backgroundTask after parsing data");
                    System.out.println("result in fragment 4******* "+result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            System.out.println("######################################### Intermetttttttt");
            return "InternetFailed";
        }

        @Override
        protected void onPostExecute(String a )
        {
            super.onPreExecute();
        }

    }// end class AsyncTask





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
                    String P_ID = MyApp.passenger_from_session.getID()+"";
                    URL url = new URL(uri);//take the url for the php in case we want to retrives the driver schedule
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                    String data= URLEncoder.encode("P_ID","UTF-8")+"="+URLEncoder.encode(P_ID,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream Is=httpURLConnection.getInputStream();
                    bufferedReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                    String response="";
                    String line="";
                    while ((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }
                    bufferedReader.close();
                    Is.close();
                    httpURLConnection.disconnect();
                    return response;
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                sJson=s;
               // Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                extractJSON();
                showData();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }//end getJson class

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
        int req_id;

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
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
            req_id=temp.getReqNum();

            ids[position]=temp.getReqNum();//to take the id for the request


            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context,"absent"+ids[position],Toast.LENGTH_SHORT).show(); //get request id to send absent to her driver
                    // Intent i=new Intent(getActivity(), passenger.class);
                    //String rID=ids[position]+"";
                    //i.putExtra("R_ID",rID);//json will be parsed in absent activity.
                    //startActivity(i);

                    // System.out.println("id in saidd passenger "+ids[position]);
                }
            });
            unSubscribe.setText("إلغاء الطلب");
            unSubscribe.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"unsubscribe"+ids[position],Toast.LENGTH_SHORT).show(); //get request id to send unSubscribe to her driver
                    // query and parseJSON, then go to mapActivity.

                    //  System.out.println("schedula id "+ids[position]);
                    // Background b= new Background();
                    // try {
                    //   String result=b.execute(ids[position]+"").get();
                    // if(!result.equalsIgnoreCase("InternetFailed")){
                    //    Intent i=new Intent(getActivity(), MapsActivity.class);
                    //   i.putExtra("json",result);//json will be parsed in the map activity.
                    //   startActivity(i);
                    //this.finish();
                    //  }
                    // else
                    //  Toast.makeText(getActivity(),"ارجو التأكد من توصيل الانترنت",Toast.LENGTH_LONG).show();
                    // } catch (InterruptedException e) {
                    //  e.printStackTrace();
                    // } catch (ExecutionException e) {
                    //    e.printStackTrace();
                    // }

                }
            });

            requestNum.setText(temp.getReqNum()+"");
            driverName.setText(temp.getDriverN()+"");
            dropOffLocation.setText(temp.getDropOffL());
            dropOffTime.setText(temp.getDropOffT());
            statues.setText(temp.getConfirm());



            return row;
        }


    }
}
