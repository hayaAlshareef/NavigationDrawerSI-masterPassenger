package wassilni.pl.navigationdrawersi.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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
import java.util.concurrent.ExecutionException;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;


public class FragmentOne extends Fragment {
//GetAllReq

    public static final String MY_JSON ="getRequest";//MY_JSON
    String url_getReq="http://wassilni.com/db/GetAllReq.php";
    String sJson;
    String pathurl="http://wassilni.comdbdriverGetPassengers.php";
    private static final String JSON_ARRAY ="result";// the string must be the same as the name of the json object in the php file
    private static final String R_ID = "R_ID";// the string must be the same as the key name in the php file
    //private static final String time= "time";// the string must be the same as the key name in the php file
    //private static final String endDate = "endDate";
    private static final String D_F_Name ="D_F_Name";
    private static final String D_L_Name ="D_L_Name";
    private static final String R_dropoff_loc="R_dropoff_loc";
    private static final String r_time="r_time";
    private static final String confirm="confirm";
    private static final String picAdd ="pickAdd";
    private static final String dropAdd="dropAdd";
    private static final String D_ID="D_ID";
    private static final String Rating="Ratting";
    private int TRACK = 0;
    private JSONArray users = null;
    private JSONArray rating = null;
    String RID;
    ListView listView;
    int ids[];
    String D_IDs[];
    ArrayList<Request> RequestArrayList;
    RatingBar ratingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        RequestArrayList = new ArrayList<Request>();
        listView=(ListView)view.findViewById(R.id.listRequest);

        if (MyApp.passenger_from_session != null){// if there is data in the session
           // getJSON(url,"secound");
            GetJSON gj = new GetJSON();
            gj.execute(url_getReq,"getReq");
            System.out.println((MyApp.passenger_from_session!=null)? "********************* not null" : "************** null!");
        }
        else {
            System.out.println((MyApp.passenger_from_session != null) ? "ee********************* not null" : "ee************** null!");
            startActivity(new Intent(getActivity(), login.class));
            //getActivity().finish();
        }

        return view;
    }


    int i_ID;
    //show the one row from database
    private void showData(){
        try {

            JSONObject jsonObject;
          /*  String fullName = jsonObject.getString(FName)+" "+jsonObject.getString(LName);*/
            String id[] = new String[users.length()];
            String DFName,DLName, dropOffL, time,confirms,picupL,D_id;

            for (int i = 0; i < users.length(); i++) {
                jsonObject = users.getJSONObject(TRACK);
                i_ID = Integer.parseInt(jsonObject.getString(R_ID));
                DFName=jsonObject.getString(D_F_Name);
                DLName=jsonObject.getString(D_L_Name);
                dropOffL = jsonObject.getString(dropAdd);
                time = jsonObject.getString(r_time);
                confirms = jsonObject.getString(confirm);
                picupL=jsonObject.getString(picAdd);
                D_id=jsonObject.getString(D_ID);
                id[i] = jsonObject.getString(R_ID);
                if(confirms.equals("y")){
                    Request s = new Request(i_ID,D_id,DFName + " " +DLName ,dropOffL,time,picupL, "مقبول");
                    RequestArrayList.add(s);//add the object to array list
                }
                TRACK++;
            }

            ids=new int [TRACK];
            D_IDs=new String[TRACK];

            listView.setAdapter(new myList(getActivity(),  RequestArrayList));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    //extract the jason object
    private void extractJSON(){
        try {
            JSONObject jsonObject = new JSONObject(sJson);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            if(users.length()!=0)
                showData();
            else  Toast.makeText(getActivity(), "لا توجد إشتراكات حاليا", Toast.LENGTH_SHORT).show();

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





  //  private void getJSON(String url,String method) {
        class GetJSON extends AsyncTask<String ,Void,String> {
            ProgressDialog loading;
      String method;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                 method=params[1];

               if(method.equals("getReq")) {
                   BufferedReader bufferedReader = null;
                   try {
                       String P_ID = MyApp.passenger_from_session.getID() + "";
                       URL url = new URL(uri);//take the url for the php in case we want to retrives the driver schedule
                       HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                       httpURLConnection.setRequestMethod("POST");
                       httpURLConnection.setDoOutput(true);
                       httpURLConnection.setDoInput(true);
                       OutputStream outputStream = httpURLConnection.getOutputStream();
                       BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                       String data = URLEncoder.encode("P_ID", "UTF-8") + "=" + URLEncoder.encode(P_ID, "UTF-8");
                       bufferedWriter.write(data);
                       bufferedWriter.flush();
                       bufferedWriter.close();
                       outputStream.close();
                       InputStream Is = httpURLConnection.getInputStream();
                       bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                       String response = "";
                       String line = "";
                       while ((line = bufferedReader.readLine()) != null) {
                           response += line;
                       }
                       bufferedReader.close();
                       Is.close();
                       httpURLConnection.disconnect();
                       return response;
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
                else if(method.equals("getRatting")){


                   String did=params[2];
                   BufferedReader bufferedReader = null;
                   try {
                       String P_ID = MyApp.passenger_from_session.getID() + "";
                       URL url = new URL(uri);//take the url for the php in case we want to retrives the driver schedule
                       HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                       httpURLConnection.setRequestMethod("POST");
                       httpURLConnection.setDoOutput(true);
                       httpURLConnection.setDoInput(true);
                       OutputStream outputStream = httpURLConnection.getOutputStream();
                       BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                       String data = URLEncoder.encode("D_ID", "UTF-8") + "=" + URLEncoder.encode(did, "UTF-8");
                       bufferedWriter.write(data);
                       bufferedWriter.flush();
                       bufferedWriter.close();
                       outputStream.close();
                       InputStream Is = httpURLConnection.getInputStream();
                       bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                       String response = "";
                       String line = "";
                       while ((line = bufferedReader.readLine()) != null) {
                           response += line;
                       }
                       bufferedReader.close();
                       Is.close();
                       httpURLConnection.disconnect();
                       return response;
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

               }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                sJson=s;
             //   Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                if(method.equals("getReq")){
                extractJSON();
               }

            }
        }

 //   }//end getJson class

    class myList extends BaseAdapter  {
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
        String ratingValue;



        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(context);
            View row=inflater.inflate(R.layout.list_item_requests,parent,false);
            TextView requestNum=(TextView) row.findViewById(R.id.requestNumberRetrive);
            TextView driverName=(TextView) row.findViewById(R.id.DriverNameRetrive);
            TextView dropOffLocation=(TextView) row.findViewById(R.id.dropOffLRet);
            TextView dropOffTime=(TextView) row.findViewById(R.id.dropOffTRet);
            TextView statues=(TextView)row.findViewById(R.id.statusRet);
            TextView pickuppAdd=(TextView)row.findViewById(R.id.pickup);
            Button absent=(Button) row.findViewById(R.id.absent);
            Button unSubscribe=(Button) row.findViewById(R.id.unSubscribe);
             ratingBar=(RatingBar)row.findViewById(R.id.ratingBar1);


            temp=items.get(position);

            req_id=temp.getReqNum();

            ids[position]=temp.getReqNum();//to take the id for the request

            D_IDs[position]=temp.getD_ID();



            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    final String methods = "addRating";

                    ratingValue=rating+"";
                    backgroundTask b = new backgroundTask(getActivity());
                    try {
                        String result =  b.execute(methods, ratingValue,D_IDs[position]).get();
                        if(result.contains("Rating Added Successfully")){

                            Toast.makeText(getContext()," شكرا لتقيميك للسائق",Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    System.out.println(ratingValue+" rrrrrrratting ");
                }
            });




            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Toast.makeText(context,ids[position],Toast.LENGTH_SHORT).show(); //get request id to send absent to her driver
                     Intent i=new Intent(getActivity(), Absent.class);
                    String rID=ids[position]+"";
                    i.putExtra("R_ID",rID);//json will be parsed in the Absent activity.
                    startActivity(i);

                    // System.out.println("id in saidd passenger "+ids[position]);
                }
            });
            unSubscribe.setText("إلغاء الإشتراك");
            unSubscribe.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("تأكيد إلغاء الإشتراك")
                            .setMessage("هل أنت متأكد من إلغاءإشتراكك؟")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                               /*
                               * Here put the code for deleting the request !!!!!!!!!!!!
                               * */
                                    String res= "";
                                    String method = "delReq";
                                    backgroundTask bc = new backgroundTask(getActivity());
                                    try {
                                        res=bc.execute(method,ids[position]+"").get();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    if(res.equals("request Deleted Successfully")){
                                        Toast.makeText(getContext(),"تم إلغاء الإشتراك",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(),"حدث خطأ أثناء إلغاء الإشتراك , الرجاء المحاولة في وقت أخر",Toast.LENGTH_SHORT).show();

                                    }
                                }})
                            .setNegativeButton(android.R.string.no, null).show();


                }
            });


            requestNum.setText(temp.getReqNum()+"");
            driverName.setText(temp.getDriverN());
            dropOffLocation.setText(temp.getDropOffL());
            dropOffTime.setText(temp.getDropOffT());
            statues.setText(temp.getConfirm());
            statues.setTextColor(getResources().getColor(R.color.green_dark));
            pickuppAdd.setText(temp.getPickupAdd());
            return row;
        }


    }
}
