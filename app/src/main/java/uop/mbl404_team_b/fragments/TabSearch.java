package uop.mbl404_team_b.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import uop.mbl404_team_b.MainActivity;
import uop.mbl404_team_b.R;
import uop.mbl404_team_b.models.SearchModel;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class TabSearch extends Fragment {

    //variables
    Button btnName;
    Button btnDistance;
    Button btnRating;
    Button btnSearch;

    Button btn2miles;
    Button btn5miles;
    Button btn10miles;
    Button btn25miles;
    Button btn50miles;

    CheckBox chkBoxDefault;
    TextView tvClearAll;

    EditText etAddress;
    EditText etCity;
    EditText etState;
    EditText etZipcode;

    Spinner ddlStatus;

    String selectedSortBy;
    Integer selectedDistance;

    boolean isNameSelected = false;
    boolean isDistanceSelected = false;
    boolean isRatingSelected = false;

    boolean is2MilesSelected = false;
    boolean is5MilesSelected = false;
    boolean is10MilesSelected = false;
    boolean is25MilesSelected = false;
    boolean is50MilesSelected = false;

    final int selColor = Color.parseColor("#5b9aff");
    final int unselColor = Color.parseColor("#cccccc");

    public static SearchModel SETTING_SEARCH = new SearchModel();

    //default constructor
    public TabSearch(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_pref, container, false);

        Init(rootView);
        SetClickListeners(rootView);
        SetEditViewFocusListeners();
        LoadPreferences();

        return rootView;
    }

    //class for focus change listners to hide the soft keyboard
    private class MyFocusChangeListener implements View.OnFocusChangeListener {
        public void onFocusChange(View v, boolean hasFocus){

            if(!hasFocus) {
                InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //load user search preferences
    private void LoadPreferences(){

        selectedDistance = MainActivity.preferenceSettings.getInt("distance", 2);
        selectedSortBy = MainActivity.preferenceSettings.getString("sortby", "");

        switch (selectedSortBy) {
            case "Distance":
                btnDistance.setBackgroundColor(selColor);
                break;
            case "Rating":
                btnRating.setBackgroundColor(selColor);
                break;
            default:
                btnName.setBackgroundColor(selColor);
                break;
        }

        switch (selectedDistance) {
            case 5:
                btn5miles.setBackgroundColor(selColor);
                break;
            case 10:
                btn10miles.setBackgroundColor(selColor);
                break;
            case 25:
                btn25miles.setBackgroundColor(selColor);
                break;
            case 50:
                btn50miles.setBackgroundColor(selColor);
                break;
            default:
                btn2miles.setBackgroundColor(selColor);
                break;
        }

        String strAddress = MainActivity.preferenceSettings.getString("address", "");
        String strCity = MainActivity.preferenceSettings.getString("city", "");
        String strState = MainActivity.preferenceSettings.getString("state", "");
        String strZip = MainActivity.preferenceSettings.getString("zipcode", "");
        Integer intStatus = MainActivity.preferenceSettings.getInt("status", 1);

        etAddress.setText(strAddress);
        etCity.setText(strCity);
        etState.setText(strState);
        etZipcode.setText(strZip);
        ddlStatus.setSelection(intStatus);

        //save preferences to static object
        SETTING_SEARCH.setAddress(strAddress);
        SETTING_SEARCH.setCity(strCity);
        SETTING_SEARCH.setState(strState);
        SETTING_SEARCH.setZipcode(strZip);
        SETTING_SEARCH.setSearchDistance(selectedDistance);
        SETTING_SEARCH.setSortCategory(selectedSortBy);
        SETTING_SEARCH.setStatus(intStatus);

    }

    //instantiates all variables
    private void Init(View v){
        btnName = (Button) v.findViewById(R.id.btnName);
        btnDistance = (Button) v.findViewById(R.id.btnDistance);
        btnRating = (Button) v.findViewById(R.id.btnRating);
        btn2miles = (Button) v.findViewById(R.id.btn2miles);
        btn5miles = (Button) v.findViewById(R.id.btn5miles);
        btn10miles = (Button) v.findViewById(R.id.btn10miles);
        btn25miles = (Button) v.findViewById(R.id.btn25miles);
        btn50miles = (Button) v.findViewById(R.id.btn50miles);
        etAddress = (EditText) v.findViewById(R.id.editTextAddress);
        etCity = (EditText) v.findViewById(R.id.editTextCity);
        etState = (EditText) v.findViewById(R.id.editTextState);
        etZipcode = (EditText) v.findViewById(R.id.editTextZipcode);
        chkBoxDefault = (CheckBox) v.findViewById(R.id.chkBoxDefault);
        tvClearAll = (TextView) v.findViewById(R.id.lnkClearAll);
        ddlStatus = (Spinner) v.findViewById(R.id.ddlStatus);
        btnSearch = (Button) v.findViewById(R.id.btnSearch);
    }

    //set click Listeners
    private void SetClickListeners(View v){

        btnName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isNameSelected = true;
                isDistanceSelected = false;
                isRatingSelected = false;
                btnName.setBackgroundColor(selColor);
                btnDistance.setBackgroundColor(unselColor);
                btnRating.setBackgroundColor(unselColor);
            }
        });
        btnDistance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isNameSelected = false;
                isDistanceSelected = true;
                isRatingSelected = false;
                btnName.setBackgroundColor(unselColor);
                btnDistance.setBackgroundColor(selColor);
                btnRating.setBackgroundColor(unselColor);
            }
        });
        btnRating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isNameSelected = false;
                isDistanceSelected = false;
                isRatingSelected = true;
                btnName.setBackgroundColor(unselColor);
                btnDistance.setBackgroundColor(unselColor);
                btnRating.setBackgroundColor(selColor);
            }
        });
        btn2miles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is2MilesSelected = true;
                is5MilesSelected = false;
                is10MilesSelected = false;
                is25MilesSelected = false;
                is50MilesSelected = false;
                btn2miles.setBackgroundColor(selColor);
                btn5miles.setBackgroundColor(unselColor);
                btn10miles.setBackgroundColor(unselColor);
                btn25miles.setBackgroundColor(unselColor);
                btn50miles.setBackgroundColor(unselColor);
            }
        });

        btn5miles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is2MilesSelected = false;
                is5MilesSelected = true;
                is10MilesSelected = false;
                is25MilesSelected = false;
                is50MilesSelected = false;
                btn2miles.setBackgroundColor(unselColor);
                btn5miles.setBackgroundColor(selColor);
                btn10miles.setBackgroundColor(unselColor);
                btn25miles.setBackgroundColor(unselColor);
                btn50miles.setBackgroundColor(unselColor);
            }
        });

        btn10miles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is2MilesSelected = false;
                is5MilesSelected = false;
                is10MilesSelected = true;
                is25MilesSelected = false;
                is50MilesSelected = false;
                btn2miles.setBackgroundColor(unselColor);
                btn5miles.setBackgroundColor(unselColor);
                btn10miles.setBackgroundColor(selColor);
                btn25miles.setBackgroundColor(unselColor);
                btn50miles.setBackgroundColor(unselColor);
            }
        });

        btn25miles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is2MilesSelected = false;
                is5MilesSelected = false;
                is10MilesSelected = false;
                is25MilesSelected = true;
                is50MilesSelected = false;
                btn2miles.setBackgroundColor(unselColor);
                btn5miles.setBackgroundColor(unselColor);
                btn10miles.setBackgroundColor(unselColor);
                btn25miles.setBackgroundColor(selColor);
                btn50miles.setBackgroundColor(unselColor);
            }
        });
        btn50miles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is2MilesSelected = false;
                is5MilesSelected = false;
                is10MilesSelected = false;
                is25MilesSelected = false;
                is50MilesSelected = true;
                btn2miles.setBackgroundColor(unselColor);
                btn5miles.setBackgroundColor(unselColor);
                btn10miles.setBackgroundColor(unselColor);
                btn25miles.setBackgroundColor(unselColor);
                btn50miles.setBackgroundColor(selColor);
            }
        });

        //set click listner for "Clear all" link button
        tvClearAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                etAddress.setText("");
                etCity.setText("");
                etState.setText("");
                etZipcode.setText("");
                ddlStatus.setSelection(0);
            }
        });

        //populate status spinner content
        ArrayAdapter<String> adapterA = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.search_loc_status));
        ddlStatus.setAdapter(adapterA);

        //button click event for button Search to go to the location tab
        btnSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //get selected sort by
                if (isRatingSelected){
                    selectedSortBy = btnRating.getText().toString();
                }
                else if (isDistanceSelected){
                    selectedSortBy = btnDistance.getText().toString();
                }
                else{
                    selectedSortBy = btnName.getText().toString();
                }

                //get selected distance
                if (is2MilesSelected){
                    selectedDistance = 2;
                }
                else if (is5MilesSelected){
                    selectedDistance = 5;
                }
                else if (is10MilesSelected){
                    selectedDistance = 10;
                }
                else if (is25MilesSelected){
                    selectedDistance = 25;
                }
                else{
                    selectedDistance = 50;
                }

                String strAddress = etAddress.getText().toString().trim();
                String strCity = etCity.getText().toString().trim();
                String strState = etState.getText().toString().trim();
                String strZip = etZipcode.getText().toString().trim();
                Integer intStatus = ddlStatus.getSelectedItemPosition();

                //add user selected preferences to preference editor
                MainActivity.preferenceEditor.putString("sortby", selectedSortBy);
                MainActivity.preferenceEditor.putInt("distance", selectedDistance);
                MainActivity.preferenceEditor.putString("address", strAddress);
                MainActivity.preferenceEditor.putString("city", strCity);
                MainActivity.preferenceEditor.putString("state", strState);
                MainActivity.preferenceEditor.putString("zipcode", strZip);
                MainActivity.preferenceEditor.putInt("status", intStatus);

                //saves the preferences to file only if checkbox default checked
                if (chkBoxDefault.isChecked()) {
                    MainActivity.preferenceEditor.commit();
                }

                //save preferences to static object
                SETTING_SEARCH.setAddress(strAddress);
                SETTING_SEARCH.setCity(strCity);
                SETTING_SEARCH.setState(strState);
                SETTING_SEARCH.setZipcode(strZip);
                SETTING_SEARCH.setSortCategory(selectedSortBy);
                SETTING_SEARCH.setSearchDistance(selectedDistance);
                SETTING_SEARCH.setStatus(intStatus);

                //GetLocationResults();
                TabLocations.refreshDisplay();
                //navigate to locations tab
                MainActivity.setCurrentTab(1);
            }
        });
    }

    //set textboxes focus change listeners
    private void SetEditViewFocusListeners(){
        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        etAddress.setOnFocusChangeListener(ofcListener);
        etCity.setOnFocusChangeListener(ofcListener);
        etState.setOnFocusChangeListener(ofcListener);
        etZipcode.setOnFocusChangeListener(ofcListener);
    }

}
