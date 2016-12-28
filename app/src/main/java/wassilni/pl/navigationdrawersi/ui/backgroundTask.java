package wassilni.pl.navigationdrawersi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hp on 10/11/16.
 */

public class backgroundTask extends AsyncTask<String ,Void,String> {
    private AppCompatActivity appCompatActivity;
    private Context ctx;
   // private ProgressDialog progressDialog;/////
   private int c;

    backgroundTask(Context ctx){
        this.ctx=ctx;
        appCompatActivity= (AppCompatActivity)new AppCompatActivity();
    }
    @Override
    protected void onPreExecute() {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");
    }

    @Override
    protected String doInBackground(String... voids) {
        String req_url= "http://wassilni.com/db/addPass.php";//register url
        String login_url="http://wassilni.com/db/loginPassenger.php";//login url
        String delete_url="http://wassilni.com/db/DelPass.php";//delete
        String sendComplaint_url="http://wassilni.com/db/addComplaint.php"; //send complaint
        String update_url="http://wassilni.com/db/UpdatePass.php"; // update passenger info
        String DelReq_url="http://wassilni.com/db/DelReq.php"; // delete request
        String addReq_url="http://wassilni.com/db/AddReq.php";
        String method = voids[0];
        Log.d("Background","before comparison @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if(method.equals("register"))
        {
            String fName ,lName , email , password , phone , school;

            fName =voids[1];
            lName =voids[2];
            email =voids[3]; 
            password =voids[4];
            phone =voids[5];
            school =voids[6];
            //nationality =voids[7];
            //driverNum =voids[8];
            try {
                URL url = new URL(req_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("fName","UTF-8")+"="+URLEncoder.encode(fName,"UTF-8")+"&"+
                        URLEncoder.encode("lName","UTF-8")+"="+URLEncoder.encode(lName,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("school","UTF-8")+"="+URLEncoder.encode(school,"UTF-8");
                       //URLEncoder.encode("nationality","UTF-8")+"="+URLEncoder.encode(nationality,"UTF-8")+"&"+
                       // URLEncoder.encode("driverNum","UTF-8")+"="+URLEncoder.encode(driverNum,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is=httpURLConnection.getInputStream();
                Is.close();
                return "Registration success";
                        
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (method.equals("login")){

System.out.println("$$$$$$$$$$$$$$$   In Login !!!!!");
            String login_name=voids[1];
            String login_pass=voids[2];
            try{
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                String data=URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream Is=httpURLConnection.getInputStream();
                BufferedReader buffredReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=buffredReader.readLine())!=null){
                    response+=line;
                }
                buffredReader.close();
                Is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
        else if (method.equals("delete")) {
            String id=voids[1];
            try{
                URL url = new URL(delete_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                String data=URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream Is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                Is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//end delete if
        else if (method.equals("addReq")) {
            String S_ID=voids[1];
            String P_ID=voids[2];
            String picLocation = voids[3];
            String dropLocation = voids[4];
            String time = voids[5];
            String startD = voids[6];
            String endD = voids[7];
            String D_ID = voids[8];
            try{
                URL url = new URL(addReq_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                String data=URLEncoder.encode("S_ID","UTF-8")+"="+URLEncoder.encode(S_ID,"UTF-8")+"&"+
                        URLEncoder.encode("P_ID","UTF-8")+"="+URLEncoder.encode(P_ID,"UTF-8")+"&"+
                        URLEncoder.encode("D_ID","UTF-8")+"="+URLEncoder.encode(D_ID,"UTF-8")+"&"+
                        URLEncoder.encode("R_pickup_loc","UTF-8")+"="+URLEncoder.encode(picLocation,"UTF-8")+"&"+
                        URLEncoder.encode("R_dropoff_loc","UTF-8")+"="+URLEncoder.encode(dropLocation,"UTF-8")+"&"+
                        URLEncoder.encode("r_time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+
                        URLEncoder.encode("R_starting_Date","UTF-8")+"="+URLEncoder.encode(startD,"UTF-8")+"&"+
                        URLEncoder.encode("R_ending_Date","UTF-8")+"="+URLEncoder.encode(endD,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream Is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                Is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//end delete if
        else if(method.equals("sendComplaint")){
            String D_ID=voids[1];
            String complaint=voids[2];
            Log.d("Background","complaint"+ complaint);
            String passenger_ID=voids[3];
            String time=voids[4];
            String sender="P";
            try{
                URL url = new URL(sendComplaint_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                String data=URLEncoder.encode("D_ID","UTF-8")+"="+URLEncoder.encode(D_ID,"UTF-8")+"&"+
                        URLEncoder.encode("passenger_ID","UTF-8")+"="+URLEncoder.encode(passenger_ID,"UTF-8")+"&"+
                       // URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+
                        URLEncoder.encode("sender","UTF-8")+"="+URLEncoder.encode(sender,"UTF-8")+"&"+
                        URLEncoder.encode("complaint","UTF-8")+"="+URLEncoder.encode(complaint,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream Is=httpURLConnection.getInputStream();
                BufferedReader buffredReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=buffredReader.readLine())!=null){
                    response+=line;
                }
                buffredReader.close();
                Is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }// end sendComplaint if
        else if (method.equals("update")){
            String id, fName ,lName , email , password , phone , school;
            id=voids[1];
            fName =voids[2];
            lName =voids[3];
            email =voids[4];
            password =voids[5];
            phone =voids[6];
            school =voids[7];
            try {
                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data =URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("fName","UTF-8")+"="+URLEncoder.encode(fName,"UTF-8")+"&"+
                        URLEncoder.encode("lName","UTF-8")+"="+URLEncoder.encode(lName,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("school","UTF-8")+"="+URLEncoder.encode(school,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                httpURLConnection.disconnect();
                return "update success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } // end update if
        else if(method.equals("delReq")){
            String R_ID=voids[1];
            try{
                URL url = new URL(DelReq_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter (outputStream,"UTF-8"));
                String data=URLEncoder.encode("R_ID","UTF-8")+"="+URLEncoder.encode(R_ID,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream Is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(Is,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                Is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;}
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
   }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Registration success")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

           // Intent i=new Intent(appCompatActivity.getApplicationContext(), FragmentOne.class);
            //appCompatActivity.startActivity(i);

        }

         else {
          // alertDialog.setMessage(result);
           // alertDialog.show();
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
           if(result.equals("Login success ... welcome ")){

                Intent i=new Intent(appCompatActivity.getApplicationContext(), FragmentOne.class);
            appCompatActivity.startActivity(i);
        }
        }

    }

}
