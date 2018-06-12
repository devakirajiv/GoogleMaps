//package com.example.rajivd7435.devakimapsapprepo;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Address;
//import android.location.Criteria;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.location.LocationProvider;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//
//    private EditText locationSearch;
//    private Location myLocation;
//    private LocationManager locationManager;
//    private boolean gotMyLocationOneTime;
//    private boolean isGPSEnabled;
//    private boolean isNetworkEnabled;
//    private boolean notTrackingMyLocation = true;
//    private boolean trackingMyLocation = true;
//
//
//
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.0f;
//    private static final int MY_LOC_ZOOM_FACTOR = 17;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        // LatLng sydney = new LatLng(-34, 151);
//        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//
//        // add a marker at your place of birth and move the camera to it
//        //when the marker is tapped, display "born here"
//        LatLng birth = new LatLng(32.7, -117.2);
//        mMap.addMarker(new MarkerOptions().position(birth).title("Born here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(birth));
//
//       /** if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("GoogleMaps", "Failed FINE Permission Check");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//        }
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("GoogleMaps", "Failed COARSE Permission Check");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//        }
//
//        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//                || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//            mMap.setMyLocationEnabled(true);
//        }
//        **/
//
//        locationSearch = (EditText) findViewById(R.id.editText_addr);
//
//        //search supplement
//        gotMyLocationOneTime = false;
//        getLocation();
//    }
//
//
//    //ADD a vIEW BUTTON AND method to switch between satellite and map views
//
//    public void changeView(View v) {
//        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        } else {
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        }
//
//    }
//
//    public void onSearch(View v) {
//        String location = locationSearch.getText().toString();
//
//        List<Address> addressList = null;
//        List<Address> addressListZip = null;
//
//        //use LocationManager for user location
//        //Implement LocationListener inerface to setup location services
//        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String provider = service.getBestProvider(criteria, false);
//
//        Log.d("GoogleMaps", "onSearch: location = " + location);
//        Log.d("GoogleMaps", "onSearch: provider " + provider);
//
//        LatLng userLocation = null;
//
//        //Check the last known location, need to specifically list the provider (network or gps)
//
//        try {
//
//
//            if (locationManager != null) {
//                Log.d("GoogleMaps", "onSearch: locationManager is not null");
//
//                if ((myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) != null) {
//                    userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                    Log.d("GoogleMaps", "onSearch: using NETWORK_PROVIDER userLocation is: " + myLocation.getLatitude() + " " + myLocation.getLongitude());
//                    Toast.makeText(this, "UserLoc " + myLocation.getLatitude() + myLocation.getLongitude(), Toast.LENGTH_SHORT);
//                } else if ((myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) != null) {
//                    userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                    Log.d("GoogleMaps", "onSearch: using NETWORK_PROVIDER userLocation is: " + myLocation.getLatitude() + " " + myLocation.getLongitude());
//                    Toast.makeText(this, "UserLoc " + myLocation.getLatitude() + myLocation.getLongitude(), Toast.LENGTH_SHORT);
//                } else {
//                    Log.d("GoogleMaps", "onSearch: myLocation is null from getLastKnownLocation");
//                }
//            }
//
//
//        } catch (SecurityException | IllegalArgumentException e) {
//            Log.d("GoogleMaps", "onSearch: Exception getLastKnownLocation");
//            Toast.makeText(this, "onSearch: Exception getLastKnownLocation", Toast.LENGTH_SHORT);
//        }
//
//        //get the location if it exists
//
//        if (!location.matches("")) {
//            Log.d("GoogleMaps", "onSearch: location field is populated");
//
//            Geocoder geocoder = new Geocoder(this, Locale.US);
//
//            try {
//                //get a list of the addresses
//                addressList = geocoder.getFromLocationName(location, 100,
//                        userLocation.latitude - (5.0 / 60),
//                        userLocation.longitude - (5.0 / 60),
//                        userLocation.latitude + (5.0 / 60),
//                        userLocation.longitude + (5.0 / 60));
//
//                Log.d("GoogleMaps", "onSearch: addressList is created");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (!addressList.isEmpty()) {
//                Log.d("GoogleMaps", "onSearch: addressList size is : " + addressList.size());
//                for (int i = 0; i < addressList.size(); i++) {
//                    Address address = addressList.get(i);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//                    //place a marker on the map
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(i + ": " + address.getAddressLine(0)));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                }
//            }
//        }
//
//
//    }//end onSearch
//
//    public void getLocation() {
//        try {
//
//            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            //get gps status, isProviderEnabled returns true if user has enabled gps;
//            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (isGPSEnabled){
//                Log.d("GoogleMaps", "getLocation: GPS is enabled");
//                Toast.makeText(this, "GPS is Enabled", Toast.LENGTH_SHORT);
//            }
//
//            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            if (isNetworkEnabled){
//                Log.d("GoogleMaps", "getLocation: Network is enabled");
//                Toast.makeText(this, "Network is Enabled", Toast.LENGTH_SHORT);
//            }
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                Log.d("GoogleMaps", "getLocation: no provider enabled!");
//            } else {
//                if (isNetworkEnabled) {
//                    //request location updates
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
//                }
//                if (isGPSEnabled) {
//                    //request location updates
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
//                }
//
//            }
//
//        } catch (Exception e) {
//            Log.d("GoogleMaps", "getLocation: Exception in get Location");
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    //LocationListener to setup callbacks for requestLocationUpdates
//    LocationListener locationListenerNetwork = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//
//            dropAmarker(LocationManager.NETWORK_PROVIDER);
//
//            //check if doing one time, if so remove updates to both gps and network
//            if(gotMyLocationOneTime == false){
//                locationManager.removeUpdates(this);
//                locationManager.removeUpdates(locationListenerGPS);
//                gotMyLocationOneTime = true;
//            } else {
//                if(ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                        MIN_TIME_BW_UPDATES,
//                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
//            }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            Log.d("GoogleMaps", "locationListenerNetwork: status changed");
//
//
//        }
//
//
//
//
//
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
//
//    LocationListener locationListenerGPS = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//
//            dropAmarker(LocationManager.GPS_PROVIDER);
//
//            //check if doing one time, if so remove updates to both gps and network
//            if(gotMyLocationOneTime == false){
//                locationManager.removeUpdates(this);
//                locationManager.removeUpdates(locationListenerNetwork);
//                gotMyLocationOneTime = true;
//            } //else {
////                if(ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
////                        && ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
////                    return;
////
////                }
////                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
////                        MIN_TIME_BW_UPDATES,
////                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerGPS);
////            }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            Log.d("GoogleMaps", "locationListenerGPS: status changed");
//
//            //switch (status)
//                //case LocationProvider.AVAILABLE
//                //printout log.d and/or toast message
//                //break;
//                //case LocationProvider.OUT_OF_SERVICE
//                //LOG.D TO DEBUG
//                //enable network updates
//                //break;
//                //case LocationProvider.TEMPORARILY_UNAVAILABLE;
//                //enable both network and gps
//                //break;
//                //default;
//                //enable both network and gps
//
//            //if(ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//            //                        && ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            //                    return;
//            //                }
//            //                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//            //                        MIN_TIME_BW_UPDATES,
//            //                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
//
//            switch (status){
//                case LocationProvider.AVAILABLE:
//                    Log.d("GoogleMaps", "onStatusChanged: location provider is available;");
//                    break;
//                case LocationProvider.OUT_OF_SERVICE:
//                    Log.d("GoogleMaps", "onStatusChanged: time to debug! location provider out of service;");
//                    if(ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                                            && ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//                                        return;
//                                    }
//                                   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                                            MIN_TIME_BW_UPDATES,
//                                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
//                    break;
//                case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                    getLocation();
//                    break;
//                default:
//                    getLocation();
//                    break;
//            }
//
//
//
//            }
//
//
//
//
//
//
//
//
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
//
//    public void dropAmarker(String provider) {
//        //if(locationManager != null)
//        //if(checkSelfPermission... fails)
//        //     return
//        //  myLocation = locationManager.getLastKnownLocation(provider)
//        //LatLng userLocation = null;
//        //if(myLocation == null) print log or toast message, error
//        //else
//        //  userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude())
//        //  CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, MY_LOC_ZOOM_FACTOR);
//        //IF (PROVIDER == LocationManager.GPS_PROVIDER)
//        //add circle for the marker with 2 outer rings, etc. (2 rings not necessary)
//        //mMap.addCircle(new CircleOptions()
//        //.center(userLocation)
//        //.radius(1)
//        //.strokeColor(Color.RED)
//        //.strokeWidth(2)
//        //fillColor(Color.red))
//        //else you are in network, add circle for the marker with BLUE
//        //mMap.animateCamera(update)
//        if (locationManager != null) {
//            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//
//        }
//        myLocation = locationManager.getLastKnownLocation(provider);
//        LatLng userLocation = null;
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, MY_LOC_ZOOM_FACTOR);
//
//        if(myLocation == null) {
//            Toast.makeText(this, "dropAmarker: No location found", Toast.LENGTH_SHORT);
//        } else {
//            userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//            update = CameraUpdateFactory.newLatLngZoom(userLocation, MY_LOC_ZOOM_FACTOR);
//        }
//
//        if(provider == LocationManager.GPS_PROVIDER){
//            mMap.addCircle(new CircleOptions().center(userLocation).radius(1).strokeColor(Color.RED).strokeWidth(2).fillColor(Color.RED));
//        } else {
//            mMap.addCircle(new CircleOptions().center(userLocation).radius(1).strokeColor(Color.BLUE).strokeWidth(2).fillColor(Color.BLUE));
//        }
//
//        mMap.animateCamera(update);
//    }
//
//    public void trackMyLocation(View view){
//        //kick off the location tracker using getLocation to start the LocationListener
//        //if(notTrackingMyLocation) {getLocation(); notTrackingMyLocation = false;
//        //else {removeUpdate for both both network and gps; notTrackingMyLocation = true;
//        getLocation();
//        if(notTrackingMyLocation) {
//            getLocation();
//            Toast.makeText(this, "Tracking", Toast.LENGTH_SHORT).show();
//            notTrackingMyLocation = false;
//        } else {
//            locationManager.removeUpdates(locationListenerGPS);
//            locationManager.removeUpdates(locationListenerNetwork);
//            Toast.makeText(this, "Not tracking", Toast.LENGTH_SHORT).show();
//            notTrackingMyLocation = true;
//
//        }
//
//
//
//
//
//    }
//
//    public void clearMarkers(View view){
//        mMap.clear();
//    }
//
//}
//


package com.example.rajivd7435.devakimapsapprepo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText locationSearch;
    private LocationManager locationManager;
    private Location myLocation;

    private boolean gotMyLocationOneTime;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean trackingMyLocation = true;

//    private List<LatLng> points;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATE = 0.0f;
    private static final int MY_LOC_ZOOM_FACTOR = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //Add a marker on themap that shows your place of birth.
        //and displays the message "born here" when tapped.
        LatLng sanDiego = new LatLng(32.7157, -117.1611);
        mMap.addMarker(new MarkerOptions().position(sanDiego).title("born here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sanDiego));

/*        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Log.d("MyMapsApp", "Failed FINE permission check");
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Log.d("MyMapsApp", "Failed FINE permission check");
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
*/


        locationSearch = (EditText) findViewById(R.id.editText_addr);
        gotMyLocationOneTime = false;
        getLocation();

    }

    //Add View button and method (changeView) to switch between satellite and map views.

    public void changeView(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void onSearch(View view) {
        String location = locationSearch.getText().toString();

        List<Address> addressList = null;
        List<Address> addressListZip = null;

        //use LocationManager for user location
        //Implement LocationListener inerface to setup location services
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);

        Log.d("GoogleMaps", "onSearch: location = " + location);
        Log.d("GoogleMaps", "onSearch: provider " + provider);

        LatLng userLocation = null;

        //Check the last known location, need to specifically list the provider (network or gps)

        try {


            if (locationManager != null) {
                Log.d("GoogleMaps", "onSearch: locationManager is not null");

                if ((myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) != null) {
                    userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    Log.d("GoogleMaps", "onSearch: using NETWORK_PROVIDER userLocation is: " + myLocation.getLatitude() + " " + myLocation.getLongitude());
                    Toast.makeText(this, "UserLoc " + myLocation.getLatitude() + myLocation.getLongitude(), Toast.LENGTH_SHORT);
                } else if ((myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) != null) {
                    userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    Log.d("GoogleMaps", "onSearch: using NETWORK_PROVIDER userLocation is: " + myLocation.getLatitude() + " " + myLocation.getLongitude());
                    Toast.makeText(this, "UserLoc " + myLocation.getLatitude() + myLocation.getLongitude(), Toast.LENGTH_SHORT);
                } else {
                    Log.d("GoogleMaps", "onSearch: myLocation is null from getLastKnownLocation");
                }
            }


        } catch (SecurityException | IllegalArgumentException e) {
            Log.d("GoogleMaps", "onSearch: Exception getLastKnownLocation");
            Toast.makeText(this, "onSearch: Exception getLastKnownLocation", Toast.LENGTH_SHORT);
        }

        //get the location if it exists

        if (!location.matches("")) {
            Log.d("GoogleMaps", "onSearch: location field is populated");

            Geocoder geocoder = new Geocoder(this, Locale.US);

            try {
                //get a list of the addresses
                addressList = geocoder.getFromLocationName(location, 100,
                        userLocation.latitude - (5.0 / 60),
                        userLocation.longitude - (5.0 / 60),
                        userLocation.latitude + (5.0 / 60),
                        userLocation.longitude + (5.0 / 60));

                Log.d("GoogleMaps", "onSearch: addressList is created");

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!addressList.isEmpty()) {
                Log.d("GoogleMaps", "onSearch: addressList size is : " + addressList.size());
                for (int i = 0; i < addressList.size(); i++) {
                    Address address = addressList.get(i);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    //place a marker on the map
                    mMap.addMarker(new MarkerOptions().position(latLng).title(i + ": " + address.getAddressLine(0)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
    }

    //Method getLocation to place a marker at current location
    public void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            //get GPS status
            //isProiderEnabled returns true if user has enabled gps on phone
            isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Log.d("MyMapsApp", "getLocation: GPS is enabled");
            }
            //get Network status
            isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
            if (isNetworkEnabled) {
                Log.d("MyMapsApp", "getLocation: Network is enabled");
            }

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d("MyMapsApp", "getLocation: no provider is enabled");
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerNetwork);
                }
                if (isGPSEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerGPS);
                }
            }
        } catch (Exception e) {
            Log.d("MyMapsApp", "getLocation: Caught exception");
            e.printStackTrace();
        }
    }

    //LocationListener is an anonymous inner class
    //Setup for callbacks from the requestLocationUpdates
    LocationListener locationListenerNetwork = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            dropAmarker(LocationManager.NETWORK_PROVIDER);


            if (gotMyLocationOneTime == false) {
                locationManager.removeUpdates(this);
                locationManager.removeUpdates(locationListenerGPS);
                gotMyLocationOneTime = true;
            } else {
                
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerNetwork);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("MyMapsApp", "locationListenerNetwork: status change");

            switch(status) {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(getApplicationContext(), "LocationProvider.AVAILABLE", Toast.LENGTH_SHORT).show();
                    Log.d("MyMapsApp", "locationListenerNetwork: LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("MyMaps", "locationListenerNetwork: LocationProvider.OUT_OF_SERVICE");
                    isNetworkEnabled=true;
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("MyMaps", "LocationProvider is temporarily unavailable");
                    isNetworkEnabled=true;
                    isGPSEnabled=true;
                    break;
                default:
                    Log.d("MyMaps", "LocationProvider default");
                    isNetworkEnabled=true;
                    isGPSEnabled=true;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    LocationListener locationListenerGPS = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            dropAmarker(LocationManager.GPS_PROVIDER);
            //Check if doing one time via onMapReady, if so remove updates to both gps and network
            if (gotMyLocationOneTime == false) {
                locationManager.removeUpdates(this);
                locationManager.removeUpdates(locationListenerGPS);
                gotMyLocationOneTime = true;
            } else {
                //if here then tracking so relaunch request for network
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerGPS);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("MyMapsApp", "locationListenerNetwork: status change");
            //switch(i)
            //print out log.d and or toast message
            //break;
            //case LocationProvider.OUT_OF_SERVICE
            //enable network updates
            //break;
            //case LocationProvider.TEMPORARILY_UNAVAILABLE
            //enable both network and gps
            //break;
            //default;
            //enbale both network and gps
            switch(status) {
                case LocationProvider.AVAILABLE:
                    Log.d("MyMapsApp", "locationListenerNetwork: LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("MyMaps", "locationListenerNetwork: LocationProvider.OUT_OF_SERVICE");
                    isNetworkEnabled=true;
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("MyMaps", "LocationProvider is temporarily unavailable");
                    isNetworkEnabled=true;
                    isGPSEnabled=true;
                    break;
                default:
                    Log.d("MyMaps", "LocationProvider default");
                    isNetworkEnabled=true;
                    isGPSEnabled=true;
            }


        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void dropAmarker(String provider) {

        if(locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyMapsApp", "Failed FINE permission check");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyMapsApp", "Failed FINE permission check");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }

            myLocation = locationManager.getLastKnownLocation(provider);
            LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            if(myLocation == null){
                Log.d("MyMapsApp", "location is null");
            }
            else {
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, MY_LOC_ZOOM_FACTOR);
                if (provider.equals(LocationManager.GPS_PROVIDER)) {
                    mMap.addCircle(new CircleOptions().center(userLocation).radius(1).strokeColor(Color.RED).strokeWidth(2).fillColor(Color.RED));
                } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                    mMap.addCircle(new CircleOptions().center(userLocation).radius(1).strokeColor(Color.BLUE).strokeWidth(2).fillColor(Color.BLUE));
                }

                mMap.animateCamera(update);
            }
        }
    }
    public void trackMyLocation(View view){

        if(trackingMyLocation){
            getLocation();
            trackingMyLocation=false;
        }
        else if(!trackingMyLocation){
            isGPSEnabled=!isGPSEnabled;
            isNetworkEnabled=!isNetworkEnabled;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListenerGPS);
            locationManager.removeUpdates(locationListenerNetwork);
            trackingMyLocation=true;
        }


    }

    public void clearMarkers(View view){
        mMap.clear();

    }

}