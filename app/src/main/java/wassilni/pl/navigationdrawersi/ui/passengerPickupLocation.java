package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import wassilni.pl.navigationdrawersi.R;

public class passengerPickupLocation extends FragmentActivity implements OnMapReadyCallback {

    /*
     comment by haya...
    * two ways to specify the passenger location:
    *  1. to get the passenger's location using GPS.
    *  2. to enable the passenger to specify her own location on the map.
    *
    *  What to consider in this class!!? :
    *  user's location must be saved in the DB.
    *  if user has request to a driver, user cannot change his location.
    * */

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener listener;
    private LatLng userLocation;
    private LatLng currentLocation=null;
    boolean useGPS = true;
    private Button gpsButton;
    //private TextView locStatus;
    private Marker m;
TextView tvInfo;
    public String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_pickup_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvInfo=(TextView)findViewById(R.id.tvInfo);

        Intent i= getIntent();
        type=i.getStringExtra("Type");
        if(type.equalsIgnoreCase("pickup"))
        {
            tvInfo.setText("الرجاء تحديد مكان البدء");
        }
        else
            tvInfo.setText("الرجاء تحديد مكان الوصول");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                // prompt the user to turn on GPS service.
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        //locStatus = (TextView) findViewById(R.id.locationStatus);
        gpsButton = (Button) findViewById(R.id.useGPS);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //useGPS = true;
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(getApplicationContext(),"الرجاء تفعيل أذونات الموقع من الإعدادات",Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(passengerPickupLocation.this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                            0 );//*/
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        if(currentLocation==null)
        {
            Toast.makeText(getApplicationContext(),"الرجاء الإنتظار حتى يتم الإتصال بخدمة الموقع",Toast.LENGTH_SHORT).show();
        }
                else {
    userLocation = currentLocation;
    setMarker();
    if (type.equalsIgnoreCase("pickup")) {
        FragmentTwo.pickupET.setText(extractLatLng(userLocation));
        Toast.makeText(getApplicationContext(), "تم تحديد مكان البدء", Toast.LENGTH_LONG).show();
    } else {
        FragmentTwo.dropoffET.setText(extractLatLng(userLocation));
        Toast.makeText(getApplicationContext(), "تم تحديد مكان الوصول", Toast.LENGTH_LONG).show();
    }
}
            }

        });
        // Prompt the user to select among two methods. then walk her through the procedures.


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Riyadh = new LatLng(24.6796205, 46.6981272);
        //mMap.addMarker(new MarkerOptions().position(Riyadh).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Riyadh, 10));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                 //   Log.d("passengerPic", "in map onCLICKLISTNER @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    // TODO Auto-generated method stub
                    //double lat = point.latitude;
                    //double lng = point.longitude;

                    userLocation=point;
                    setMarker();
                    if(type.equalsIgnoreCase("pickup"))
                    {
                        FragmentTwo.pickupET.setText(extractLatLng(userLocation));
                        Toast.makeText(getApplicationContext(),"تم تحديد مكان البدء",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        FragmentTwo.dropoffET.setText(extractLatLng(userLocation));
                        Toast.makeText(getApplicationContext(),"تم تحديد مكان الوصول",Toast.LENGTH_LONG).show();
                    }
                }
            });

}
    public void setMarker()
    {
        /*
        * This method will set marker on the user's location, created to be called in a single line since it'll be called twice.
        * */
        if (m != null)
            m.remove();
        {
            if (userLocation != null) {
                m = mMap.addMarker(new MarkerOptions().position(userLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
                //locStatus.setText(userLocation.toString());
            } else
                Toast.makeText(getApplicationContext(), "Please specify your pickup location", Toast.LENGTH_SHORT).show();
        }
    }
    public static String extractLatLng(LatLng s){
        String str=s.toString().substring(s.toString().indexOf('(',0)+1,s.toString().indexOf(')',0));
        str= str.replaceAll("\\s+","");

        return str;
    }
}
