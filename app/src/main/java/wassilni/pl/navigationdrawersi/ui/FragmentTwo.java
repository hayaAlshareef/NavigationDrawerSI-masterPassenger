package wassilni.pl.navigationdrawersi.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import Objects.MyApp;
import wassilni.pl.navigationdrawersi.R;

public class FragmentTwo extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {

   // @InjectView(R.id.circleLayout)
    //LinearLayout circleLayout;
   Button pickupB,dropoffB,searchB,dateStartB,dateEndB;
    public static EditText pickupET,dropoffET,sDateET,eDateET;
    EditText pickupAdd,dropoffAdd;
    static boolean CheckButton;//to know witch pickup date the user click
    public static String sDate;
    public static String eDate;
    public static String time,hourS, mintS;
    public static String pLoc,pAdd;
    public static String dLoc,dAdd;
    private Request r ;
    Spinner hourSpinner, mintSpinner;
    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, containter, false);
        hourSpinner = (Spinner) view.findViewById(R.id.spinnerHour);
        hourSpinner.setOnItemSelectedListener(this);
        mintSpinner = (Spinner) view.findViewById(R.id.spinnerMint);
        mintSpinner.setOnItemSelectedListener(this);

          searchB=(Button) view.findViewById(R.id.searchb);
          pickupB=(Button) view.findViewById(R.id.pickupB);
          dropoffB=(Button) view.findViewById(R.id.dropoffB);
        dateStartB=(Button) view.findViewById(R.id.datepickerB);
         dateEndB=(Button) view.findViewById(R.id.datepickerB1);
        dateStartB.setOnClickListener(this);
        dateEndB.setOnClickListener(this);
        sDateET=(EditText) view.findViewById(R.id.StartingDateET);
        eDateET=(EditText) view.findViewById(R.id.endingDateTE);
        pickupET=(EditText) view.findViewById(R.id.pickupET);
        dropoffET=(EditText) view.findViewById(R.id.dropOffLET);
        pickupET.setEnabled(false);
        dropoffET.setEnabled(false);
        pickupAdd=(EditText) view.findViewById(R.id.pickupAddET);
        dropoffAdd=(EditText) view.findViewById(R.id.dropoffAddET);
        sDateET.addTextChangedListener(this);
        eDateET.addTextChangedListener(this);
        pickupET.addTextChangedListener(this);
        dropoffET.addTextChangedListener(this);

        ///to open search page --- before user click this button , user must be enter all requierd fields then we will send it to next page to get result
        // user can select one or two from result then click requiest : 1-the requ display it in fragment one page(with wait status) , 2-the req send it to rhe driver
        //in the search page the user can view any driver page

        searchB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(validateInput())  {// if not empty -----------------------------------------------------------
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

    private boolean validateInput(){

        boolean result =true;


        if(dropoffET.getText().toString().length()==0)     //size as per your requirement
        {
            dropoffET.setError("يجب عليك إختيار وجهتك ");
            result=false;

        }
        if(pickupET.getText().toString().length()==0)     //size as per your requirement
        {
            pickupET.setError("يجب عليك إختيار مكان الإنطلاق  ");
            result=false;

        }

        if(sDateET.getText().toString().length()==0)     //size as per your requirement
        {
            sDateET.setError("يجب عليك إدخال تاريخ بداية الإشتراك");
            result=false;

        }
        if(eDateET.getText().toString().length()==0)     //size as per your requirement
        {
            eDateET.setError("يجب عليك إدخال تاريخ نهاية الإشتراك");
            result=false;

        }

        if(eDateET.getText().toString().length()>=10) {
            //  matcher = Pattern.compile(DATE_PATTERN).matcher(et_DOB.getText().toString());
            Date d=null;
            String value = eDateET.getText().toString().trim();
            if (value.length() != 0) {
                try {


                    d = spf.parse(value);
                    if (!value.equals(spf.format(d))) {//if the date not match the date format
                        d = null;
                    }
                }catch(ParseException ex){
                    ex.printStackTrace();
                }
                if (d == null) {
                    // Invalid date format
                    eDateET.setError("التنسيق اليوم غير صحيح يجب ان يكون 1994-29-12");
                    result = false;
                }
            }}
            if(sDateET.getText().toString().length()>=10) {
                //  matcher = Pattern.compile(DATE_PATTERN).matcher(et_DOB.getText().toString());
                Date d=null;
                String value = sDateET.getText().toString().trim();
                if (value.length() != 0) {
                    try {


                        d = spf.parse(value);
                        if (!value.equals(spf.format(d))) {//if the date not match the date format
                            d = null;
                        }
                    }catch(ParseException ex){
                        ex.printStackTrace();
                    }
                    if (d == null) {
                        // Invalid date format
                        sDateET.setError("التنسيق اليوم غير صحيح يجب ان يكون 1994-29-12");
                        result = false;
                    }
                }
        }

        return result;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ButterKnife.reset(this);
    }

    public String getFields()
    {
        /*
        * I think i need to re-format DATE , And TIME !!!!!!!
        * either in php , or here before sending them to the PHP page.*/
        //if all fields has values
        sDate = sDateET.getText().toString();
        eDate = eDateET.getText().toString();
        time =hourS+":"+mintS+":"+"00";
        pLoc = pickupET.getText().toString();
        dLoc = dropoffET.getText().toString();
        pAdd=pickupAdd.getText().toString().trim();
        dAdd=dropoffAdd.getText().toString().trim();


        r=new Request(sDate,eDate, pLoc,dLoc,time,pAdd,dAdd);
        System.out.println(pAdd+" adddddddres"+r.getPickupL());
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
        //2016-12-28	2017-01-20	11:00:00	24.678747461415842,46.688804626464844	24.726262143865004,46.63715735077858
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.datepickerB || v.getId() == R.id.datepickerB1) {

            if (v.getId() == R.id.datepickerB) {
                CheckButton = true;
                System.out.println(CheckButton);
            } else if (v.getId() == R.id.datepickerB1) {
                CheckButton = false;
                System.out.println(CheckButton);
            }
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getFragmentManager(), "DatePicker");


        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        TextView myText = (TextView) view;
      /*  ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) adapterView.getChildAt(0)).setTextSize(18);*/
        if(spinner.getId()==R.id.spinnerHour){
            hourS = (String) myText.getText();
        }
        else if(spinner.getId()==R.id.spinnerMint){
            mintS = (String) myText.getText();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        validateInput();

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        validateInput();

    }


    @SuppressLint("ValidFragment")
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        int yy, mm, dd;
        String date;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
            yy = calendar.get(android.icu.util.Calendar.YEAR);
            mm = calendar.get(android.icu.util.Calendar.MONTH);
            dd = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);


            populateSetDate(calendar);
        }

        public void populateSetDate(final Calendar calendar) {
            Date newDate = calendar.getTime();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            date = spf.format(newDate);

            if (CheckButton == true)
                sDateET.setText(date);
            else
                eDateET.setText(date);

        }

    }

    public Request getRequest(){

        return r;
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
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
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
                    outputStream.close();
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
                    System.out.println("result in fragment 2******* "+result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            System.out.println("######################################### Internetttttttt");
            return "InternetFailed";
        }

        @Override
        protected void onPostExecute(String a )
        {
            super.onPreExecute();
        }




    }// end class AsyncTask




