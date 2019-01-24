/*
* MBL404 - Team B
* Instructor: John Becton
*
* Created by: Leon Nguyen
* Created: 11/12/2016
* Modified by: Leon Nguyen
* Modified: 11/13/2016
* Description: Class for all methods for locations section
*
* */

package uop.mbl404_team_b.fragments;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uop.mbl404_team_b.MainActivity;
import uop.mbl404_team_b.R;
import uop.mbl404_team_b.helpers.CustomListViewAdapter;
import uop.mbl404_team_b.models.ContactModel;

import static android.content.Context.LOCATION_SERVICE;

public class TabLocations extends Fragment {
    public static ArrayList<ContactModel> locationList;
    public static Integer CONTACT_SELECTED_INDEX = -1;
    public static View rootFragLocView;
    public static ListView listViewLoc;

    private static Activity myActivity;
    private static final int REQUEST_CODE = 99;
    private MyCurrentLocationListener locationListener;
    private static Marker mCurrLocationMarker;
    private LocationManager locationManager;
    private String city;

    private static GoogleMap gMap;

    //default constructor
    public TabLocations(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootFragLocView = inflater.inflate(R.layout.fragment_locations, container, false);
        //populate location model for user search location

        myActivity = getActivity();
        //get the list view control
        listViewLoc = (ListView) rootFragLocView.findViewById(R.id.listLocationView);
        listViewLoc.setClickable(true);
        listViewLoc.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            //when user clicks on an item on the list, pan camera to the clinic location on the map.
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                ContactModel model = (ContactModel)adapter.getItemAtPosition(position);
                Object[] geoParams = getLocationFromAddress(String.valueOf(model.getZipcode()), true);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng)geoParams[0], 13));
            }
        });
        //add the map fragment container
        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map_container, mSupportMapFragment).commit();
        }

        //check if google map is ready.
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    gMap = googleMap;
                    if (gMap != null) {

                        //check location service permission, prompts the user
                        CheckPermission();
                    }
                }
            });
        }

        return rootFragLocView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //update the listview with the list of locations
    public static void refreshDisplay(){
        if (TabSearch.SETTING_SEARCH != null){
            String strAddress = TabSearch.SETTING_SEARCH.getFullAddress();
            Geocoder coder = new Geocoder(myActivity);
            List<Address> address = new ArrayList<>();
            try {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null || address.size() == 0) {
                    Toast.makeText(rootFragLocView.getContext(), "No results", Toast.LENGTH_SHORT).show();
                    return;
                }
                Address location = address.get(0);
                Double lat = location.getLatitude();
                Double lng = location.getLongitude();
                int dist = TabSearch.SETTING_SEARCH.getSearchDistance();

                locationList = MainActivity.dbHelper.getContactsWithinDistance(lat, lng, dist, TabSearch.SETTING_SEARCH.getSortCategory());

                //if there are results, populate list view adapter and show locations on map
                if (locationList != null && locationList.size() !=0) {
                    CustomListViewAdapter adapter = new CustomListViewAdapter(MainActivity.m_context, locationList);
                    //adapter.getItem()
                    listViewLoc.setAdapter(adapter);

                    Object[] geoParams;
                    //for reach location in location list results, mark them on the map
                    for (int i = 0; i < locationList.size(); i++) {
                        geoParams = getLocationFromAddress(String.valueOf(locationList.get(i).getZipcode()), true);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position((LatLng) geoParams[0]);
                        markerOptions.title(locationList.get(i).getAddress());
                        markerOptions.snippet(locationList.get(i).toString());
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        gMap.clear();
                        mCurrLocationMarker = gMap.addMarker(markerOptions);
                    }

                    //pan and zoom the map camera to the user search location
                    geoParams = getLocationFromAddress(String.valueOf(locationList.get(0).getZipcode()), true);
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) geoParams[0], 3));
                }
            }
            catch (Exception ex) {
                Toast.makeText(rootFragLocView.getContext(), "No results", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }

        }
    }

    /* location listener class for the get current location button and logic. Works, but camera
     * panning off grid, not sure why. Need to debug.
     */
    public class MyCurrentLocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //Set current location marker to location
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(city);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = gMap.addMarker(markerOptions);

            //move map camera
            //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13)); //<****** 11/27
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    //Method to check for location permission and request user for permission
    private void CheckPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationListener = new MyCurrentLocationListener();
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setZoomControlsEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
        }
        else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    //Using Geocoder class to get the latitude and longitude from a street address or zipcode
    private static Object[] getLocationFromAddress(String strAddress, boolean geoPointOnly) {

        Geocoder coder = new Geocoder(myActivity);
        List<Address> address = new ArrayList<>();
        LatLng p1 = null;
        Address location = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        if (geoPointOnly){
            return new Object[] {p1};
        }
        else{
            return new Object[] {
                    location.getLocality(),
                    getUSStateCode(address.get(0)),
                    p1
            };
        }

    }

    //Convert the two letter state to full state names.
    private static String getUSStateCode(Address USAddress){
        String fullAddress = "";
        for(int j = 0; j <= USAddress.getMaxAddressLineIndex(); j++)
            if (USAddress.getAddressLine(j) != null)
                fullAddress = fullAddress + " " + USAddress.getAddressLine(j);

        String stateCode = null;
        Pattern pattern = Pattern.compile(" [A-Z]{2} ");
        String helper = fullAddress.toUpperCase().substring(0, fullAddress.toUpperCase().indexOf("USA"));
        Matcher matcher = pattern.matcher(helper);
        while (matcher.find())
            stateCode = matcher.group().trim();

        return stateCode;
    }
}