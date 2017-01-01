package Objects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by Najla AlHazzani on 11/19/2016.
 */

public class Passenger {
    private int ID;
    private String FName;
    private String LName;
    private String Passwrod;
    private String Email;
    private String Phone;
    private String School;

    //**************************************** Constructor(S) ▼▼▼▼▼

    public Passenger(){}//default Constructor




    //  **************************************** service method HERE ▼▼▼▼▼.

    //public void EditPassengerInfo(all passenger attributes){}

    public boolean checkPassword(String email){
        // this method will ......!?
        return false;
    }

    public void editProfile(int P_ID){
        //this method will edit passenger profile in the DB.

    }

    public Passenger getPassengerInfo(int R_ID){
        // this method will !!!!!!
        return null;
    }

    public Driver viewDriverInfo(int D_ID){
        // this method will retreive driver's info based on D_ID.
        return null;
    }



    public boolean deleteAccount(int P_ID){
        //this method will delete passenger account in DB.
        return false;
    }

    public Passenger getPassengerInfo(){
        // ..........!?!?!?
        return null;
    }

    public void getPassengerFromDB(String email)
    {
        String a = getJSON("Email",email,null);
    }

    private String getJSON(String type, String value,Activity a) {

        ProgressDialog loading;
        // final Driver[] driv = new Driver[1];
        class GetJSON extends AsyncTask<String, Void, String> {
            //ProgressDialog loading;
            private Activity activity;
            public GetJSON(Activity a){
                super();
                activity=a;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getActivity(), "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {
                //super.doInBackground(params);
                String uri="http://wassilni.com/db/getPass.php";

                BufferedReader bufferedReader = null;
                try {
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in backgroundTask 11111111");
                    URL url = new URL(uri);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    //[0]type  [1]value
                    String data;
                    if(params[0].equalsIgnoreCase("ID"))
                        data = URLEncoder.encode("P_ID","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8");
                    else //if (params[0].equalsIgnoreCase("email"))
                        data = URLEncoder.encode("P_Email","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8");
                    //Log.d("F1",data);
                    System.out.println("in backgroundTask printing data********************************************"+data);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in backgroundTask 222222");
                    bufferedReader = new BufferedReader(new InputStreamReader( httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    InputStream Is=httpURLConnection.getInputStream();
                    Is.close();
                    httpURLConnection.disconnect();
                    String result= sb.toString().trim();
                    System.out.println(result);
                    parseData(result);
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in backgroundTask after parsing data");
                    return result;

                }catch(IOException e){
                    e.printStackTrace();
                    return null;}
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //parseData(s);
                System.out.println("************************************* OnPostExecute Finished now!");
            }
        }
        GetJSON gj = new GetJSON(a);
        String driverFlag="failed";
        //System.out.println("^^^^^^^^^^^^^^^^^"+driverFlag);
        try {
            driverFlag=gj.execute(type,value).get();
            return driverFlag;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return driverFlag;//*/
        //return " O ";
    }

    private void parseData(String s)
    {
        //{"result":[{"P_ID":"1","P_Email":"arwa@gmail.com","P_F_Name":"arwa","P_L_Name":"abdulaziz","P_Phone":"556693340","school":""}]}
        Passenger p = new Passenger();
        try {

            JSONArray auser= null;
            JSONObject j = new JSONObject(s);
            auser=j.getJSONArray("result");
            JSONObject jsonObject=auser.getJSONObject(0);

            //String fullName = jsonObject.getString(FName)+" "+jsonObject.getString(LName);
            String JSON_ARRAY ="result";// the string must be the same as the name of the json object in the php file
            String p_id= "P_ID";// the string must be the same as the key name in the php file
            String FName = "P_F_Name";// the string must be the same as the key name in the php file
            String LName= "P_L_Name";// the string must be the same as the key name in the php file
            String pass= "Password";// the string must be the same as the key name in the php file
            String phoneNum = "P_Phone";// the string must be the same as the key name in the php file
            String email= "P_Email";// the string must be the same as the key name in the php file
            String school= "school";// the string must be the same as the key name in the php file

            p.setID(jsonObject.getInt(p_id));
            System.out.println(p.getID());
            p.setEmail(jsonObject.getString(email));
            //System.out.println("***************************************************"+jsonObject.getString(email));
            p.setFName(jsonObject.getString(FName));
            p.setLName(jsonObject.getString(LName));
            p.setPasswrod(jsonObject.getString(pass));
            p.setPhone(jsonObject.getString(phoneNum));
            p.setSchool(jsonObject.getString(school));

            //System.out.println(d.Email+"\t'ffffffffffffffffffffffffffffffffffffffffffff");
            MyApp.passenger_from_DB= p;
            System.out.println(" ################ --1-- IN Parse Data class "+MyApp.passenger_from_DB.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void retrieveSession(SharedPreferences spRetrieve) {
        Passenger pass =null;// new Driver();
        System.out.println("استرجاع سييييييييييييشششششششننن");
        //From XML to MyApp.pass.
        pass = new Passenger();
        pass.setID(spRetrieve.getInt("ID", -1));
        pass.setEmail(spRetrieve.getString("Email", ""));
        pass.setFName(spRetrieve.getString("FName", ""));
        pass.setLName(spRetrieve.getString("LName", ""));
        pass.setPasswrod(spRetrieve.getString("password", ""));
        pass.setPhone(spRetrieve.getString("Phone", ""));
        pass.setSchool(spRetrieve.getString("School", ""));

        MyApp.passenger_from_session=pass;

        System.out.println(" ################ --2-- IN RetreiveSession Method "+MyApp.passenger_from_session.toString());

        //set MyApp.passenger_from_session;

    }//retreive session

    public static void createSession(SharedPreferences sp)
    {

        SharedPreferences.Editor editor = sp.edit();
        editor = editor.clear();//to make sure there's no duplicates.
        System.out.println("ادخال السيييييييييششششششششن ");
        // driver photo.
        // edit ::::: SECOND PARAMETER WILL BE taken from the DB. **************************************************
        editor.putInt("ID", MyApp.passenger_from_DB.getID());
        editor.putString("Email", MyApp.passenger_from_DB.getEmail());
        editor.putString("FName", MyApp.passenger_from_DB.getFName());
        editor.putString("LName", MyApp.passenger_from_DB.getLName());
        editor.putString("password", MyApp.passenger_from_DB.getPasswrod());
        editor.putString("Phone", MyApp.passenger_from_DB.getPhone());
        editor.putString("School", MyApp.passenger_from_DB.getSchool());

        MyApp.passenger_from_session=MyApp.passenger_from_DB;

        editor.commit();
        System.out.println(" ################ --3-- IN createSession Method "+MyApp.passenger_from_session.toString());
        //set MyApp.passenger_from_session;

    }//create session

    //**************************************** SETTERS &&& GETTERS HERE ▼▼▼▼▼


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPasswrod() {return Passwrod;}

    public void setPasswrod(String passwrod) {Passwrod = passwrod;}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String toString()
    {
        return FName+" "+LName+" "+Email+" "+Phone+" "+Passwrod;
    }
}
