package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;

public class FragmentTwo extends Fragment {

   // @InjectView(R.id.circleLayout)
    //LinearLayout circleLayout;
   Button pickupB,dropoffB,searchB;
    public static EditText pickupET,dropoffET,sDateET,eDateET,timeET;

    public String sDate;
    public String eDate;
    public String time;
    public String pLoc;
    public String dLoc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, containter, false);
          searchB=(Button) view.findViewById(R.id.searchb);
          pickupB=(Button) view.findViewById(R.id.pickupB);
          dropoffB=(Button) view.findViewById(R.id.dropoffB);
        sDateET=(EditText) view.findViewById(R.id.StartingDateET);
        eDateET=(EditText) view.findViewById(R.id.endingDateTE);
        timeET=(EditText) view.findViewById(R.id.timeET);
        pickupET=(EditText) view.findViewById(R.id.pickupET);
        dropoffET=(EditText) view.findViewById(R.id.dropOffLET);
        pickupET.setEnabled(false);
        dropoffET.setEnabled(false);
        ///to open search page --- before user click this button , user must be enter all requierd fields then we will send it to next page to get result
        // user can select one or two from result then click requiest : 1-the requ display it in fragment one page(with wait status) , 2-the req send it to rhe driver
        //in the search page the user can view any driver page
        searchB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if((sDateET.getText().toString()!="") &&
                         (eDateET.getText().toString()!= "") &&
                         (timeET.getText().toString()!="") &&
                         (pickupET.getText().toString()!="")&&
                         (dropoffET.getText().toString()!="")) {
                     String json=getFields();
                     if(json!="")
                     {
                         Intent i = new Intent(getActivity(), Search.class);
                         i.putExtra("json",json);
                         startActivity(i);
                     }
                 }
                 else
                     Toast.makeText(getContext(),"الرجاء ملء جميع الخانات",Toast.LENGTH_SHORT).show();


             }
         });
        pickupB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(getActivity(),passengerPickupLocation.class);
                 i.putExtra("Type","pickup");
                 startActivity(i);
             }
         });

        dropoffB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),passengerPickupLocation.class);
                i.putExtra("Type","dropoff");
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ButterKnife.reset(this);
    }

    public String getFields()
    {
        //if all fields has values
        sDate = sDateET.getText().toString();
        eDate = eDateET.getText().toString();
        time = timeET.getText().toString();
        pLoc = pickupET.getText().toString();
        dLoc = dropoffET.getText().toString();
        String result="";
        Background b =new Background();
        try {
            result =b.execute(sDate,eDate,time,pLoc,dLoc).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO  "+sDate+"\t"+eDate+"\t"+time+"\t"+pLoc+"\t"+dLoc);
        return result;
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
            String uri="http://wassilni.com/db/requestMatching.php";

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
                    data = URLEncoder.encode("SDate","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8")+"&"+
                            URLEncoder.encode("EDate","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"+
                            URLEncoder.encode("Time","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"+
                            URLEncoder.encode("pickup","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"+
                            URLEncoder.encode("dropoff","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8");
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



}
